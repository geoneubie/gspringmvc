package csb.repos

import csb.model.DataProviderEntity
//import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.JpaRepository

import javax.xml.crypto.Data

/**
 * Created by dneufeld on 12/13/15.
 */
interface IDataProviderRepository extends JpaRepository<DataProviderEntity, Long> {

    // Specific custom query adds
    DataProviderEntity findByName(String name)

}
