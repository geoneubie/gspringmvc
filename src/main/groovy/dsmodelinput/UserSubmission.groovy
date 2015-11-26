package dsmodelinput

import org.springframework.stereotype.Component

@Component
public class UserSubmission implements IUserSubmission {

    def title = "Sgt. Pepper's Lonely Hearts Club Band"
    def artist = "The Beatles"
    def hmStagingDirs = [ : ]

    public setStagingDirs( hmStagingDirs ) {
        this.hmStagingDirs = hmStagingDirs
    }

    int getStagingDirsCount() {
        return hmStagingDirs.size()
    }

    Map transform( Map userEntries ) {
        def hm = [ : ]
        hm << [ TRANSFORMED : "Playing ${title} by ${artist}" ]
        return hm
    }
}