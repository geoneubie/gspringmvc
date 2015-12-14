package csb.repos

import csb.model.DataProviderEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by dneufeld on 12/13/15.
 */
interface IDataProviderRepository extends JpaRepository<DataProviderEntity, Long> {

    List<DataProviderEntity> findByName( String name )

}
