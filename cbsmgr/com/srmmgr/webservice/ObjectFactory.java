
package com.srmmgr.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.srmmgr.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateProgProductRelResponse_QNAME = new QName("http://webservice.srmmgr.com/", "updateProgProductRelResponse");
    private final static QName _GetAllProgProductRel_QNAME = new QName("http://webservice.srmmgr.com/", "getAllProgProductRel");
    private final static QName _DeleteProgProductRel_QNAME = new QName("http://webservice.srmmgr.com/", "deleteProgProductRel");
    private final static QName _DeleteProgProductRelResponse_QNAME = new QName("http://webservice.srmmgr.com/", "deleteProgProductRelResponse");
    private final static QName _UpdateProgProductRel_QNAME = new QName("http://webservice.srmmgr.com/", "updateProgProductRel");
    private final static QName _SaveProgProductRelResponse_QNAME = new QName("http://webservice.srmmgr.com/", "saveProgProductRelResponse");
    private final static QName _GetAllProgProductRelResponse_QNAME = new QName("http://webservice.srmmgr.com/", "getAllProgProductRelResponse");
    private final static QName _SaveProgProductRel_QNAME = new QName("http://webservice.srmmgr.com/", "saveProgProductRel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.srmmgr.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteProgProductRelResponse }
     * 
     */
    public DeleteProgProductRelResponse createDeleteProgProductRelResponse() {
        return new DeleteProgProductRelResponse();
    }

    /**
     * Create an instance of {@link SaveProgProductRelResponse }
     * 
     */
    public SaveProgProductRelResponse createSaveProgProductRelResponse() {
        return new SaveProgProductRelResponse();
    }

    /**
     * Create an instance of {@link UpdateProgProductRelResponse }
     * 
     */
    public UpdateProgProductRelResponse createUpdateProgProductRelResponse() {
        return new UpdateProgProductRelResponse();
    }

    /**
     * Create an instance of {@link GetAllProgProductRelResponse }
     * 
     */
    public GetAllProgProductRelResponse createGetAllProgProductRelResponse() {
        return new GetAllProgProductRelResponse();
    }

    /**
     * Create an instance of {@link GetAllProgProductRel }
     * 
     */
    public GetAllProgProductRel createGetAllProgProductRel() {
        return new GetAllProgProductRel();
    }

    /**
     * Create an instance of {@link ProgProductRel }
     * 
     */
    public ProgProductRel createProgProductRel() {
        return new ProgProductRel();
    }

    /**
     * Create an instance of {@link UpdateProgProductRel }
     * 
     */
    public UpdateProgProductRel createUpdateProgProductRel() {
        return new UpdateProgProductRel();
    }

    /**
     * Create an instance of {@link DeleteProgProductRel }
     * 
     */
    public DeleteProgProductRel createDeleteProgProductRel() {
        return new DeleteProgProductRel();
    }

    /**
     * Create an instance of {@link SaveProgProductRel }
     * 
     */
    public SaveProgProductRel createSaveProgProductRel() {
        return new SaveProgProductRel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProgProductRelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "updateProgProductRelResponse")
    public JAXBElement<UpdateProgProductRelResponse> createUpdateProgProductRelResponse(UpdateProgProductRelResponse value) {
        return new JAXBElement<UpdateProgProductRelResponse>(_UpdateProgProductRelResponse_QNAME, UpdateProgProductRelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProgProductRel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "getAllProgProductRel")
    public JAXBElement<GetAllProgProductRel> createGetAllProgProductRel(GetAllProgProductRel value) {
        return new JAXBElement<GetAllProgProductRel>(_GetAllProgProductRel_QNAME, GetAllProgProductRel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProgProductRel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "deleteProgProductRel")
    public JAXBElement<DeleteProgProductRel> createDeleteProgProductRel(DeleteProgProductRel value) {
        return new JAXBElement<DeleteProgProductRel>(_DeleteProgProductRel_QNAME, DeleteProgProductRel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProgProductRelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "deleteProgProductRelResponse")
    public JAXBElement<DeleteProgProductRelResponse> createDeleteProgProductRelResponse(DeleteProgProductRelResponse value) {
        return new JAXBElement<DeleteProgProductRelResponse>(_DeleteProgProductRelResponse_QNAME, DeleteProgProductRelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProgProductRel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "updateProgProductRel")
    public JAXBElement<UpdateProgProductRel> createUpdateProgProductRel(UpdateProgProductRel value) {
        return new JAXBElement<UpdateProgProductRel>(_UpdateProgProductRel_QNAME, UpdateProgProductRel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveProgProductRelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "saveProgProductRelResponse")
    public JAXBElement<SaveProgProductRelResponse> createSaveProgProductRelResponse(SaveProgProductRelResponse value) {
        return new JAXBElement<SaveProgProductRelResponse>(_SaveProgProductRelResponse_QNAME, SaveProgProductRelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProgProductRelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "getAllProgProductRelResponse")
    public JAXBElement<GetAllProgProductRelResponse> createGetAllProgProductRelResponse(GetAllProgProductRelResponse value) {
        return new JAXBElement<GetAllProgProductRelResponse>(_GetAllProgProductRelResponse_QNAME, GetAllProgProductRelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveProgProductRel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.srmmgr.com/", name = "saveProgProductRel")
    public JAXBElement<SaveProgProductRel> createSaveProgProductRel(SaveProgProductRel value) {
        return new JAXBElement<SaveProgProductRel>(_SaveProgProductRel_QNAME, SaveProgProductRel.class, null, value);
    }

}
