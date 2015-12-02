package csb.service

import org.springframework.stereotype.Service

/**
 * Created by dneufeld on 11/24/15.
 */
@Service
interface ISubmitService {

    Map transform( Map userEntries )

}