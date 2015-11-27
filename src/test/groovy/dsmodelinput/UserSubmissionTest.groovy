package dsmodelinput

import appconfig.UserSubmissionConfig

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UserSubmissionConfig.class)
public class UserSubmissionTest {

    @Autowired
    private IUserSubmission us

    @Test
    public void usShouldNotBeNull() {
        assertNotNull(us)
    }

    @Test
    public void getStagingDirsCount() {

        def v = us.stagingDirsCount
        assert v >= 1

    }

    @Test
    public void transformSad() {

        def userEntries = [:]
        userEntries << [ FILE : null ]
        userEntries << [ JSON : '{ foo: "bar" }' ]
        def hm = us.transform( userEntries )
        def v = hm.TRANSFORMED
        assert v == "You failed to upload because the file was missing or empty."

    }

}
