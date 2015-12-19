package csb.repos

import csb.config.AppConfig
import csb.config.SecurityConfig
import csb.model.security.Role
import csb.model.security.User
import csb.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=[AppConfig.class, SecurityConfig.class] )
class UserRepositoryTest {

    private static final Logger logger =
            LoggerFactory.getLogger( UserRepositoryTest.class )

    @Autowired
    private IUserRepository iuserRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    @Test
    public void encrypt() {
        String encoded = passwordEncoder.encode( "Sea-ID" )
        logger.debug("Encoded password: ${encoded}")

    }

    @Test
    public void saveUser() {

        UserService userService = new UserService()

        User user
        Role role = Role.USER

        user = new User()
        user.username = "Sea-ID"
        user.enabled = true
        user.password = passwordEncoder.encode( "Sea-ID" )
        user.role = role

        user = iuserRepository.save( user )
        logger.debug("Saved User - name: ${user.username}")
        assert( user != null )
    }

}


