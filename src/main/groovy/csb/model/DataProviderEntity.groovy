package csb.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class DataProviderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id

    private String name
    private String providerEmail
    private String processorEmail
    private String ownerEmail

    protected DataProviderEntity() {

    }


    public DataProviderEntity( String name, String providerEmail, String processorEmail, String ownerEmail ) {

        this.name = name
        this.providerEmail = providerEmail
        this.processorEmail = processorEmail
        this.ownerEmail = ownerEmail

    }

    public getName() {
        return this.name
    }

    public setName( String name ) {
        this.name = name
    }

    public getProviderEmail() {
        return this.providerEmail
    }

    public setProviderEmail( String providerEmail ) {
        this.providerEmail = providerEmail
    }

    public getProcessorEmail() {
        return this.processorEmail
    }

    public setProcessorEmail( String processorEmail ) {
        this.processorEmail = processorEmail
    }

    public getOwnerEmail() {
        return this.ownerEmail
    }

    public setOwnerEmail( String ownerEmail ) {
        this.ownerEmail = ownerEmail
    }

    @Override
    public String toString() {
        return "${id}:${name}:${providerEmail}:${processorEmail}:${ownerEmail}"
    }

}
