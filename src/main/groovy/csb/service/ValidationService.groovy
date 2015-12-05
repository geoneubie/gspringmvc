package csb.service
import groovy.json.JsonSlurper
import org.springframework.stereotype.Service

import javax.servlet.http.Part
/**
 * Created by dneufeld on 11/24/15.
 */

@Service
class ValidationService<T> implements IValidateService {

    private T t

    public ValidationService(T t) {

        this.t = t

    }

    public boolean validate() {

        this.validate(t)

    }

    boolean validate( String json ) {

        boolean valid = false
        def jsonSlurper = new JsonSlurper()
        def cmiMap = jsonSlurper.parseText( json )

        // Required fields from user input form
        valid = (cmiMap.shipname!="")?true:false
        valid = (cmiMap.dataProvider!="" && valid)?true:false

        return valid

    }

    boolean validate( File storedFile ) {

        boolean valid = false
        def br = storedFile.newReader()

        try {
            // Assume first line header
            String line = br.readLine()
            line = br.readLine()
            def tokens = []
            def pts = []

            tokens = line.tokenize( ',' )
            def lat = Double.parseDouble(tokens[0])
            def lon = Double.parseDouble(tokens[1])
            def z = Double.parseDouble(tokens[2])
            valid = true

        } catch (Exception e) {

            valid = false

        } finally {

            return valid

        }

    }

    boolean validate( Part uploadFile ) {

        boolean valid = false
        if ( uploadFile != null && uploadFile.size > 0 ) {
            valid = true
        }
        return valid

    }

}