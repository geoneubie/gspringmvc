package csb.service

import csb.model.DataProviderEntity
import csb.model.DataProviders
import groovy.json.JsonSlurper
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Path
import java.nio.file.Paths

class GeoJsonServiceTest {

    private static final Logger logger =
            LoggerFactory.getLogger( GeoJsonServiceTest.class )

    def geojsonService

    public GeoJsonServiceTest() {

        //Test set-up
        DataProviderEntity dp = new DataProviderEntity()

        dp.name = "SEAID"
        dp.providerEmail = "support@sea-id.org"
        dp.providerUrl = "https://www.sea-id.org"
        dp.processorEmail = "support@sea-id.org"
        dp.ownerEmail = "support@sea-id.org"

        DataProviders dps = new DataProviders()
        dps.addProvider("SEAID", dp)
        this.geojsonService = new GeoJsonService( dps )

    }


    @Test
    public void meta() {

        def csbMetadataInput = '{"shipname":"Kilo Moana","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"SEAID"}'
        def cmiMap = (new JsonSlurper()).parseText( csbMetadataInput )
        List<String> metaHdrList = geojsonService.meta( cmiMap )

        assert metaHdrList[0].length() > 0 && metaHdrList[1].length() == 2

    }

    @Test
    public void scanXyzChunk() {

        ClassLoader classLoader = getClass().getClassLoader()
        File xyzFile = new File(classLoader.getResource("data/95003.xyz").getFile())
        Scanner sc = new Scanner( xyzFile )
        List pts = geojsonService.scanXyzChunk( sc, true )

        assert pts.size() == 99

    }

    @Test
    public void scanXyzToEOF() {

        ClassLoader classLoader = getClass().getClassLoader()
        File xyzFile = new File(classLoader.getResource("data/95003.xyz").getFile())
        Scanner sc = new Scanner( xyzFile )
        int totPts = 0
        def pts = []
        boolean skip = true //Skip the first header line

        while ( sc.hasNext() ) {
            pts = geojsonService.scanXyzChunk( sc, skip )
            skip = false
            totPts += pts.size()
        }
        assert totPts == 198

    }

    @Test
    public void feature() {

        def pt = ["42.8339", "-50.2883", "428.3", "1028889" ]

        assert geojsonService.feature( pt ) == '        {"type":"Feature","geometry":{"type":"Point","coordinates":["-50.2883","42.8339"]},"properties":{"depth":"428.3","time":"1028889"}}'

    }

//    @Test Doesn't work for unknown reasons
//    public void featuresChunk() {
//        def pts = [ ["42.8339", "-50.2883", "428.3"], ["43.8339", "-49.2883", "429.3"] ]
//        String testCase = '{"type":"Feature","geometry":{"type":"Point","coordinates":["-50.2883","42.8339"]},"properties":{"depth":"428.3"}},' + System.getProperty("line.separator")
//        testCase += '{"type":"Feature","geometry":{"type":"Point","coordinates":["-49.2883","43.8339"]},"properties":{"depth":"429.3"}'
//        assertEquals geojsonService.featuresChunk( pts ), testCase
//    }

    @Test
    public void writeAllFeatures() {

        ClassLoader classLoader = getClass().getClassLoader()
        File xyzFile = new File(classLoader.getResource("data/95003.xyz").getFile())
        Scanner sc = new Scanner( xyzFile )
        Path p = Paths.get( "${xyzFile.getAbsolutePath()}" )
        File jsonFile = new File( "${p.getParent()}/95003_all.json" )
        if ( jsonFile.exists() ) {
            jsonFile.delete()
            // Create a new one
            jsonFile = new File( "${p.getParent()}/95003_all.json" )
        }

        int totPts = 0
        boolean skip = true //Skip the first header line

        def lineSeparator = System.getProperty("line.separator")
        while ( sc.hasNext() ) {
            def pts = geojsonService.scanXyzChunk( sc, skip )
            jsonFile << geojsonService.featuresChunk( pts )
            if ( sc.hasNext() ) {
                jsonFile << ",${lineSeparator}"
            }
            skip = false
            totPts += pts.size()
        }

        File readJsonFile = new File( "${p.getParent()}/95003_all.json" )
        assert readJsonFile.readLines().size() == totPts

    }
}
