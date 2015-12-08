staging.dir.map=[ CSBFILES : "/tmp/dev/csb/" ]
log.location="/tmp/dev/log"

data.providers=[
        [ uid: "1", name: "SEAID", providerEmail: "support@sea-id.org",
                 processorEmail: "support@sea-id.org", ownerEmail: "support@sea-id.org" ],
        [ uid: "2", name: "LINBLAD", providerEmail: "support@expeditions.com",
          processorEmail: "support@expeditions.com", ownerEmail: "support@expeditions.com" ]
]

environments {

    dev {

        staging.dir.map=[ CSBFILES : "/tmp/dev/csb/" ]
        log.location="/tmp/dev/log"

    }

    test {

        staging.dir.map=[ CSBFILES : "/tmp/test/csb/" ]
        log.location="/tmp/test/log"

    }

    prod {

        staging.dir.map=[ CSBFILES : "/tmp/prod/csb/" ]
        log.location="/tmp/prod/log"

    }

}


