package csb.repos

import csb.model.DataProvider
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional


@Transactional
interface IDataProviderRepository extends JpaRepository<DataProvider, Long> {

    // Specific custom query adds
    @Cacheable( value="dpeCache" )
    DataProvider findByName( String name )

    @Cacheable( value="dpeCache" )
    List<DataProvider> findAll()

    @CachePut( value="dpeCache", key="#result.id" )
    DataProvider save( DataProvider dpe )

    @CacheEvict( value="dpeCache" )
    void deleteAll()
}
