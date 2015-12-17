package csb.repos

import csb.model.DataProviderEntity
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional


@Transactional
interface IDataProviderRepository extends JpaRepository<DataProviderEntity, Long> {

    // Specific custom query adds
    @Cacheable( value="dpeCache" )
    DataProviderEntity findByName( String name )

    @Cacheable( value="dpeCache" )
    List<DataProviderEntity> findAll()

    @CachePut( value="dpeCache", key="#result.id" )
    DataProviderEntity save( DataProviderEntity dpe )

    @CacheEvict( value="dpeCache" )
    void deleteAll()
}
