package csb.repos
import csb.config.AppConfig
import csb.config.SecurityConfig
import csb.model.security.Role
import csb.model.security.User
import csb.service.security.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=[AppConfig.class, SecurityConfig.class] )
class UserRepositoryTest {

    private static final Logger logger =
            LoggerFactory.getLogger( UserRepositoryTest.class )

    @Autowired
    private UserService userService

    @Test
    public void saveUser() {

        User user
        Role role = Role.USER

        user = new User()
        user.username = "Dave"
        user.enabled = true
        user.password = "Dave"
        user.role = role

        userService.save( user )
        logger.debug("Saved User: ${user}")
        assert( user != null )

    }

    @Test
    public void seed() {
        userService.seed()
    }
}


