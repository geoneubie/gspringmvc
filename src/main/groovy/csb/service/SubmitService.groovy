package csb.service

import csb.dsmodelinput.Staging
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.Part

@Service
public class SubmitService implements ISubmitService {

    private static final Logger logger =
            LoggerFactory.getLogger(SubmitService.class);

    @Autowired
    private Staging stagingDirs

    public SubmitService( Staging stagingDirs ) {
        this.stagingDirs = stagingDirs
    }

    Map transform( Map userEntries ) {

        def hmMsg = [ : ]
        def mapStagingDirs = this.stagingDirs.map
        def csbMetadataInput = userEntries.JSON
        Part file = userEntries.FILE

        try {

            if ( file != null && file.size > 0 ) {

                def filename = "csb_${UUID.randomUUID()}.xyz"
                logger.debug "Local log statement - ${csbMetadataInput} : ${mapStagingDirs.CSBFILES}/${filename}"
                file.write "${mapStagingDirs.CSBFILES}/${filename}"
                hmMsg << [ TRANSFORMED : "Your file ${file.submittedFileName} has been received!" ]

            } else {

                hmMsg << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]

            }

        } catch ( Exception e ) {

            hmMsg << [ TRANSFORMED : "You failed to upload ${file.submittedFileName} + ${e.message}" ]

        } finally {

            return hmMsg

        }

    }

}