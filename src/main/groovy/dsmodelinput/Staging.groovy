package dsmodelinput

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 11/27/15.
 */
@Component
class Staging {

    protected def hmStagingDirs = [:]
    protected String env

    public Staging() {
        init()
        createDirs()
    }

    void setEnvironment(String env) {

        this.env = env

    }

    Map getMap() {

        return this.hmStagingDirs

    }

    void createDirs() {

        // Create directories if they don't already exist
        hmStagingDirs.each { key, value ->
            def file = new File("${value}")
            file.exists()?:file.mkdirs()
        }

    }

    public void init() {}

}
