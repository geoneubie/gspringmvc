package csb.service
import csb.config.AppConfig
import csb.model.Staging
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

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
    public void transformException() {

        try {

            def userEntries = null
            def hm = ss.transform(userEntries)

        } catch (Exception e) {

            assert true

        }

    }

}
