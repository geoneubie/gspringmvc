package csb.service

import com.sun.org.apache.xalan.internal.xsltc.compiler.Sort
import csb.model.security.Role
import csb.model.security.User
import csb.repos.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

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

    public User create( User user ) {
        logger.debug( "Create User - username: ${user.username}" )

        //user.setPassword( passwordEncoder.encode(user.getPassword() ) )
        return user

    }

    @Transactional
    public void save( User user ) {

        iuserRepository.save( user )
        logger.debug("Saved User - name: ${user.username}")

    }

    public void seed() {

        User user
        Role role = Role.USER

        user = new User()
        user.username = "Sea-ID"
        user.enabled = true
        user.password = "Sea-ID"
        user.role = role
        user = this.create( user )
        logger.debug( "Saved User - username: ${user.username}" )
        this.save( user )

        user = new User()
        user.username = "LINBLADT"
        user.enabled = true
        user.password = "LINBLADT"
        user.role = role
        user = this.create( user )
        logger.debug( "Saved User - username: ${user.username}" )
        this.save( user )

    }

}
