package dsmodelinput

/**
 * Created by dneufeld on 11/27/15.
 */

class StagingDevelopment extends Staging {

    @Override
    public void init() {

        // Read from values from config server or properties file
        this.hmStagingDirs << [ CSBFILES : "/tmp/dev/csb" ]

    }
}
