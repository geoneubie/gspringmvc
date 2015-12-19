package csb.model

import csb.model.security.CurrentUser
import csb.model.security.Role
import csb.model.security.User
import org.junit.Test

/**
 * Created by dneufeld on 12/8/15.
 */
class CurrentUserTest {

    @Test
    void currentUserCreate() {

        User user
        Role role = Role.USER

        user = new User()
        user.username = "Dave"
        user.enabled = true
        user.password = "Dave"
        user.role = role

        CurrentUser cu = new CurrentUser( user )

        assert cu.getUser().username == "Dave"
    }

}
