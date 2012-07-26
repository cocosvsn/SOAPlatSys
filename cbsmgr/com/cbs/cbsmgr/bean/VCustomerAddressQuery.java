package com.cbs.cbsmgr.bean;
// default package



/**
 * VCustomerAddressQuery entity. @author MyEclipse Persistence Tools
 */

public class VCustomerAddressQuery  implements java.io.Serializable {


    // Fields    

     private VCustomerAddressQueryId id;


    // Constructors

    /** default constructor */
    public VCustomerAddressQuery() {
    }

    
    /** full constructor */
    public VCustomerAddressQuery(VCustomerAddressQueryId id) {
        this.id = id;
    }

   
    // Property accessors

    public VCustomerAddressQueryId getId() {
        return this.id;
    }
    
    public void setId(VCustomerAddressQueryId id) {
        this.id = id;
    }
   








}