package csb.repos

import csb.model.security.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface IUserRepository extends JpaRepository<User, Long> {

    // Specific custom query adds
    User findOneByUsername( String username )

    User save( User user )

}
