package csb.service

import csb.dsmodelinput.Staging

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


@Service
class GeoJSONService implements ITransformService {

    @Autowired
    private Staging stagingDirs

    public GeoJSONService( Staging stagingDirs ) {
        this.stagingDirs = stagingDirs
    }


    //"dataProvider":"Linblad"}
    Map transform( Map entries ) throws Exception {
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( entries.JSON )

        def metaJb =  new JsonBuilder()
        // Create the CSB GeoJSON metadata header
        Map meta = metaJb {
            type "FeatureCollection"
            crs {
                type "name"
                properties {
                    name "EPSG:4326"
                }
            }
            properties {
                convention "CSB 1.0"
                platform {
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
                providerContactPoint {
                    hasEmail "explore@expeditions.com"
                }
                processorContactPoint {
                    hasEmail "explore@expeditions.com"
                }
                ownerContactPoint {
                    hasEmail "explore@expeditions.com"
                }
                depthUnits "meters"
                timeUnits "UTC"
            }
        }

        //Add the sensors
        def sensorList =
            [
                [ type: "Sounder", make: "${cmiMap.soundermake}", model: "${cmiMap.soundermodel}", serialno: "${cmiMap.sounderserialno}" ],
                [ type: "gps", make: "${cmiMap.gpsmake}", model: "${cmiMap.gpsmake}" ]
            ]

        meta.properties.platform << [ sensors: sensorList ]

        def lat
        def lon
        def z
        def tokens = []
        def pts = []

        def sb = new StringBuffer( )
        int i = 0
        new File( "${entries.BASEFILENM}.xyz" ).eachLine { line ->
            if (i > 1 && i < 10000) {
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

        def jsonFile = new File( "${entries.BASEFILENM}.json" )
        jsonFile.write("${metaJb.toPrettyString( )}")

    }

    boolean validate ( String s ) {}
}