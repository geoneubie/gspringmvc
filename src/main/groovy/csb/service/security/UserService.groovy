package csb.service.security

import com.sun.org.apache.xalan.internal.xsltc.compiler.Sort
import csb.model.security.Role
import csb.model.security.User
import csb.repos.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.transaction.annotation.Transactional

class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger( UserService.class )

    @Autowired
    private IUserRepository iuserRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    @Transactional( readOnly=true )
    public User getUserById( long id ) {

        logger.debug( "Getting user id ${id}" )
        return iuserRepository.findOne( id )

    }

    @Transactional( readOnly=true )
    public User findOneByUsername( String username ) {

        logger.debug( "Getting user by username ${username}" )
        return iuserRepository.findOneByUsername( username )

    }

    @Transactional( readOnly=true )
    public Collection<User> getAllSortedUsers() {

        logger.debug("Getting all users and sorting them")
        return iuserRepository.findAll( new Sort( "username" ) )

    }

    @Transactional
    public void save( User user ) {
        user.password = passwordEncoder.encode(user.getPassword() )
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

        logger.debug( "Saved User - username: ${user}" )
        this.save( user )

        user = new User()
        user.username = "LINBLADT"
        user.enabled = true
        user.password = "LINBLADT"
        user.role = role

        this.save( user )
        logger.debug( "Saved User - username: ${user}" )
    }

}
