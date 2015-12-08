package csb.service

import csb.model.DataProviders
import csb.model.Staging
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeoJsonService implements ITransformService {

    private static final Logger logger =
            LoggerFactory.getLogger( GeoJsonService.class )

    @Autowired
    private Staging stagingDirs

    private DataProviders dps

    public GeoJsonService( Staging stagingDirs, DataProviders dps ) {
        this.stagingDirs = stagingDirs
        this.dps = dps

        logger.debug( dps.getProvider("SEAID").providerEmail )
    }

    // Need to handle "dataProvider" field
    Map transform( Map entries ) throws Exception {
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( entries.JSON )
        def dp = dps.getProvider( "${cmiMap.dataProvider}" )
        //def dp = dps.hmProviders["SEAID"]
        logger.debug("${dp.providerEmail}")
        def metaJb =  new JsonBuilder()
        // Create the CSB GeoJSON metadata header
        def meta = metaJb {
            type "FeatureCollection"
            crs {
                type "name"
                properties {
                    name "EPSG:4326"
                }
            }
        }

        def platform = [ : ]
        def platJb = new JsonBuilder( )
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

        //Add the sensors
        def sounderJb = new JsonBuilder( )
        def sounder = sounderJb {
            type "Sounder"
            make "${cmiMap.soundermake}"
            model "${cmiMap.soundermodel}"
            serialno "${cmiMap.sounderserialno}"
        }

        def gpsJb = new JsonBuilder( )
        def gps = gpsJb {
            type "GPS"
            make "${cmiMap.gpsmake}"
            model "${cmiMap.gpsmake}"
        }

        def sensorsJb = new JsonBuilder( )
        def sensors = sensorsJb( [sounder, gps] )
        platform << [ "sensors": sensors ]

        def metaProps = [ convention: "CSB 1.0" ]
        metaProps << [ "paltform": platform ]

        def propJb = new JsonBuilder(  )
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

        def lat
        def lon
        def z
        def tokens = []
        def pts = []

        def sb = new StringBuffer( )
        int i = 0
        new File( "${entries.BASEFILENM}.xyz" ).eachLine { line ->
            //Assumes header
            if ( i >= 1 ) {
                tokens = line.tokenize( ',' )
                lat = Double.parseDouble(tokens[0])
                lon = Double.parseDouble(tokens[1])
                z = Double.parseDouble(tokens[2])
                pts << [lat, lon, z]
            }
            i++
        }

        def features = [ ]
        pts.each { pt ->
            // Create the GeoJSON feature
            def featJb = new JsonBuilder(  )
            features << featJb {
                type 'Feature'
                geometry {
                    type 'Point'
                    coordinates( [pt[1],pt[0]] )
                }
                properties {
                    depth pt[2]
                }
            }

        }

        meta.put( "features", features )

        metaJb.toPrettyString()
        def jsonFile = new File( "${entries.BASEFILENM}.json" )
        jsonFile.write("${metaJb.toPrettyString( )}")

    }

}