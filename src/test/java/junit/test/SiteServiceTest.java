/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * junit.test.SiteServiceTest.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 10:29:30 AM
 */
package junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sh.sbl.cms.service.ICmsSiteService;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 10:29:30 AM
 * @description TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-common.xml"})
public class SiteServiceTest {
	@Autowired
	private ICmsSiteService cmsSiteService;
	
	@Test
	public void 测试查询所有品牌() {
		assertEquals(2, this.cmsSiteService.getAllSites().size());
	}
}

