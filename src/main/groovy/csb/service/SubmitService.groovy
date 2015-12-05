package csb.service
import csb.dsmodelinput.Staging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.Part

@Service
class SubmitService implements ITransformService {

    private static final Logger logger =
            LoggerFactory.getLogger( SubmitService.class )

    @Autowired
    private Staging stagingDirs

    public SubmitService( Staging stagingDirs ) {
        this.stagingDirs = stagingDirs
    }

    Map transform( Map userEntries ) throws Exception {

        def hmMsg = [:]
        def mapStagingDirs = this.stagingDirs.map
        def csbMetadataInput = userEntries.JSON
        Part uploadFile = userEntries.FILE
        ValidationService<?> vs = new ValidationService<Part>( uploadFile )
        boolean validFile = vs.validate()

        if ( validFile ) {

            def baseFilename = "${mapStagingDirs.CSBFILES}/csb_${UUID.randomUUID()}"
            uploadFile.write "${baseFilename}.xyz"
            def storedFile = new File( "${baseFilename}.xyz" )
            vs = new ValidationService<File>( storedFile )
            def validContent = vs.validate()
            if ( validContent ) {
                def metaFile = new File( "${baseFilename}_meta.json" )
                metaFile.write csbMetadataInput

                // Processing data still needed for next transform
                hmMsg << [ TRANSFORMED : "Your file ${uploadFile.submittedFileName} has been received!" ]
                hmMsg << [ BASEFILENM : "${baseFilename}" ]
                hmMsg << [ JSON : csbMetadataInput ]
            } else {
                hmMsg << [ TRANSFORMED : "Your file content is invalid." ]
                hmMsg << [ ERROR : "BAD CONTENT"]
            }

        } else {

            hmMsg << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]
            hmMsg << [ ERROR : "NO CONTENT"]
        }

        return hmMsg

    }

}