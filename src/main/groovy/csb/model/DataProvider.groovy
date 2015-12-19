package csb.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class DataProvider implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long id

    private String name
    private String providerEmail
    private String providerUrl
    private String processorEmail
    private String ownerEmail

    protected DataProvider() {

    }


    public DataProvider( String name, String providerEmail, String providerUrl, String processorEmail, String ownerEmail ) {

        this.name = name
        this.providerEmail = providerEmail
        this.providerUrl = providerUrl
        this.processorEmail = processorEmail
        this.ownerEmail = ownerEmail

    }

    public getId() {
        return this.id
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

    public getProviderUrl() {
        return this.providerUrl
    }

    public setProviderUrl( String providerUrl ) {
        this.providerUrl = providerUrl
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
        return "${id}:${name}:${providerEmail}:${providerUrl}:${processorEmail}:${ownerEmail}"
    }

}
