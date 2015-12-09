package csb.service
import csb.config.AppConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import java.nio.file.Path
import java.nio.file.Paths

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@ComponentScan( basePackages=[ "csb.config" ] )
class GeoJsonServiceNewTest extends GroovyTestCase {

    private static final Logger logger =
            LoggerFactory.getLogger( GeoJsonServiceNewTest.class )

    def geojsonService = new GeoJsonServiceNew()

    @Test
    public void scanXyzChunk() {

        ClassLoader classLoader = getClass().getClassLoader();
        File xyzFile = new File(classLoader.getResource("data/95003.xyz").getFile());
        Scanner sc = new Scanner( xyzFile )
        List pts = geojsonService.scanXyzChunk( sc, true )

        assert pts.size() == 99

    }

    @Test
    public void scanXyzToEOF() {

        ClassLoader classLoader = getClass().getClassLoader();
        File xyzFile = new File(classLoader.getResource("data/95003.xyz").getFile());
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

        def pt = ["42.8339", "-50.2883", "428.3" ]

        assert geojsonService.feature( pt ) == '{"type":"Feature","geometry":{"type":"Point","coordinates":["-50.2883","42.8339"]},"properties":{"depth":"428.3"}}'
    }

//    @Test
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

        while ( sc.hasNext() ) {
            def pts = geojsonService.scanXyzChunk( sc, skip )
            jsonFile << geojsonService.featuresChunk( pts )
            skip = false
            totPts += pts.size()
        }


        File readJsonFile = new File( "${p.getParent()}/95003_all.json" )
        if ( skip ) totPts = totPts - 1
        assert readJsonFile.readLines().size() == totPts-1 //subtract one

    }
}
