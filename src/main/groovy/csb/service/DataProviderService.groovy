package csb.service

import csb.controller.FileUploadController
import csb.model.DataProviderEntity
import csb.model.DataProviders
import csb.repos.IDataProviderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class DataProviderService {

    private static final Logger logger =
            LoggerFactory.getLogger( DataProviderService.class )

    @Autowired
    private IDataProviderRepository idpRepository;

    @Transactional( readOnly=true )
    public DataProviderEntity distinctFindByName( name ) {

        return idpRepository.findByName( name )

    }

    @Transactional( readOnly=true )
    public List<DataProviderEntity> findAll() {

        return idpRepository.findAll()

    }

    @Transactional( readOnly=true )
    public DataProviders getAllDataProviders() {

        DataProviders dps = new DataProviders()
        for ( DataProviderEntity dpe : idpRepository.findAll() ) {
            dps.addProvider( dpe.name, dpe)
        }

        return dps

    }

    @Transactional
    public void save( DataProviderEntity dpe ) {

        idpRepository.save( dpe )
        logger.debug("Saved DataProvider - name: ${dpe.name}")

    }

    @Transactional
    public void seed() {

        DataProviderEntity dpe

        dpe = new DataProviderEntity()
        dpe.name = "Sea-ID"
        dpe.providerEmail = "support@sea-id.org"
        dpe.providerUrl = "https://www.sea-id.org"
        dpe.processorEmail = "support@sea-id.org"
        dpe.ownerEmail = "support@sea-id.org"
        idpRepository.save(dpe)
        logger.debug( "Saved DataProvider - name: ${dpe.name}" )

        dpe = new DataProviderEntity()
        dpe.name = "LINBLAD"
        dpe.providerEmail = "support@expeditions.com"
        dpe.providerUrl = "http://www.expeditions.com"
        dpe.processorEmail = "support@expeditions.com"
        dpe.ownerEmail = "support@expeditions.com"
        idpRepository.save(dpe)
        logger.debug( "Saved DataProvider - name: ${dpe.name}" )

    }

    @Transactional
    public void deleteAll() {
        idpRepository.deleteAll()
        idpRepository.flush()
    }

}
