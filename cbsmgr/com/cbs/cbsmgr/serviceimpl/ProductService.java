package com.cbs.cbsmgr.serviceimpl;

import java.util.List;
import org.apache.log4j.Logger;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.serviceiface.IProductService;

public class ProductService implements IProductService {

	public IProductManager productManager = null;
	Logger logger = null;
	
	public ProductService(/*IProductManager productManager*/)
	{
		productManager = (IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		productManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		productManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = productManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = productManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = productManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = productManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = productManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		productManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		productManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		productManager.update(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	public List findbyExample(Object exampleentity) {
		List list = productManager.findbyExample(exampleentity);
		return list;
	}
	/*
	public Product createProduct(Long accountId, ProductDTO productDTO) throws CBSException {
		
	    // ������ƷproductDTO ���ʻ�accountId
		
		// �ж��������Ϸ���
	    if (!Validator.isIdValid(accountId))
	      throw new CBSException(ErrorNumber.PRODUCT_ACCOUNT_ID_IS_INVALID);
	    if (productDTO == null)
	      throw new CBSException(ErrorNumber.PRODUCTDTO_IS_NULL);
	    
	    // �õ��ʻ�accountId����Ϣ --> acct
	    Account acct =  null;
	    AccountService accountService = new AccountService();
	    acct = (Account)accountService.getById(accountId.toString());
	    
	    // ������Ʒ����ݿ�
	    Product product = new Product(productDTO);
	    product.setCustomerId(acct.getCustomerId());
	    product = (Product)productManager.save(product);
	    
	    // l��Ӳ������Ʒ
	    if (productDTO.getHardwareId() != null && productDTO.getHardwareId().longValue() != 0)
	    {
	        Hardware hardware = null ;
	        HardwareService hardwareService = new HardwareService();
	        hardware = (Hardware)hardwareService.getById(productDTO.getHardwareId().toString());
//	        try {
//	          hardware = (Hardware) BusinessObjectFactory.getBusinessObjectById(
//	              "Hardware", productDTO.getHardwareId());
//	        }
//	        catch (CBSException ex) {
//	          throw new CBSException(ErrorNumber.HARDWARE_FINDBYPK_ERROR);
//	        }
	        
	        linkHardwareToProduct(product, hardware);
	    }
	    
		return product;
	}
	
	public void linkHardwareToProduct(Product product, Hardware hardware) throws CBSException 
	{
		  // l�� Ӳ��hardware �� ��Ʒproduct
		  if (product == null)
		    throw new CBSException(ErrorNumber.PRODUCT_IS_NULL);
		  if (hardware == null)
		    throw new CBSException(ErrorNumber.HARDWARE_IS_NULL);
		
//		  logger.debug("Linking Hardware[" + hardware.getHardwareId() +
//		       "] to Product[" + product.getId() + "]");
		  // Check if product is hardware product
		  if (!this.isHardwareProduct(product)) {
		    throw new CBSException(ErrorNumber.PRODUCT_IS_NOT_HARDWARE);
		  }
		
		  // Check if the specified hardware is already assigned to other customer
		  if (hardware.getCustomerId().longValue() != 0) {
		    throw new CBSException(ErrorNumber.HARDWARE_ALREADY_USED);
		  }
		
		  String modelCollection = product.getHardwareModelCollection();
		  //Check hardware module match
		  if (ExpressionAnalyser.COMPARE_MISMATCH ==
		      ExpressionAnalyser.contains(modelCollection,
		                                  hardware.getHardwareModelId())) {
		    throw new CBSException(ErrorNumber.HARDWARE_MODEL_NOT_MATCH);
		  }
		
		  String statusCollection = product.getHardwareStatusCollection();
		  //Check hardware status match
		  if (ExpressionAnalyser.COMPARE_MISMATCH ==
		      ExpressionAnalyser.contains(statusCollection,
		                                  hardware.getHardwareStatusId())) {
		
		    throw new CBSException(ErrorNumber.HARDWARE_STATUS_NOT_MATCH);
		
		  }
		  //link Hardware
		  product.setHardwareId(hardware.getHardwareId());
		  hardware.setCustomerId(product.getCustomerId());
//		  logger.debug("Successfully linked Hardware[" + hardware.getHardwareId() +
//		       "] to Product[" + product.getId() + "]");
	}
	
	public boolean isHardwareProduct(Product product) throws CBSException {
		 // �жϲ�Ʒproduct�Ƿ� ��Ӳ����Ʒ
		 if (product == null)
		     throw new CBSException(ErrorNumber.PRODUCT_IS_NULL);

		 ProductCategory productCategory = null;
		 ProductCategoryService productCategoryService = new ProductCategoryService();
		 productCategory = (ProductCategory)productCategoryService.getById(product.getProductCategoryId().toString());
//		    try {
//		      productCategory = (ProductCategory) BusinessObjectFactory.
//		          getBusinessObjectById(
//		          "ProductCategory", product.getProductCategoryId());
//		    }
//		    catch (CBSException ex) {
//		      throw new CBSException(ErrorNumber.PRODUCT_CATEGORY_FINDBYPK_ERROR);
//		    }

		 if (productCategory.getHardwareTag().compareToIgnoreCase("Y") != 0) 
		 {
		     return false;
		 }

		 return true;
	}
	
	public boolean checkProductCombination(Long customerId) throws CBSException 
	{
	    // ����Ʒ��Ϲ���
	    if (!Validator.isIdValid(customerId))
	      throw new CBSException(ErrorNumber.PRODUCT_CUSTOMER_ID_IS_INVALID);
//		    Customer customer = (Customer)BusinessObjectFactory.getBusinessObjectById("Customer",customerId);
	    CustomerProfile customerProfile = new CustomerProfile(customerId);
	    return checkProductCombination(customerProfile);
	}

	public boolean checkProductCombination(CustomerProfile customerProfile) throws CBSException 
	{
    // ����Ʒ��Ϲ���
    if (customerProfile == null)
      throw new CBSException(ErrorNumber.PRODUCT_CUSTOMER_PROFILE_IS_NULL);

    Collection products = customerProfile.getProducts();
    Iterator productItr = products.iterator();

    //Get active product combination rules
//    ProductCombRuleHome productCombRuleHome = null ;
//    try {
//      productCombRuleHome = (ProductCombRuleHome) BusinessObjectFactory.
//          getBusinessObjectHome(
//          "ProductCombRule");
//    }
//    catch (CBSException ex) {
//      throw new EJBException(ex);
//    }
//    Collection rules = null;
//    try {
//      rules = productCombRuleHome.findByActive();
//    }
//    catch (FinderException ex) {
////      logger.debug("Product Combination Rules was not found");
//      throw new CBSException(ErrorNumber.PRODUCT_COMBINATION_RULE_NOT_FOUND);
//    }
    
    // ��ѯ��ݿ�õ�Active��product combination rules
    ProductCombRuleService productCombRuleService = new ProductCombRuleService();
    // rules - ���е���Ч�Ĳ�Ʒ��Ϲ���
    Collection rules = (Collection)productCombRuleService.findByActive();
    
    // �������е�product combination rules
    while (productItr.hasNext()) {
      ProductProfile productProfile = (ProductProfile) productItr.next();
      Iterator combRuleItr = rules.iterator();
      while (combRuleItr.hasNext()) 
      {
        ProductCombRule rule = (ProductCombRule) combRuleItr.next();
//        BrcProduct brcProduct = (BrcProduct) BusinessObjectFactory.
//            getBusinessObjectById("BrcProduct", rule.getBrcProductId());
        BrcProductService brcProductService = new BrcProductService();
        BrcProduct brcProduct = (BrcProduct)brcProductService.getById(rule.getBrcProductId().toString());

        if (this.matchBRCProduct(productProfile, brcProduct)) 
        {
          //product combination compare
          // rule - ��������ǰ�Ĳ�Ʒ��Ϲ���
          // ************ getProductCombinationPatterns �����⣬��δ��� ***********
          Vector patterns = rule.getProductCombinationPatterns();
          
          // products - �ͻ��µĲ�Ʒ
          // patterns - ��ǰ��Ʒ��Ϲ���� ... 
          // productProfile - ��������ǰ�Ŀͻ��Ĳ�Ʒ
          if (!this.checkProductCombinationPatterns(products, patterns, productProfile)) 
          {
            return false;
          }
        }
      }
      //What should return if the product does not match the BRCProduct of all rules?
    }
    return true;
  }
	
	public boolean checkProductCombinationPatterns(Collection products,
              Collection patterns,
              ProductProfile currentProductProfile
              ) throws CBSException 
    {
		if (products == null || patterns == null) 
		{
			return false;
		}
		//Indicate if any must contain pattern is matched.
		boolean containsMatched = false;
		boolean containsAllBypass = true;
		//Indicate if any must not contain pattern is matched.
		boolean notContainMatched = false;
		
		Iterator patternItr = patterns.iterator();
		while (patternItr.hasNext()) 
		{
			ProductCombinationPattern pattern = (ProductCombinationPattern)
			patternItr.next();
			
			if (pattern.getPatternString() != null) 
			{
				if (!pattern.getPatternString().equals("*")) 
				{
					if (pattern.getPatternMode().equals(ProductCombinationPattern.
					           CONTAIN_PATTERN)) 
					{
						// 
						containsAllBypass = false;
					}
					
					// pattern.getPatternString() - 
					// pattern.getCheckLinkedOnlyTag() - 
					// products - �ͻ��µĲ�Ʒ
					// currentProductProfile - ��������ǰ�Ŀͻ��Ĳ�Ʒ
					if (matchProductCombinationPattern(
							pattern.getPatternString(),
					        pattern.getCheckLinkedOnlyTag(),
					        products,
					        currentProductProfile)
					        ) 
					{
						if (pattern.getPatternMode().equals(ProductCombinationPattern.
						             CONTAIN_PATTERN)) 
						{
							logger.debug("Product Combination Contains Pattern matched");
							containsMatched = true;
						}
						else if (pattern.getPatternMode().equals(ProductCombinationPattern.
								NOT_CONTAIN_PATTERN)) 
						{
							logger.debug(
								"One of Product Combination NOT Contains Pattern mismatched");
							notContainMatched = true;
						}
					}
				}
			}
		}
		return (containsAllBypass || containsMatched) && (!notContainMatched);
	}
	
	private boolean matchProductCombinationPattern(String strCombinationPattern,
              String strCheckLinkedOnlyTag,
              Collection products,
              ProductProfile currentProductProfile) 
				throws CBSException 
	{
		if (Validator.isEmptyString(strCheckLinkedOnlyTag)) 
		{
			return false;
		}
		if (Validator.isEmptyString(strCombinationPattern)) 
		{
			return false;
		}
		if (products == null) 
		{
			return false;
		}
	
		Vector productsNeedCheck = new Vector(); //Contains the products which will be checked
		
		if (strCheckLinkedOnlyTag.compareToIgnoreCase("Y") == 0 &&
		currentProductProfile != null) 
		{
			// productsNeedCheck will only contains the products in the same "cluster" of currentProduct
			Long rootProductId;
			
			if (currentProductProfile.getLinkProductId().longValue() == 0) 
			{
				//currentProduct does not linked to others,it is its own root
				rootProductId = currentProductProfile.getProductId();
				
				//We did not check whether rootProduct is hardware or can be found
				//if the checkOnlyLinked is set and current product has no link.
				//Should we add here?
			}
			
			else 
			{
				//currentProduct has linked to a hardware product
				rootProductId = currentProductProfile.getLinkProductId();
				
				//Verify root product is a hardware product by looking through all products
				boolean checkRootProductIsHardware = false;
				Iterator productItr = products.iterator();
				while (productItr.hasNext()) 
				{
					ProductProfile productProfile = (ProductProfile) productItr.next();
					
					if (productProfile.getProductId().longValue() ==
					rootProductId.longValue() &&
					productProfile.isHardware()) 
					{
						checkRootProductIsHardware = true;
						break;
					}
				}
				//if root product does not found or it is not a hardware product
				//so there is some problem, the input parameter is wrong
				if (!checkRootProductIsHardware) 
				{
					rootProductId = new Long(0);
					return false;
				}
			}
			
			//Constructs the productsNeedCheck with all products which are linked to rootProduct
			if (rootProductId.longValue() != 0) 
			{
				Iterator productItr = products.iterator();
				while (productItr.hasNext()) 
				{
					ProductProfile productProfile = (ProductProfile) productItr.next();
					
					if (productProfile.getLinkProductId().longValue() ==
					rootProductId.longValue() ||
					productProfile.getProductId().longValue() ==
					rootProductId.longValue()) 
					{
						productsNeedCheck.addElement(productProfile);
					}
				}
			}
		}
		else 
		{
			//checkLinkedOnlyTag is NO, or current product is null,
			//so we have to construct productsNeedCheck with all products.
			productsNeedCheck.addAll(products);
		}
	
		//Check each of brcProduct from pattern and calculate how many product in productsNeedCheck matches
		//If one of BRCProduct does not find enough match from productsNeedCheck, then failed.
		Iterator brcProductItr = ExpressionAnalyser.patternStringToVector(
		strCombinationPattern).iterator();
		while (brcProductItr.hasNext()) 
		{
			Long brcProductId = (Long) brcProductItr.next();
//			BrcProduct brcProduct = (BrcProduct) BusinessObjectFactory.getBusinessObjectById("BrcProduct",
//																					brcProductId);
			BrcProductService brcProductService = new BrcProductService();
			BrcProduct brcProduct = (BrcProduct)brcProductService.getById(brcProductId.toString());
			
			int matchCount = 0;
			Iterator productItr = productsNeedCheck.iterator();
			while (productItr.hasNext()) 
			{
				ProductProfile productProfile = (ProductProfile) productItr.next();
				if (matchBRCProduct(productProfile, brcProduct)) 
				{
					matchCount++;
				}
			}
			
			if (matchCount < brcProduct.getTotalNumber().longValue()) 
			{
				return false;
			}
		}
		return true;
	}
	  
	public boolean matchBRCProduct(ProductProfile productProfile,
            BrcProduct brcProduct) throws CBSException 
    {
		if (productProfile == null || brcProduct == null) 
		{
			return false;
		}
		
		logger.debug("Comparing Product[" + productProfile.getProductId() +
		"] with BCRProduct[" + brcProduct.getBrcProductId() + "]");
		//product category compare
		if (productProfile.getProductCategoryId().longValue() !=
		brcProduct.getProductCategoryId().longValue()) 
		{
			logger.debug("Product Category mismatch");
			return false;
		}
		
		//product status compare
		if (ExpressionAnalyser.COMPARE_MISMATCH ==
		ExpressionAnalyser.contains(brcProduct.getStatusCollection(),
		               productProfile.getProductStatusId())) 
		{
			logger.debug("Product Status mismatch");
			return false;
		}
		
		//product finanace option compare
		if (ExpressionAnalyser.COMPARE_MISMATCH ==
		ExpressionAnalyser.contains(brcProduct.getFinanceOptionCollection(),
		               productProfile.getFinanceOptionId())) 
		{
			logger.debug("Finance Option mismatch");
			return false;
		}
		
		//product capture date from compare
		if (brcProduct.getCaptureDateFrom() != null) 
		{
			if (productProfile.getCaptureDate().getTime() <
			brcProduct.getCaptureDateFrom().getTime()) 
			{
				logger.debug("Capture Date(From) mismatch");
				return false;
			}
		}
		
		//product capture date to compare
		if (brcProduct.getCaptureDateTo() != null) 
		{
			if (productProfile.getCaptureDate().getTime() >
			brcProduct.getCaptureDateTo().getTime()) 
			{
				logger.debug("Capture Date(To) mismatch");
				return false;
			}
		}
		
		//product old status compare
		if (ExpressionAnalyser.COMPARE_MISMATCH ==
		ExpressionAnalyser.contains(brcProduct.getOldStatusCollection(),
		               productProfile.getProductOldStatusId())) 
		{
			logger.debug("Product Previous Status mismatch");
			return false;
		}
		
		//product status change date from
		if (brcProduct.getStatusChangeDateFrom() != null &&
		productProfile.getStatusChangeDate() != null) 
		{
			if (productProfile.getStatusChangeDate().getTime() <
			brcProduct.getStatusChangeDateFrom().getTime()) 
			{
				logger.debug("Product Status Changed Date(From) mismatch");
				return false;
			}
		}
		
		//product status change date to compare
		if (brcProduct.getStatusChangeDateTo() != null &&
		productProfile.getStatusChangeDate() != null) 
		{
			if (productProfile.getStatusChangeDate().getTime() >
			brcProduct.getStatusChangeDateTo().getTime()) {
				logger.debug("Product Status Changed Date(To) mismatch");
				return false;
			}
		}
		
		//product market segment compare
		if (ExpressionAnalyser.COMPARE_MISMATCH ==
		ExpressionAnalyser.contains(brcProduct.getMarketSegmentCollection(),
		               productProfile.getMarketSegmentId())) 
		{
			logger.debug("Market Segment mismatch");
			return false;
		}
		
		//product dealer compare
		if (ExpressionAnalyser.COMPARE_MISMATCH ==
		ExpressionAnalyser.contains(brcProduct.getDealerTypeCollection(),
		               productProfile.getDealerTypeId())) 
		{
			logger.debug("Dealer mismatch");
			return false;
		}
		
		//under ProviderGuarantee check
		if (brcProduct.getProviderGuaranteeTag() != null) 
		{
			if (brcProduct.getProviderGuaranteeTag().compareToIgnoreCase("Y") == 0 &&
			productProfile.getProviderGuaranteeDate() != null) 
			{
				if (TimeConvertor.getCurrentDate().getTime() >
				productProfile.getProviderGuaranteeDate().getTime()) 
				{
					logger.debug("Provider Guarantee mismatch");
					return false;
				}
			}
			if (brcProduct.getProviderGuaranteeTag().compareToIgnoreCase("N") == 0 &&
			productProfile.getProviderGuaranteeDate() != null) 
			{
				if (TimeConvertor.getCurrentDate().getTime()  <=
				productProfile.getProviderGuaranteeDate().getTime()) 
				{
					logger.debug("Provider Guarantee mismatch");
					return false;
				}
			}
		}
		
		//hardware tag check:
		if (productProfile.isHardware()) 
		{
			//hardwareModel check
			if (ExpressionAnalyser.COMPARE_MISMATCH ==
			ExpressionAnalyser.contains(brcProduct.getHardwareModelCollection(),
			                 productProfile.getHardwareModelId())) 
			{
				logger.debug("Hardware Model mismatch");
				return false;
			}
		
			//hardware status check
			if (ExpressionAnalyser.COMPARE_MISMATCH ==
			ExpressionAnalyser.contains(brcProduct.getHardwareStatusCollection(),
			                 productProfile.getHardwareStatusId())) 
			{
			logger.debug("Hardware Status mismatch");
			return false;
			}
		
			//hardware depots type check
			if (ExpressionAnalyser.COMPARE_MISMATCH ==
			ExpressionAnalyser.contains(brcProduct.getHardwareDepotTypeCollection(),
			                 productProfile.getHardwareDepotTypeId())) 
			{
			logger.debug("Hardware Deopt Type mismatch");
			return false;
			}
		
			//under ManufactoryGuarantee check
			if (brcProduct.getManufactoryGuaranteeTag() != null) 
			{
				if (productProfile.getHardwareModelId().longValue() == 0)
				{
					logger.debug("Manufactory Guarantee mismatch");
					return false;
				}
				if (brcProduct.getManufactoryGuaranteeTag().compareToIgnoreCase("Y") ==
				0 &&
				productProfile.getManufactoryGuaranteeDate() != null) 
				{
					if (TimeConvertor.getCurrentDate().getTime() >
					productProfile.getManufactoryGuaranteeDate().getTime()) 
					{
						logger.debug("Manufactory Guarantee mismatch");
						return false;
					}
				}
				if (brcProduct.getManufactoryGuaranteeTag().compareToIgnoreCase("N") ==
				0 &&
				productProfile.getManufactoryGuaranteeDate() != null) 
				{
					if (TimeConvertor.getCurrentDate().getTime() <=
					productProfile.getManufactoryGuaranteeDate().getTime()) 
					{
						logger.debug("Manufactory Guarantee mismatch");
						return false;
					}
				}
			}
		}
		logger.debug("Matched Product[" + productProfile.getProductId() +
		"] with BCRProduct[" + brcProduct.getBrcProductId() + "]");
		return true;
	}*/
}
