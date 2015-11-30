import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

def activeProfile = System.getProperty "spring.profiles.active"
def config = new ConfigSlurper("${activeProfile}").parse(new File('config/appConfig.groovy').toURI().toURL())

appender("STDOUT", ConsoleAppender) {

    encoder(PatternLayoutEncoder) {
        pattern = "%-4relative [%thread] %level %logger - %msg%n"
    }

}

appender("FILE", FileAppender) {

    file = "${config.log.location}/csb.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }

}

switch (activeProfile) {

    case "dev":
        logger("csb", DEBUG, [ "STDOUT", "FILE" ], false)
        break
    case "test":
        logger("csb", INFO, [ "STDOUT", "FILE" ], false)
        break
    case "prod":
        logger("csb", WARN, [ "FILE" ], false)
        break
    default:
        logger("csb", INFO, [ "STDOUT", "FILE" ], false)

}

root(WARN, ["STDOUT"])