package csb.repos

import csb.model.security.User
import org.springframework.data.jpa.repository.JpaRepository

interface IUserRepository extends JpaRepository<User, Long> {

    // Specific custom query adds
    Optional<User> findOneByUsername(String username)

}
