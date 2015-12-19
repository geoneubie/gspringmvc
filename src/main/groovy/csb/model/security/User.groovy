package csb.model.security


import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id

    @Column( name = "username", unique = true, nullable = false, length = 45 )
    private String username

    @Column( name = "password", nullable = false, length = 60 )
    private String password

    @Column( name = "enabled", nullable = false )
    private boolean enabled

    @Column( name = "role", nullable = false )
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User( String username, String password, boolean enabled, Role role ) {
        this.username = username
        this.password = password
        this.enabled = enabled
        this.role = role
    }

    public String getUsername() {

        return this.username

    }

    public void setUsername( String username ) {

        this.username = username

    }

    public String getPassword() {

        return this.password

    }

    public void setPassword( String password ) {

        this.password = password

    }

    public boolean isEnabled() {

        return this.enabled

    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled
    }

    public Role getRole() {

        return this.role

    }

    public void setUserRole( Role role ) {

        this.role = role

    }

}
