package csb.service
import csb.config.AppConfig
import csb.model.Staging
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.multipart.MultipartFile

import static org.junit.Assert.assertNotNull

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
class SubmitServiceTest {

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
    public void transformHappy() {

        def csbMetadataInput = '{"shipname":"Kilo Moana","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"SEAID"}'
        def fileBytes = "LAT, LON, BAT_TTIME\n42.9946, -50.2891, 99.9".getBytes()
        MultipartFile uploadFile = new MockMultipartFile("file", "xyzfile.xyz", null, fileBytes);

        def userEntries = [:]
        userEntries << [ FILE : uploadFile ]
        userEntries << [ JSON : csbMetadataInput ]
        def hm = ss.transform( userEntries )
        def v = hm.TRANSFORMED
        assert v == "Your file xyzfile.xyz has been received!"

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

}
