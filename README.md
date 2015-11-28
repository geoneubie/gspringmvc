# gspringmvc
Sample groovy/gradle spring mvc and sprint boot app supports file upload.

The bootRun task has been configured to read in the environment, so for example to run as test environment use:
gradle -PjvmArgs="-Dspring.profiles.active=test" bootRun

bootRun defaults to dev if no args passed

