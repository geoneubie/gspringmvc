package csb.bootstrap
import csb.config.AppConfig
import csb.model.security.Role
import csb.model.security.User
import csb.repos.IUserRepository
import csb.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class UserInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger =
            LoggerFactory.getLogger(UserInitializer.class)

    private UserService userService = new UserService()

    @Autowired
    private IUserRepository iUserRepository

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (AppConfig.activeProfile != "prod") {
            logger.debug( "Bootstrapping initial Users..." )
            User user
            Role role = Role.USER

            user = new User()
            user.username = "Sea-ID"
            user.enabled = true
            user.password = "Sea-ID"
            user.role = role

            logger.debug( "Saved User - username: ${user.username}" )
            iUserRepository.save( user )
        }

    }

}