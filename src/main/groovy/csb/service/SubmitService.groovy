package csb.service

import csb.dsmodelinput.Staging
import groovy.json.JsonSlurper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.Part

@Service
class SubmitService implements ITransformService {

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

        if ( uploadFile != null && uploadFile.size > 0 ) {

            def baseFilename = "${mapStagingDirs.CSBFILES}/csb_${UUID.randomUUID()}"
            uploadFile.write "${baseFilename}.xyz"

            def metaFile = new File( "${baseFilename}_meta.json" )
            metaFile.write csbMetadataInput

            // Processing data still needed for next transform
            hmMsg << [ TRANSFORMED : "Your file ${uploadFile.submittedFileName} has been received!" ]
            hmMsg << [ BASEFILENM : "${baseFilename}" ]
            hmMsg << [ JSON : csbMetadataInput ]

        } else {

            hmMsg << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]

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


}