package csb.service

import org.springframework.stereotype.Service

import java.lang.invoke.MethodHandleImpl


/**
 * Created by dneufeld on 11/24/15.
 */
@Service
interface ITransformService {

    Map transform( Map userEntries )

    boolean validate( String s )
}