package csb.util

/**
 * Created by dneufeld on 12/19/15.
 */
class CsbUtil {

    static def returnJsonValue( String input ) {
        //Orignal value or Double if numeric
        ( input.empty )?input:new Double( input )
    }
}
