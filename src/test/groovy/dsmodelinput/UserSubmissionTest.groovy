package dsmodelinput
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
        assert v == 1
    }

    @Test
    public void transform() {
        def hm = us.transform()
        def v = hm.TRANSFORMED
        assert v == "Playing Sgt. Pepper's Lonely Hearts Club Band" +
                " by The Beatles"
    }
}
