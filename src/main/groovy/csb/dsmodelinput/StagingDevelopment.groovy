package csb.dsmodelinput

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 11/27/15.
 */
@Component
class StagingDevelopment extends Staging {

    @Override
    public void init() {

        // Read from values from config server or properties file
        this.hmStagingDirs << [ CSBFILES : "/tmp/dev/csb" ]

    }
}
