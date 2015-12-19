package csb.service.security

import csb.model.security.CurrentUser
import csb.model.security.Role
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CurrentUserService implements ICurrentUserService {

    private static final Logger logger =
            LoggerFactory.getLogger( CurrentUserService.class )

    @Override
    public boolean canAccessUser( CurrentUser currentUser, long userId ) {
        logger.debug( "Checking if user has access to user=${currentUser}, ${userId}" )
        return ( currentUser != null
            && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals( userId ) ) )
    }

}
