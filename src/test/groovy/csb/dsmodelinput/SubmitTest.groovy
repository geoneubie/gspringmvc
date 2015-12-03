package csb.dsmodelinput

import csb.config.AppConfig
import csb.service.ITransformService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.context.annotation.ComponentScan

import static org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@ComponentScan( basePackages=[ "csb.dsmodelinput", "csb.config", "csb.aspect" ] )
class SubmitTest {

    @Autowired
    private ITransformService ss

    @Autowired
    private Staging stagingDirs

    @Test
    public void usShouldNotBeNull() {
        assertNotNull(ss)
    }

    @Test
    public void getStagingDirsCount() {

        def v = stagingDirs
        assert v.map.size() >= 1
        println v.hmStagingDirs.CSBFILES

    }

    @Test
    public void transformSad() {

        def userEntries = [:]
        userEntries << [ FILE : null ]
        userEntries << [ JSON : '{ foo: "bar" }' ]
        def hm = ss.transform( userEntries )
        def v = hm.TRANSFORMED
        assert v == "You failed to upload because the file was missing or empty."

    }

    @Test
    public void transformException() {

        try {

            def userEntries = null
            def hm = ss.transform(userEntries)

        } catch (Exception e) {

            assert true

        }

    }

    @Test
    public void jsonValid() {

        def csbMetadataInput = '{"shipname":"Kilo Moana","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"Linblad"}'
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( csbMetadataInput )
        assert ss.validate( csbMetadataInput ) == true

    }

    @Test
    public void jsonNotValid() {

        def csbMetadataInput = '{"shipname":"","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"Linblad"}'
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( csbMetadataInput )
        assert ss.validate( csbMetadataInput ) == false

    }

}
