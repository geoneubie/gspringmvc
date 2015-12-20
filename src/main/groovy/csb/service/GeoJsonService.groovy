package csb.service

import csb.model.DataProviders
import csb.repos.IDataProviderRepository
import csb.util.CsbUtil
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service

@ComponentScan( basePackages=[ "csb.config"] )
@Service
class GeoJsonService implements ITransformService {

    @Autowired
    private IDataProviderRepository idpRepository

    @Autowired
    private DataProviderService dpService

    private DataProviders dps

    public GeoJsonService() {

    }

    public GeoJsonService( DataProviders dps ) {

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

    def addGps( Map cmiMap ) {

        def gpsJb = new JsonBuilder()
        def gps = gpsJb {
            type "GPS"
            make "${cmiMap.gpsmake}"
            model "${cmiMap.gpsmodel}"
            offSetApplied false
        }
        return gps

    }

    def addSounder( Map cmiMap ) {

        def sounderJb = new JsonBuilder()

        def lonOffset = CsbUtil.returnJsonValue( cmiMap.longitudinalOffsetFromGPStoSonar )
        def latOffset = CsbUtil.returnJsonValue( cmiMap.lateralOffsetFromGPStoSonar )
        def v = CsbUtil.returnJsonValue( cmiMap.velocity )

        def sounder = sounderJb {
            type "Sounder"
            make "${cmiMap.soundermake}"
            model "${cmiMap.soundermodel}"
            serialNumber "${cmiMap.sounderserialno}"
            longitudinalOffsetFromGPStoSonar {
                value lonOffset
                uom "m"
            }
            lateralOffsetFromGPStoSonar {
                value latOffset
                uom "m"
            }
            velocity {
                value v
                uom "m/s"
            }
        }
        return sounder

    }

    def addSensors( Map cmiMap ) {

        def sensorsJb = new JsonBuilder()
        def sensors = sensorsJb([this.addSounder(cmiMap), this.addGps(cmiMap)])
        return sensors

    }

    Map platform( Map cmiMap ) {

        def platform = [:]
        def platJb = new JsonBuilder()
        def d
        if ( cmiMap.draft != "" ) {
            d = new Double( cmiMap.draft )
        } else {
            d = ""
        }

        platform << platJb {
            type "Ship"
            name "${cmiMap.shipname}"
            ImoNumber "${cmiMap.imonumber}"
            platformStatus "new"
            draft {
                value d
                "uom" "m"
                offsetApplied false
            }
        }

        platform << [ "sensors": this.addSensors(cmiMap) ]

        return platform

    }

    List<String> meta( Map cmiMap ) {

        def dp = dps.getProvider( cmiMap.dataProvider )

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

        def metaProps = [ convention: "CSB 1.0" ]
        metaProps << [ "platform": this.platform( cmiMap ) ]

        def propJb = new JsonBuilder()
        metaProps << propJb {
            providerContactPoint {
                orgName "${dp.name}"
                hasEmail "${dp.providerEmail}"
                orgUrl "${dp.providerUrl}"
            }
            processorContactPoint {
                hasEmail "${dp.processorEmail}"
            }
            ownerContactPoint {
                hasEmail "${dp.ownerEmail}"
            }
            depthUnits "meters"
            timeUnits "Epoch"
        }

        meta << [ "properties": metaProps ]
        String fullMetaStr = new JsonBuilder(meta).toPrettyString()

        String preMetaStr = fullMetaStr.substring( 0, fullMetaStr.length()-2 ) + ","
        String postMetaStr = fullMetaStr.substring( fullMetaStr.length()-2, fullMetaStr.length() )

        return [ preMetaStr, postMetaStr ]

    }

    List scanXyzChunk( Scanner sc, boolean skip ) {

        def i = 0
        def lat
        def lon
        def z
        def t
        def tokens = []
        def pts = []

        while ( sc.hasNext() && i < 100 ) {
            String line = sc.nextLine()
            if (!skip) {
                tokens = line.tokenize(',')
                lon = Double.parseDouble(tokens[0]) //x
                lat = Double.parseDouble(tokens[1]) //y
                z = Double.parseDouble(tokens[2])
                t = Double.parseDouble(tokens[3])
                pts << [lat, lon, z, t]
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
                time pt[3]
            }
        }
        return "        ${featJb.toString()}"

    }

    Map transform( Map entries ) throws Exception {
        this.dps = dpService.getAllDataProviders()

        def jsonSlurper = new JsonSlurper()
        def cmi = jsonSlurper.parseText( entries.JSON )
        List<String> metaListStr = this.meta( cmi )

        def jsonFile = new File( "${entries.BASEFILENM}.json" )
        def xyzFile = new File( "${entries.BASEFILENM}.xyz" )

        Scanner sc = new Scanner( xyzFile )
        // Write the metadata header block
        def lineSeparator = System.getProperty("line.separator")
        jsonFile.write "${metaListStr[0]}${lineSeparator}"
        jsonFile.append "    \"features\": [${lineSeparator}"

        // Now write the features
        boolean skip = true //Skip the first header line

        while ( sc.hasNext() ) {
            def pts = this.scanXyzChunk( sc, skip )
            jsonFile.append this.featuresChunk( pts )
            if ( sc.hasNext() ) {
                jsonFile.append ",${lineSeparator}"
            }
            skip = false
        }

        // Now close-out the file
        jsonFile.append "${lineSeparator}    ]${metaListStr[1]}"

        def hmMsg = [ TRANSFORMED : "GeoJson conversion complete." ]
        return hmMsg

    }

}
