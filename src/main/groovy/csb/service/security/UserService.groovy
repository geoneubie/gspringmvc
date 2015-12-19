package csb.service.security

import com.sun.org.apache.xalan.internal.xsltc.compiler.Sort
import csb.model.security.Role
import csb.model.security.User
import csb.repos.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional

class UserService implements IUserService {

    private static final Logger logger =
            LoggerFactory.getLogger( UserService.class )

    @Autowired
    private IUserRepository iuserRepository

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

    @Transactional
    public User create( User user ) {

        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword() ) )
        return iuserRepository.save( user )

    }

    public void seed() {

        User user

        user = new User()
        user.username = "Sea-ID"
        user.enabled = true
        user.password = "Sea-ID"
        user.role(Role.USER)
        user = this.create( user )
        logger.debug( "Saved User - username: ${user.username}" )

        user = new User()
        user.username = "LINBLADT"
        user.enabled = true
        user.password = "LINBLADT"
        user.role(Role.USER)
        user = this.create( user )
        logger.debug( "Saved User - username: ${user.username}" )

    }

}
