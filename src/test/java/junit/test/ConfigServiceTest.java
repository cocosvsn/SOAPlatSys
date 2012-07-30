/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * junit.test.ConfigServiceTest.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 5:13:59 PM
 */
package junit.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sh.sbl.cms.beans.Config;
import cn.sh.sbl.cms.service.IConfigService;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 5:13:59 PM
 * @description 配置Service测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-common.xml"})
public class ConfigServiceTest {
	@Autowired
	private IConfigService configService;
	@Autowired
	private Logger logger;
	
	@Test
	public void 测试查询所有配置信息() {
		List<Config> list = this.configService.getAllConfigs();
		for (Config config : list) {
//			logger.debug("name={{}}, value={{}}, comment={{}}, valid={{}}", 
//					new Object[] {config.getName(), config.getValue(), config.getComment(), config.isValid()});
			System.out.println(config.getName() + " : " + config.isValid());
		}
		assertTrue(0 < list.size());
	}
}
