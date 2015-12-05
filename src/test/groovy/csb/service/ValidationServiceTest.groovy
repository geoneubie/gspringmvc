package csb.service
import csb.config.AppConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
/**
 * Created by dneufeld on 12/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@ComponentScan( basePackages=[ "csb.config"] )
class ValidationServiceTest {

    @Test
    public void validateFileTest() {

        File f = File.createTempFile("myTestFile", ".tmp")
        ValidationService<File> vsf = new ValidationService<File>(f)
        assert vsf.validate()==false

    }

    @Test
    public void jsonValid() {

        def csbMetadataInput = '{"shipname":"Kilo Moana","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"Linblad"}'
        ValidationService<String> vs = new ValidationService<String>( csbMetadataInput )
        assert vs.validate() == true

    }

    @Test
    public void jsonNotValid() {

        def csbMetadataInput = '{"shipname":"","soundermake":"","imonumber":"","soundermodel":"","draft":"","sounderserialno":"","longitudinalOffsetFromGPStoSonar":"","lateralOffsetFromGPStoSonar":"","velocity":"","gpsmake":"","gpsmodel":"","dataProvider":"Linblad"}'
        ValidationService<String> vs = new ValidationService<String>( csbMetadataInput )
        assert vs.validate() == false

    }




}


