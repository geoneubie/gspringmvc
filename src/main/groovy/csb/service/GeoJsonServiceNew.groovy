package csb.service
import csb.model.DataProviders
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeoJsonServiceNew {

    @Autowired
    private DataProviders dps

    public GeoJsonServiceNew(DataProviders dps) {
        this.dps = dps
    }

    String featureCollection() {
        def fcJb = new JsonBuilder()
        // Create the CSB GeoJSON metadata header
        def fc = fcJb {
            type "FeatureCollection"
            crs {
                type "name"
                properties {
                    name "EPSG:4326"
                }
            }
        }
        return fcJb.toPrettyString()

    }

    def addGps(Map cmiMap) {
        def gpsJb = new JsonBuilder()
        def gps = gpsJb {
            type "GPS"
            make "${cmiMap.gpsmake}"
            model "${cmiMap.gpsmake}"
        }
        return gps
    }

    def addSounder(Map cmiMap) {

        def sounderJb = new JsonBuilder()
        def sounder = sounderJb {
            type "Sounder"
            make "${cmiMap.soundermake}"
            model "${cmiMap.soundermodel}"
            serialno "${cmiMap.sounderserialno}"
        }
        return sounder
    }

    def addSensors(Map cmiMap) {

        def sensorsJb = new JsonBuilder()
        def sensors = sensorsJb([this.addSounder(cmiMap), this.addGps(cmiMap)])
        return sensors

    }

    Map platform(Map cmiMap) {

        def platform = [:]
        def platJb = new JsonBuilder()
        platform << platJb {
            type "Ship"
            name "${cmiMap.shipname}"
            ImoNumber "${cmiMap.imonumber}"
            draft {
                value "${cmiMap.draft}"
                "uom" "m"
                offsetApplied "false"
            }
            velocity "${cmiMap.velocity}"
            longitudinalOffsetFromGPStoSonar "${cmiMap.longitudinalOffsetFromGPStoSonar}"
            lateralOffsetFromGPStoSonar "${cmiMap.lateralOffsetFromGPStoSonar}"
        }

        platform << ["sensors": this.addSensors(cmiMap)]

        return platform

    }

    String meta(Map cmiMap) {

        def dp = dps.getProvider("${cmiMap.dataProvider}")

        def metaJb = new JsonBuilder()
        def meta = metaJb {
            type "FeatureCollection"
            crs {
                type "name"
                properties {
                    name "EPSG:4326"
                }
            }
        }

        def metaProps = [convention: "CSB 1.0"]
        metaProps << ["platform": this.platform(cmiMap)]

        def propJb = new JsonBuilder()
        metaProps << propJb {
            providerContactPoint {
                hasEmail "${dp.providerEmail}"
            }
            processorContactPoint {
                hasEmail "${dp.processorEmail}"
            }
            ownerContactPoint {
                hasEmail "${dp.ownerEmail}"
            }
            depthUnits "meters"
            timeUnits "UTC"
        }

        meta << [ "properties": metaProps ]
        return new JsonBuilder(meta).toPrettyString()

    }

    List scanXyzChunk( Scanner sc, boolean skip ) {
        def i = 0
        def lat
        def lon
        def z
        def tokens = []
        def pts = []

        while ( sc.hasNext() && i < 100 ) {
            String line = sc.nextLine()
            if (!skip) {
                tokens = line.tokenize(',')
                lat = Double.parseDouble(tokens[0])
                lon = Double.parseDouble(tokens[1])
                z = Double.parseDouble(tokens[2])
                pts << [lat, lon, z]
            } else {
                // First line is a header, skip it and continue
                skip = false
            }
            i++
        }

        return pts

    }

    String featuresChunk ( List pts ) {
        StringBuilder sb = new StringBuilder()
        pts.eachWithIndex { pt, i ->
            // Create the GeoJSON feature
            sb << this.feature( pt )
            if ( i < pts.size()-1 ) sb << ",${System.getProperty("line.separator")}"

        }
        return sb.toString()

    }

    String feature( List pt ) {
        def featJb = new JsonBuilder()
        featJb {
            type 'Feature'
            geometry {
                type 'Point'
                coordinates( [pt[1],pt[0]] )
            }
            properties {
                depth pt[2]
            }
        }
        return featJb.toString()

    }

    // Need to handle "dataProvider" field
    Map transform( Map entries ) throws Exception {
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( entries.JSON )

        def fcStr = this.featureCollection()

        def metaStr = this.meta( cmiMap )





        meta.put( "features", features )

        metaJb.toPrettyString()
        def jsonFile = new File( "${entries.BASEFILENM}.json" )
        jsonFile.write("${metaJb.toPrettyString( )}")

    }

}
