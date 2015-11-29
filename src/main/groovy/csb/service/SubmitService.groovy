package csb.service

import csb.dsmodelinput.Staging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import javax.servlet.http.Part

@Component
public class SubmitService implements ISubmitService {

    @Autowired
    private Staging stagingDirs

    protected String foo

    public void setFoo(String bar) {
        foo = bar
    }

    public SubmitService(Staging stagingDirs) {
        this.stagingDirs = stagingDirs
    }

    Map transform( Map userEntries ) {
        println "foo=${foo}"
        def hmMsg = [ : ]
        def mapStagingDirs = this.stagingDirs.map
        def csbMetadataInput = userEntries.JSON
        Part file = userEntries.FILE

        try {

            if ( file != null && file.size > 0 ) {

                def filename = "csb_" + UUID.randomUUID() + ".xyz"
                println "${csbMetadataInput} : ${mapStagingDirs.CSBFILES}/${filename}"
                file.write "${mapStagingDirs.CSBFILES}/${filename}"
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