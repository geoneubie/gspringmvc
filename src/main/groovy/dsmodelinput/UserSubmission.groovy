package dsmodelinput

import org.springframework.stereotype.Component

import javax.servlet.http.Part


@Component
public class UserSubmission implements IUserSubmission {

    def hmStagingDirs = [ : ]

    public setStagingDirs( hmStagingDirs ) {
        this.hmStagingDirs = hmStagingDirs
    }

    int getStagingDirsCount() {
        return hmStagingDirs.size()
    }

    Map transform( Map userEntries ) {

        def hm = [ : ]
        def csbMetadataInput = userEntries.JSON
        Part file = userEntries.FILE

        try {

            if ( file != null && file.size > 0 ) {

                def filename = "csb_" + UUID.randomUUID() + ".xyz"
                println "${csbMetadataInput} : ${hmStagingDirs.CSBFILES}/${filename}"
                file.write "${hmStagingDirs.CSBFILES}/${filename}"
                hm << [ TRANSFORMED : "Your file ${file.submittedFileName} has been received and stored as  ${filename}!!" ]

            } else {

                hm << [ TRANSFORMED : "You failed to upload because the file was missing or empty." ]

            }

        } catch (Exception e) {

            hm << [ TRANSFORMED : "You failed to upload ${file.submittedFileName} + e.getMessage()" ]

        } finally {

            return hm

        }

    }

}