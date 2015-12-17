package csb.repos

import csb.model.DataProviderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional


@Transactional
interface IDataProviderRepository extends JpaRepository<DataProviderEntity, Long> {

    // Specific custom query adds
    DataProviderEntity findByName( String name )

}
