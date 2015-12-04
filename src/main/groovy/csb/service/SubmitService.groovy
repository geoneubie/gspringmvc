package csb.service

import csb.dsmodelinput.Staging
import groovy.json.JsonSlurper
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
        boolean validFile = this.validateFile( uploadFile )

        if ( validFile ) {

            def baseFilename = "${mapStagingDirs.CSBFILES}/csb_${UUID.randomUUID()}"
            uploadFile.write "${baseFilename}.xyz"
            def storedFile = new File( "${baseFilename}.xyz" )
            def validContent = this.validateContent( storedFile )
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

    boolean validate( String s ) {

        boolean valid = false
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( s )

        valid = (cmiMap.shipname!="")?true:false
        valid = (cmiMap.dataProvider!="" && valid)?true:false

        return valid

    }

    boolean validateContent( File storedFile ) {
        boolean valid = false
        def br = storedFile.newReader()

        try {
            // Assume first line header
            String line = br.readLine()
            line = br.readLine()
            def tokens = []
            def pts = []

            tokens = line.tokenize( ',' )
            def lat = Double.parseDouble(tokens[0])
            def lon = Double.parseDouble(tokens[1])
            def z = Double.parseDouble(tokens[2])
            valid = true

        } catch (Exception e) {

            valid = false
            logger.debug( "Exception in validateContent ${e.message}" )

        } finally {

            return valid

        }

    }

    boolean validateFile( Part uploadFile ) {

        boolean valid = false
        if ( uploadFile != null && uploadFile.size > 0 ) {
            valid = true
        }
        return valid


    }

}