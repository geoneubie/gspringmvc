package csb.dsmodelinput

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 11/27/15.
 */
@Component
class Staging {

    def hmStagingDirs = [:]

    public Staging( Map hmStagingDirs ) {

        this.hmStagingDirs = hmStagingDirs
        createDirs()

    }

    Map getMap() {

        return this.hmStagingDirs

    }

    void createDirs() {

        // Create directories if they don't already exist
        hmStagingDirs.each { key, value ->
            def file = new File( "${value}" )
            file.exists()?:file.mkdirs()
        }

    }

}
