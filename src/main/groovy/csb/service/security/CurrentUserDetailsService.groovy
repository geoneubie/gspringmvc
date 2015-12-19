package csb.service.security

import csb.model.security.CurrentUser
import csb.model.security.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CurrentUserDetailsService implements UserDetailsService {

    private static final Logger logger =
            LoggerFactory.getLogger( CurrentUserDetailsService.class )

    @Autowired
    private final UserService userService


    public CurrentUserDetailsService( UserService userService ) {

        this.userService = userService

    }

    @Override
    public CurrentUser loadUserByUsername( String username ) throws UsernameNotFoundException {

        logger.debug( "Authenticating user with username=${username}" )
        User user = userService.getUserByUsername( username )
                .orElseThrow( new UsernameNotFoundException( "User with username=${username} was not found" ) )
        return new CurrentUser( user )

    }

}
