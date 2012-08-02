package com.soaplat.cmsmgr.EncryptService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import com.soaplat.cmsmgr.EncryptService.localhost.TaskManagerSoap;
import com.soaplat.cmsmgr.bean.EncryptList;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.manageriface.IEncryptListManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.service.EncryptServiceImpl;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class EncryptServiceServiceImpl implements EncryptServiceServiceIface {
	private int tryCount = 0;
	private int retryTime = 1000;
	private EncryptServiceImpl encryptService = new EncryptServiceImpl();
	public IEncryptListManager encryptListManager = null;
	public IProductInfoManager productInfoManager = null;
	public String webserviceaddr = "";//;"http://10.220.1.100:8000/TaskManager.asmx";
	public static final Logger cmsLog = Logger.getLogger("Cms");
	public static CmsConfig cc = new CmsConfig();

	public EncryptServiceServiceImpl() 
	{
		encryptListManager = (IEncryptListManager) ApplicationContextHolder.webApplicationContext.getBean("encryptListManager");
		productInfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
		webserviceaddr = cc.getPropertyByName("CaWebServiceAddr");
		this.tryCount = Integer.valueOf(cc.getPropertyByName("RetryQueryEncryptStatusCount"));
		this.retryTime = Integer.valueOf(cc.getPropertyByName("RetryQueryEncryptStatusTime"));
	}

	/**
	 * 查询指定任务的状态,24小时运行服务, 在servlet中实例化并调用此方法，在web.xml中配置在服务启动是调用servlet
	 */
	@SuppressWarnings("unchecked")
	public void GetTaskStatus() 
	{
//		cmsLog.debug("Cms -> EncryptServiceServiceImpl -> GetTaskStatus ...");
		try 
		{
			// while (true) {
			List list = encryptListManager.findByProperty("dealstate", Long.valueOf(1));
			if(list.size() > 0)
			{
				cmsLog.info("查询到需要处理的加扰任务，共" + list.size() + "条记录。");
				for (int i = 0; i < list.size(); i++) 
				{
					EncryptList encryptList = (EncryptList) list.get(i);
					
					cmsLog.info("处理第" + (i+1) + "个加扰任务...");
					cmsLog.info("查询到未完成加扰单，加扰单ID：" + encryptList.getEncryptid());
					cmsLog.info("加扰任务单节目包ID：" + encryptList.getProductid());
					cmsLog.info("加扰任务单状态：" + encryptList.getDealstate().toString());
	
					try
					{
						int taskid = Integer.valueOf((encryptList.getEncryptid()));// 任务ID
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
						String addTimeS = df.format(encryptList.getDate4()); // 返回任务添加的时间
						Holder<String> addTime = new Holder<String>(addTimeS);
						int pri = Integer.valueOf(encryptList.getTaskprio());
						byte prio = (byte) pri; // 返回任务优先级别
						Holder<Short> prioc = new Holder<Short>(Short.valueOf(prio));
						int stateS = encryptList.getState2().intValue(); // 返回任务状态
						Holder<Integer> state = new Holder<Integer>(stateS);
						String scpi = encryptList.getScip(); // 返回加扰器IP
						Holder<String> sc = new Holder<String>(scpi);
						String err = encryptList.getDealinfo(); // 当任务没有处理成功时，返回的错误信息
						Holder<String> errMsg = new Holder<String>(err);
		
						// 调用外围接口
						// int GetTaskStatus ( uint taskId, out string addTime, out byte
						// prio,
						// int state ,out string sc,out string errMsg )
						int taskS = -1; // 返回值
						Holder<Integer> task = new Holder<Integer>(taskS);
						JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
						//			factory.setAddress("http://127.0.0.1:8000/TaskManager.asmx");
						factory.setAddress(webserviceaddr);
						factory.setServiceClass(TaskManagerSoap.class);
						TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
						
						cmsLog.info("准备调用永新视博的Webserivce地址：" + webserviceaddr);
						taskManager.getTaskStatus(
								taskid, 
								task, 
								addTime, 
								prioc, 
								state,
								sc, 
								errMsg
								);
		
						cmsLog.info("----------- CA加扰服务器反馈 -------------");
						cmsLog.info("接口调用结果 ：" + task.value);
						cmsLog.info("任务ID ：" + taskid);
						cmsLog.info("任务状态 ：" + state.value);
						cmsLog.info("任务添加时间 ：" + addTime.value);
						cmsLog.info("加扰器IP ：" + sc.value);
						cmsLog.info("任务加扰级别 ：" + prioc.value);
						cmsLog.info("返回信息 ：" + errMsg.value);
						cmsLog.info("=======================================");
						
		
						// 如果返回值为0 ，判断返回任务的状态，状态为3，更改状态dealstate为8。状态为4，更改状态dealstate为9.
						if (Integer.valueOf(task.value) == 0)
						{
							// 处理状态（0未处理 1下发任务 5加扰成功 6反馈失败 8成功 9失败）
							// 状态2（=0：未处理；=1：已分配；=2：处理中；=3：成功；=4：失败 ）
							EncryptServiceImpl encryptService = new EncryptServiceImpl();
							if (state.value == 3) 
							{
								cmsLog.info("加扰任务状态是“成功3”，准备修改数据库状态...任务ID：" + taskid);
								encryptList.setDealstate(Long.valueOf(8));
								encryptList.setDate5(new Date());
								
		//						 北京修改，20100204 14:26
		//						EncryptServiceImpl encryptService = new EncryptServiceImpl();
								encryptService.finishEncrypt(
										encryptList.getProductid(), 
										encryptList.getRemark(), 
										encryptList.getDealstate(), 
										"加扰成功", 
										""
										);
		//						 修改完毕
							} 
							else if (state.value == 4) 
							{
								cmsLog.info("加扰任务状态是“失败4”，准备修改数据库状态...任务ID：" + taskid);
								encryptList.setDealstate(Long.valueOf(9));
								encryptList.setDealinfo(errMsg.value);
								encryptService.finishEncrypt(
										encryptList.getProductid(), 
										encryptList.getRemark(), 
										encryptList.getDealstate(), 
										"加扰失败", 
										""
										);
							}
							else if(state.value == 0)
							{
								cmsLog.info("加扰任务状态是“未处理0”。任务ID：" + taskid);
							}
							else if(state.value == 1)
							{
								cmsLog.info("加扰任务状态是“已分配1”。任务ID：" + taskid);
							}
							else if(state.value == 2)
							{
								cmsLog.info("加扰任务状态是“处理中2”。任务ID：" + taskid);
							}
							else
							{
								cmsLog.warn("未知加扰任务状态。任务ID：" + taskid);
							}
							
							// 更新表
							try 
							{
								encryptList.setDate4(df.parse(addTime.value));
							} 
							catch (ParseException e) 
							{
								cmsLog.error("处理加扰任务异常。信息：" + e.getMessage());
								e.printStackTrace();							
							}
							encryptList.setTaskprio(String.valueOf(prio));
							encryptList.setState2(Long.valueOf(state.value));
							encryptList.setScip(sc.value);
							encryptListManager.update(encryptList);
							cmsLog.info("加扰任务已更新。");
						} 
						else 
						{
							// 返回值为其他，记录错误信息。
							cmsLog.warn("查询加扰任务失败。任务ID：" + taskid);
							cmsLog.warn("返回信息：" + errMsg.value);
							
							encryptList.setDealstate(Long.valueOf(9));
							encryptList.setDealinfo("查询加扰任务失败。");
							encryptListManager.update(encryptList);
							cmsLog.info("设置加扰任务状态：" + encryptList.getDealstate());
						}
					}
					catch(Exception e)
					{
						cmsLog.error("处理加扰任务异常，信息：" + e.getMessage());
					}
				}
			}
		}
		catch (Exception e) 
		{
			cmsLog.error("Cms->EncryptServiceServiceImpl->GetTaskStatus，异常：" + e.getMessage());
		}
//		cmsLog.debug("Cms -> EncryptServiceServiceImpl -> GetTaskStatus returns.");
	}

	// 添加加扰任务,24小时运行服务
	public void addTask() 
	{
//		cmsLog.debug("Cms -> EncryptServiceServiceImpl -> addTask ...");
		try 
		{
			// while (true) {
			// 获取加扰任务，从数据库
			List list = encryptListManager.findByProperty("dealstate", (long)0);
			if(list.size() > 0)
			{
				cmsLog.info("查询到需要处理的加扰任务，共" + list.size() + "条记录。");
				for (int i = 0; i < list.size(); i++) 
				{
					EncryptList encryptList = (EncryptList) list.get(i);
					
					cmsLog.info("处理第" + (i+1) + "个加扰任务...");
					cmsLog.info("查询得到数据库重未处理（或失败）的加扰单，加扰单ID：" + encryptList.getEncryptid());
					cmsLog.info("加扰任务单节目包ID：" + encryptList.getProductid());
					cmsLog.info("加扰任务单状态：" + encryptList.getDealstate().toString());

					EncryptServiceImpl encryptService = new EncryptServiceImpl();
					
					try
					{
						String err = encryptList.getDealinfo(); // 返回错误信息
						Holder<String> errMsg = new Holder<String>(err); // 返回错误信息
						int taskid = Integer.valueOf((encryptList.getEncryptid())); // 任务ID
						int taskPrio = Integer.valueOf(encryptList.getTaskprio()); // 加扰级别
						byte b = (byte) taskPrio; // 加扰级别
						String taskContent = encryptList.getContentxml(); // 任务内容
						byte[] by = taskContent.getBytes(); // 任务内容
						// 调用外围接口
						// int AddTask ( uint taskId, byte taskPrio, byte[] taskContent, out
						// string errMsg )
		
						// 调用webservice接口，添加加扰任务
						int taskS = -1; // 返回值
						Holder<Integer> task = new Holder<Integer>(taskS); // 返回值
						JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
						// factory.setAddress("http://127.0.0.1:8000/TaskManager.asmx");
						factory.setAddress(webserviceaddr);
						factory.setServiceClass(TaskManagerSoap.class);
						TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
						
						cmsLog.info("准备调用永新视博的Webserivce地址：" + webserviceaddr);
						taskManager.addTask(taskid, // 任务ID
								b, // 加扰级别
								by, // 任务内容
								task, // 返回结果
								errMsg // 返回错误信息
								);
		
						// 如果返回0 ，更改状态dealstate为1。否则把errMsg写入数据库。
						if (Integer.valueOf(task.value) == 0) 
						{
							// 添加加扰任务成功
							// 处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
							encryptList.setDealstate((long)1); 
							cmsLog.info("北京永新视博反馈添加加扰任务成功，数据库加扰任务单ID：" + taskid);
							encryptListManager.update(encryptList);
							cmsLog.info("设置加扰任务状态：" + encryptList.getDealstate());
						}
						else 
						{
							// 添加加扰任务失败
							// 处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
							encryptList.setDealstate((long)0);		
							encryptList.setDealinfo(String.valueOf(errMsg.value));
							cmsLog.warn("北京永新视博反馈添加加扰任务失败，数据库加扰任务单ID：" + taskid);
							cmsLog.warn("北京永新视博反馈的错误信息：" + errMsg.value);
							encryptListManager.update(encryptList);
							cmsLog.info("设置加扰任务状态：" + encryptList.getDealstate());
							
							cmsLog.info("准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
							encryptList.setDealstate(Long.valueOf(9));
							encryptList.setDealinfo(errMsg.value);
							encryptService.finishEncrypt(
									encryptList.getProductid(), 
									encryptList.getRemark(), 
									encryptList.getDealstate(), 
									"添加加扰失败", 
									""
									);
						}
					}
					catch (Exception e) 
					{
						cmsLog.error("Cms -> EncryptServiceServiceImpl -> addTask，异常...");
						cmsLog.error("处理加扰任务，异常：" + e.getMessage());
						
						// 添加加扰任务失败
						// 处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
						encryptList.setDealstate((long)9);		
						encryptList.setDealinfo(String.valueOf(e.getMessage()));
						cmsLog.warn("捕获异常信息：" + e.getMessage());
						encryptListManager.update(encryptList);
						cmsLog.info("设置加扰任务状态：" + encryptList.getDealstate());
						
						
						cmsLog.info("准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
						encryptList.setDealstate(Long.valueOf(9));
//						encryptList.setDealinfo("");
						encryptService.finishEncrypt(
								encryptList.getProductid(), 
								encryptList.getRemark(), 
								encryptList.getDealstate(), 
								"添加加扰异常", 
								""
								);
					}
				}
			}
		} 
		catch (Exception e) 
		{
			cmsLog.error("Cms -> EncryptServiceServiceImpl -> addTask，异常：" + e.getMessage());
		}
//		cmsLog.debug("Cms -> EncryptServiceServiceImpl -> addTask ...");
	}

	@SuppressWarnings("unchecked")
	public void addTaskHB_1_2() {
		List<EncryptList> list = encryptListManager.findByProperty("dealstate", 0L);
		if (0 < list.size()) {
			for (EncryptList encryptList : list) {
				Byte taskType = Byte.valueOf(encryptList.getRemark());		// 任务类别[0: 预加扰任务; 1: 节目打包任务]
				int taskId = Integer.valueOf((encryptList.getEncryptid())); // 任务ID
				Byte taskPrio = Byte.valueOf(encryptList.getTaskprio());
				String err = encryptList.getDealinfo();						// 返回错误信息
				Holder<String> errMsg = new Holder<String>(err);			// 返回错误信息
				byte[] by = encryptList.getContentxml().getBytes(); 		// 任务内容
				int taskS = -1; 											// 返回值
				Holder<Integer> taskResult = new Holder<Integer>(taskS);	// 返回值
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setAddress(webserviceaddr);
				factory.setServiceClass(TaskManagerSoap.class);
				TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
				
				cmsLog.debug("准备调用永新视博的Webserivce地址：" + webserviceaddr);
				taskManager.AddTaskEx(
						taskType,	// 任务类别[0: 预加扰任务; 1: 节目打包任务]
						taskId,		// 加扰级别
						taskPrio,	// 任务内容
						taskResult,		// 返回结果
						errMsg		// 返回错误信息
						);
				
				if (0 == taskResult.value) {
					// 添加加扰任务成功
					encryptList.setDealstate(1L); 
					cmsLog.info("北京永新视博反馈添加加扰任务成功，数据库加扰任务单ID：" + encryptList.getEncryptid());
					encryptListManager.update(encryptList);
				} else {
					// 添加加扰任务失败
					encryptList.setDealstate(9L);		
					encryptList.setDealinfo(errMsg.value);
					cmsLog.warn("北京永新视博反馈添加加扰任务失败，数据库加扰任务单ID：" + encryptList.getEncryptid());
					cmsLog.warn("北京永新视博反馈的错误信息：" + errMsg.value);
					encryptListManager.update(encryptList);
					cmsLog.info("准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
					this.encryptService.finishContentEncrypt(
							encryptList.getProductid(), 
							9,
							encryptList.getRemark(),
							encryptList.getGroupname(),
							encryptList.getInputmanid()
							);
				}
			}
		}
	}

	public void getTaskStatusHB_1_2() {
		List<EncryptList> list = encryptListManager.findByProperty("dealstate", 0L);
		if (0 < list.size()) {
			for (EncryptList encryptList : list) {
				Byte taskType = Byte.valueOf(encryptList.getRemark());		// 任务类别[0: 预加扰任务; 1: 节目打包任务]
				int taskId = Integer.valueOf((encryptList.getEncryptid())); // 任务ID
				Holder<String> addTime = new Holder<String>();				// 任务添加时间
				Holder<Byte> taskPrio = new Holder<Byte>();					// 任务优先级
				Holder<Integer> taskState = new Holder<Integer>();			// 任务状态
				Holder<String> sc = new Holder<String>();					// 加扰服务器IP
				Holder<String> errMsg = new Holder<String>();				// 返回错误信息
				Holder<Integer> taskResult = new Holder<Integer>();			// 返回值
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setAddress(webserviceaddr);
				factory.setServiceClass(TaskManagerSoap.class);
				TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
				taskManager.GetTaskStatusEx(
						taskType,
						taskId,
						addTime,
						taskPrio,
						taskState,
						sc,
						taskResult
				);
				
				if (0 == taskResult.value) {
					// 成功
					// 处理状态（0未处理 1下发任务 5加扰成功 6反馈失败 8成功 9失败）
					// 状态2（=0：未处理；=1：已分配；=2：处理中；=3：成功；=4：失败 ）
					if (3 == taskState.value) {
						cmsLog.debug("加扰任务状态是“成功3”，准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
						encryptList.setDealstate(Long.valueOf(8));
						encryptList.setDate5(new Date());

						this.encryptService.finishContentEncrypt(
								encryptList.getProductid(),
								8,
								encryptList.getRemark(),
								encryptList.getGroupname(),
								encryptList.getInputmanid()
						);
						// 修改完毕
					} else if (4 == taskState.value) {
						cmsLog.warn("加扰任务状态是“失败4”，准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
						encryptList.setDealstate(Long.valueOf(9));
						encryptList.setDealinfo(errMsg.value);
						this.encryptService.finishContentEncrypt(
								encryptList.getProductid(),
								9,
								encryptList.getRemark(),
								encryptList.getGroupname(),
								encryptList.getInputmanid()
						);
					} else if (0 == taskState.value) {
						cmsLog.debug("加扰任务状态是“未处理0”。任务ID：" + encryptList.getEncryptid());
					} else if (1 == taskState.value) {
						cmsLog.debug("加扰任务状态是“已分配1”。任务ID：" + encryptList.getEncryptid());
					} else if (2 == taskState.value) {
						cmsLog.debug("加扰任务状态是“处理中2”。任务ID：" + encryptList.getEncryptid());
					} else {
						cmsLog.warn("未知加扰任务状态。任务ID：" + encryptList.getEncryptid());
					}

					// 更新表
					try {
						encryptList.setDate4(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", addTime.value));
					} catch (ParseException e) {
						cmsLog.error("处理加扰任务异常。信息：" + e.getMessage());
						e.printStackTrace();
					}
					encryptList.setTaskprio(String.valueOf(taskPrio));
					encryptList.setState2(Long.valueOf(taskState.value));
					encryptList.setScip(sc.value);
					encryptListManager.update(encryptList);
					cmsLog.debug("加扰任务已更新。");
				} else {
					// 失败: 返回值为其他，记录错误信息。
					cmsLog.warn("查询加扰任务失败。任务ID：" + encryptList.getEncryptid());
					cmsLog.warn("返回信息：" + errMsg.value);
					encryptList.setDealstate(Long.valueOf(9));
					encryptListManager.update(encryptList);
				}
			}
		}
	}

	public void addTaskHB1_11() {
		cmsLog.debug("addTaskHB1_11()");
		List<EncryptList> list = encryptListManager.findByProperty("dealstate", 0L);
		if (0 < list.size()) {
			for (EncryptList encryptList : list) {
//				Byte taskType = Byte.valueOf(encryptList.getRemark());		// 任务类别[0: 预加扰任务; 1: 节目打包任务]
				int taskId = Integer.valueOf((encryptList.getEncryptid())); // 任务ID
				Byte taskPrio = Byte.valueOf(encryptList.getTaskprio());
				String err = encryptList.getDealinfo();						// 返回错误信息
				Holder<String> errMsg = new Holder<String>(err);			// 返回错误信息
				byte[] by = encryptList.getContentxml().getBytes(); 		// 任务内容
				int taskS = -1; 											// 返回值
				Holder<Integer> taskResult = new Holder<Integer>(taskS);	// 返回值
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setAddress(webserviceaddr);
				factory.setServiceClass(TaskManagerSoap.class);
				TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
				
				cmsLog.debug("准备调用永新视博的Webserivce地址：" + webserviceaddr);
//				taskManager.addTask(
//							taskType,		// 任务类别[0: 预加扰任务; 1: 节目打包任务]
//							taskId,			// 加扰级别
//							taskPrio,		// 任务内容
//							taskResult,		// 返回结果
//							errMsg			// 返回错误信息
//						);
				taskManager.addTask(
							taskId,			// 任务ID
							taskPrio,		// 加扰级别
							by,				// 任务内容
							taskResult,		// 返回结果
							errMsg			// 返回错误信息
						);
				if (0 == taskResult.value) {
					// 添加加扰任务成功
					encryptList.setDealstate(1L); 
					cmsLog.debug("北京永新视博反馈添加加扰任务成功，数据库加扰任务单ID：" + encryptList.getEncryptid());
					encryptListManager.update(encryptList);
				} else {
					// 添加加扰任务失败
					encryptList.setDealstate(9L);		
					encryptList.setDealinfo(errMsg.value);
					cmsLog.warn("北京永新视博反馈添加加扰任务失败，数据库加扰任务单ID：" + encryptList.getEncryptid());
					cmsLog.warn("北京永新视博反馈的错误信息：" + errMsg.value);
					encryptListManager.update(encryptList);
					cmsLog.debug("准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
					this.encryptService.finishContentEncrypt1_11(
							encryptList.getProductid(), 
							9,
							encryptList.getGroupname(),
							encryptList.getInputmanid()
							);
				}
			}
		}
	}
	
	public void getTaskStatusHB1_11() throws Exception {
		cmsLog.debug("getTaskStatusHB1_11()");
		List<EncryptList> list = encryptListManager.findByProperty("dealstate", 1L);
		if (0 < list.size()) {
			for (EncryptList encryptList : list) {
				// 任务类别[0: 预加扰任务; 1: 节目打包任务]
				long taskId = Long.valueOf((encryptList.getEncryptid())); // 任务ID
				Holder<String> addTime = new Holder<String>();				// 任务添加时间
				Holder<Short> taskPrio = new Holder<Short>();				// 任务优先级
				Holder<Integer> taskState = new Holder<Integer>();			// 任务状态
				Holder<String> sc = new Holder<String>();					// 加扰服务器IP
				Holder<String> errMsg = new Holder<String>();				// 返回错误信息
				Holder<Integer> taskResult = new Holder<Integer>();			// 返回值
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setAddress(webserviceaddr);
				factory.setServiceClass(TaskManagerSoap.class);
				TaskManagerSoap taskManager = (TaskManagerSoap) factory.create();
				
				for (int i = 0; i < this.tryCount; i++) {
					taskManager.getTaskStatus(
							taskId,
							taskResult,
							addTime,
							taskPrio,
							taskState,
							sc,
							errMsg
					);
					
					if (0 == taskResult.value) {
						// 成功
						// 处理状态（0未处理 1下发任务 5加扰成功 6反馈失败 8成功 9失败）
						// 状态2（=0：未处理；=1：已分配；=2：处理中；=3：成功；=4：失败 ）
						if (3 == taskState.value) {
							cmsLog.debug("加扰任务状态是“成功3”，准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
							encryptList.setDealstate(Long.valueOf(8));
							encryptList.setDate5(new Date());
							try {
								this.encryptService.finishContentEncrypt1_11(
										encryptList.getProductid(),
										8,
										encryptList.getGroupname(),
										encryptList.getInputmanid()
								);
							} catch (Exception e) {
								cmsLog.error("加扰成功时反馈异常: " + e);
							}
							// 修改完毕
						} else if (4 == taskState.value) {
							cmsLog.warn("加扰任务状态是“失败4”，准备修改数据库状态...任务ID：" + encryptList.getEncryptid());
							encryptList.setDealstate(Long.valueOf(9));
							encryptList.setDealinfo(errMsg.value);
							try {
								this.encryptService.finishContentEncrypt1_11(
										encryptList.getProductid(),
										9,
										encryptList.getGroupname(),
										encryptList.getInputmanid()
								);
							} catch (Exception e) {
								cmsLog.error("加扰失败时反馈异常: " + e);
							}
						} else if (0 == taskState.value) {
							cmsLog.debug("加扰任务状态是“未处理0”。任务ID：" + encryptList.getEncryptid());
						} else if (1 == taskState.value) {
							cmsLog.debug("加扰任务状态是“已分配1”。任务ID：" + encryptList.getEncryptid());
						} else if (2 == taskState.value) {
							cmsLog.debug("加扰任务状态是“处理中2”。任务ID：" + encryptList.getEncryptid());
						} else {
							cmsLog.warn("未知加扰任务状态。任务ID：" + encryptList.getEncryptid());
						}
						
						// 更新表
						try {
							encryptList.setDate4(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", addTime.value));
						} catch (ParseException e) {
							cmsLog.error("处理加扰任务异常。信息：" + e.getMessage());
							e.printStackTrace();
						}
						encryptList.setTaskprio(String.valueOf(taskPrio.value));
						encryptList.setState2(Long.valueOf(taskState.value));
						encryptList.setScip(sc.value);
						encryptListManager.update(encryptList);
						cmsLog.debug("加扰任务已更新。");
						break;
					} else {
						cmsLog.warn("节目包[ " + encryptList.getProductid() + " : " 
								+ encryptList.getProductname() 
								+ " ] 查询加扰任务状态出错, 第[ " + (i + 1) + " ]次尝试!");
						Thread.sleep(this.retryTime);
						
						if (2 == i) {
							// 失败: 返回值为其他，记录错误信息。
							cmsLog.warn(this.tryCount + " 次查询加扰任务失败。任务ID：" + encryptList.getEncryptid());
							cmsLog.warn("返回信息：" + taskResult.value);
							encryptList.setDealinfo(this.tryCount + " 次查询加扰状态出错, 请尝试重新加扰!");
							encryptList.setDealstate(Long.valueOf(9));
							encryptListManager.update(encryptList);
							
							TProductInfo productInfo = this.productInfoManager.getTProductInfoById(
									encryptList.getName());
							productInfo.setEncryptState(8L);
							this.productInfoManager.update(productInfo);
						}
					}
				}
			}
		}
	}
}
