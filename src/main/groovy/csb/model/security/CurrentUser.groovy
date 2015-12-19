package csb.model.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.authority.AuthorityUtils

class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user

    public CurrentUser(User user) {
        super( user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList (user.getRole().toString() ) )
        this.user = user
    }

    public User getUser() {
        return user
    }

    public Long getId() {
        return user.getId()
    }

    public Role getRole() {
        return user.getRole()
    }

}
