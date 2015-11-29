package csb.service

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 11/24/15.
 */
@Component
public interface ISubmitService {

    Map transform( Map userEntries )

}