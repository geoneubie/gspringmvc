package csb.service.security

import com.sun.org.apache.xalan.internal.xsltc.compiler.Sort
import csb.model.security.User
import csb.repos.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by dneufeld on 12/18/15.
 */
class UserService implements IUserService {

    private static final Logger logger =
            LoggerFactory.getLogger( UserService.class )

    private final IUserRepository iuserRepository

    @Autowired
    public UserService( IUserRepository iuserRepository ) {

        this.iuserRepository = iuserRepository

    }

    @Override
    public Optional<User> getUserById( long id ) {

        logger.debug( "Getting user id ${id}" )
        return Optional.ofNullable( iuserRepository.findOne( id ) )

    }

    @Override
    public Optional<User> getUserByUsername( String username ) {

        logger.debug( "Getting user by username ${username}" )
        return iuserRepository.findOneByUsername( username )

    }

    @Override
    public Collection<User> getAllUsers() {

        logger.debug("Getting all users")
        return iuserRepository.findAll( new Sort( "username" ) )

    }

    public User create( User user ) {

        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword() ) )
        return iuserRepository.save( user )

    }

}
