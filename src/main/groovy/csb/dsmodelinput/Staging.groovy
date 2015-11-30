package csb.dsmodelinput

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 11/27/15.
 */
@Component
class Staging {

    protected def hmStagingDirs = [:]

    public Staging() {
        createDirs()
    }

    public void setStagingDirs( Map hmStagingDirs ) {

        // Read from values from config server or properties file
        this.hmStagingDirs = hmStagingDirs

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
