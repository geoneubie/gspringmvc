package csb.service
import csb.model.Staging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class SubmitService implements ITransformService {

    private Staging stagingDirs

    public SubmitService () {}

    public SubmitService( Staging stagingDirs ) {
        this.stagingDirs = stagingDirs
    }

    Map transform( Map userEntries ) throws Exception {

        def hmMsg = [:]
        def mapStagingDirs = this.stagingDirs.map
        def csbMetadataInput = userEntries.JSON
        MultipartFile uploadFile = userEntries.FILE
        ValidationService<?> vs = new ValidationService<MultipartFile>( uploadFile )
        // Check if file is empty
        boolean validFile = vs.validate()

        if ( validFile ) {

            def baseFilename = "${mapStagingDirs.CSBFILES}/csb_${UUID.randomUUID()}"

            def storedFile = new File( "${baseFilename}.xyz" )
            // Write incoming file to disk
            uploadFile.transferTo( storedFile )

            // Crack the file to peek at content
            vs = new ValidationService<File>( storedFile )

            // Check if file is CSV with numeric values for lat, lon, and depth
            def validContent = vs.validate()

            if ( validContent ) {
                def metaFile = new File( "${baseFilename}_meta.json" )
                // Write incoming JSON to disk
                metaFile.write csbMetadataInput

                // Processing data still needed for next transform
                def origFileNm = "${uploadFile.originalFilename}"

                hmMsg << [ TRANSFORMED : "Your file ${origFileNm} has been received!" ]
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