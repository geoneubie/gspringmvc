package csb.service

import csb.dsmodelinput.Staging
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.Part

@Service
class SubmitService implements ISubmitService {

    private static final Logger logger =
            LoggerFactory.getLogger(SubmitService.class);

    @Autowired
    private Staging stagingDirs

    public SubmitService( Staging stagingDirs ) {
        this.stagingDirs = stagingDirs
    }

    Map transform( Map userEntries ) throws Exception {

        //Assume error condition, change msg if code succeeds
        def hmMsg = [:]
        def mapStagingDirs = this.stagingDirs.map
        def csbMetadataInput = userEntries.JSON
        Part uploadFile = userEntries.FILE

        if ( uploadFile != null && uploadFile.size > 0 ) {

            def baseFilename = "${mapStagingDirs.CSBFILES}/csb_${UUID.randomUUID()}"
            uploadFile.write "${baseFilename}.xyz"

            def metaFile = new File( "${baseFilename}.json" )
            metaFile.write csbMetadataInput

            hmMsg << [ TRANSFORMED : "Your file ${uploadFile.submittedFileName} has been received!" ]

        } else {

            hmMsg << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]

        }

        return hmMsg

    }

    boolean validateJSON( String csbMetadataInput ) {

        boolean valid = false
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( csbMetadataInput )

        valid = (cmiMap.shipname!="")?true:false
        valid = (cmiMap.dataProvider!="" && valid)?true:false

        return valid

    }

}