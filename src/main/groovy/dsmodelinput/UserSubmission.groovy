package dsmodelinput

import org.apache.catalina.User
import org.springframework.stereotype.Component

import javax.servlet.http.Part

@Component
public class UserSubmission implements IUserSubmission {

    private hmStagingDirs = [:]

    public UserSubmission() {
        init()
    }

    private void init() {

        // Read from values from config server or properties file
        this.hmStagingDirs << [ CSBFILES : "/tmp/csb" ]

        // Create directories if they don't already exist
        hmStagingDirs.each { key, value ->
            def file = new File("${value}")
            file.exists()?:file.mkdirs()
        }

    }

    def getStagingDirs() {

        return this.hmStagingDirs

    }

    int getStagingDirsCount() {

        return hmStagingDirs.size()

    }

    Map transform( Map userEntries ) {

        def hmMsg = [ : ]
        def csbMetadataInput = userEntries.JSON
        Part file = userEntries.FILE
        println hmStagingDirs.size()

        try {

            if ( file != null && file.size > 0 ) {

                def filename = "csb_" + UUID.randomUUID() + ".xyz"
                println "${csbMetadataInput} : ${hmStagingDirs.CSBFILES}/${filename}"
                file.write "${hmStagingDirs.CSBFILES}/${filename}"
                hmMsg << [ TRANSFORMED : "Your file ${file.submittedFileName} has been received!" ]

            } else {

                hmMsg << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]

            }

        } catch (Exception e) {

            hmMsg << [ TRANSFORMED : "You failed to upload ${file.submittedFileName} + ${e.message}" ]

        } finally {

            return hmMsg

        }

    }

}