staging.dir.key=CSBFILES
staging.dir.value="/tmp/dev/csb"
log.location="/tmp/dev/log"
log.level="DEBUG"

environments {

    dev {

        staging.dir.key="CSBFILES"
        staging.dir.value="/tmp/dev/csb"
        staging.dir.map=[ CSBFILES : "/tmp/dev/csb" ]
        log.location="/tmp/dev/log"

    }

    test {

        staging.dir.key="CSBFILES"
        staging.dir.value="/tmp/test/csb"
        staging.dir.map=[ CSBFILES : "/tmp/test/csb" ]
        log.location="/tmp/test/log"

    }

    prod {

        staging.dir.key="CSBFILES"
        staging.dir.value="/tmp/prod/csb"
        staging.dir.map=[ CSBFILES : "/tmp/prod/csb" ]
        log.location="/tmp/prod/log"

    }

}


