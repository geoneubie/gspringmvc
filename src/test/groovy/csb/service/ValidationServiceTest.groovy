package csb.service

import org.junit.Test
/**
 * Created by dneufeld on 12/4/15.
 */
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


