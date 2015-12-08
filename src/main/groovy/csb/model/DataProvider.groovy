package csb.model

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 12/4/15.
 */
@Component
class DataProvider {

    public String uid
    public String name
    public String providerEmail
    public String processorEmail
    public String ownerEmail

    public String toString() {
        return "${uid}:${name}:${providerEmail}:${processorEmail}:${ownerEmail}"
    }

}
