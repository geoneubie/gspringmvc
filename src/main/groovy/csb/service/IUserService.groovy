package csb.service

import csb.model.security.User

interface IUserService {

    Optional<User> getUserById( long id )

    Optional<User> getUserByUsername( String username )

    Collection<User> getAllUsers()

    User create( User user )

}