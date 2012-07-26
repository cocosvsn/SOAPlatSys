package com.sbl.common;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soaplat.cmsmgr.util.Encrypt;
import com.soaplat.sysmgr.bean.Operator;
import com.soaplat.sysmgr.bean.OperatorInfo;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.serviceimpl.OperatorService;

import flex.messaging.FlexContext;

public class FlexProxy {
	private static Logger logger = LoggerFactory.getLogger("Cms");

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public Object invoke(String operName, ParamVo param, UserInf userinf)
			throws Exception {
		HttpSession session = FlexContext.getHttpRequest().getSession();
//		session.setMaxInactiveInterval(60);
		Cookie[] cookies = FlexContext.getHttpRequest().getCookies();
		String cookieSessionID = null;
		String checkCode = null;
		logger.debug("~~~~~~[ cookies ] ~~~~~~~~~~~~~~~~~~");
		for (Cookie c : cookies) {
			logger.debug("\t\tComment: {{}}", c.getComment());
			logger.debug("\t\tDomain: {{}}", c.getDomain());
			logger.debug("\t\tMaxAge: {{}}", c.getMaxAge());
			logger.debug("\t\tName: {{}}", c.getName());
			logger.debug("\t\tPath: {{}}", c.getPath());
			logger.debug("\t\tValue: {{}}", c.getValue());
			logger.debug("\t\tVersion: {{}}", c.getVersion());
			logger.debug("\t\tSecure: {{}}", c.getSecure());
			logger.debug("\t----------------------------------------->");
			if ("JSESSIONID".equals(c.getName())) {
				cookieSessionID = c.getValue();
			}
			if ("CheckCode".equals(c.getName())) {
				checkCode = c.getValue();
			}
		}
		logger.debug("~~~~~~[ cookies ] ~~~~~~~~~~~~~~~~~~");
		logger.debug("Session ID: [{}] user = {{}}" , session.getId(), session.getAttribute("user"));
		ReturnVo returnVo = new ReturnVo();
		returnVo.setUserVo(userinf);
		RefVo refVo = ParseXML.getInstance().getRef(operName);
		try {
			Object result = null;
			if ("Login".equals(operName)) {
				logger.debug("用户[{}]执行登陆动作!", param.getParam()[0]);
				result = executMethod(refVo, param);
				if (null == result) {
					throw new Exception("登陆失败");
				} else {
					logger.debug("用户登陆时Session ID: {}", session.getId());
					OperatorInfo oper = (OperatorInfo) result;
//					returnVo.getUserVo()
//							.setUserid((String) param.getParam()[0]);
//					returnVo.getUserVo().setPasswd(
//							Encrypt.encryptAES((String) param.getParam()[1], 
//									session.getId().substring(0, 16)));
					Operator operator = oper.getOperator();
					session.setAttribute("user", operator);
					Cookie cookie = new Cookie("CheckCode", Encrypt.encryptAES(
							param.getParam()[0] + ":|:" + param.getParam()[1],
							session.getId().substring(0, 16)));
					FlexContext.getHttpResponse().addCookie(cookie);
					logger.debug("登陆时用于加密的Key: {{}}, 登陆时加密后的密码: {{}}", 
							session.getId().substring(0, 16), cookie.getValue());
//					session
//							.setAttribute("userid",
//									(String) param.getParam()[0]);
//					session.setAttribute("passwd", md5.md5s((String) param
//							.getParam()[1]));
				}
			} else {
				if(null == session.getAttribute("user")) {
					logger.info("Session ID: [{}] 超时, 用户重新自动登陆!", session.getId());
					String eCode = Encrypt.decryptAES(checkCode, 
								cookieSessionID.substring(0,16));
					String[] userinfo = eCode.split(":\\|:");
					Operator operatorExample = new Operator();
					operatorExample.setUserid(userinfo[0]);
					operatorExample.setPassword(userinfo[1]);
					logger.debug("自动登陆时待解密的密码: {{}}, 自动登陆时解密的Key: {{}}, 自动登陆时解密后密码为: {{}}", 
							new Object[] {checkCode, cookieSessionID.substring(0, 16), eCode});
					if (null != operatorExample.getPassword()) {
						Operator operator = new OperatorService().getOperator(operatorExample);
						if (null != operator) {
							logger.debug("用户[{}]自动登陆成功! ", operatorExample.getUserid());
							session.setAttribute("user", operator);
//							returnVo.getUserVo().setPasswd(Encrypt.encryptAES(
//									operatorExample.getPassword(), session.getId().substring(0, 16)));
							Cookie cookie = new Cookie("CheckCode", Encrypt.encryptAES(
									userinfo[0] + ":|:" + userinfo[1],
									session.getId().substring(0, 16)));	
							FlexContext.getHttpResponse().addCookie(cookie);
							logger.debug("自动登陆成功用于加密的Key: {{}}, 自动登陆成功加密后的密码: {{}}", 
									session.getId().substring(0, 16), cookie.getValue());
						} else {
							logger.debug("用户[{}:{}]自动登陆失败! ", operatorExample.getUserid(), 
									operatorExample.getPassword());
						}
					} else {
						logger.debug("用户[{}:{}]自动登陆失败! ", operatorExample.getUserid(), 
								operatorExample.getPassword());
					}
				}
				result = executMethod(refVo, param);
			}
			returnVo.addObj(result);
			// return result;
		} catch (Exception e) {
			throw e;
		}
		return returnVo;
	}

	public Object executMethod(RefVo refVo, ParamVo param) {
		try {
			Object object = null;
			Class<?> clazz = Class.forName(refVo.getTheClass());
			if (null == refVo.getSpringRel()) {
				object = clazz.newInstance();
				logger.debug("当前采用反射获取Service对象: Class: {{}}, Object.Instance: {{}}", clazz, object);
			} else {
				object = ApplicationContextHolder.webApplicationContext.getBean(refVo.getSpringRel(), clazz);
				logger.debug("当前通过Spring.getBean()获取Service对象: Class: {{}}, Object.Instance: {{}}", clazz, object);
			}
			Object[] params = param.getParam();
			Method theMethod = clazz.getMethod(refVo.getMethod(), refVo
					.getCls());
			for (int i = 0; i < params.length; i++) {
				if (refVo.getCls()[i].equals(Class.forName("java.lang.Long")))
					params[i] = Long.parseLong(params[i].toString());
				else if(refVo.getCls()[i].equals(Class.forName("java.lang.Double")))
					params[i] = Double.parseDouble(params[i].toString());
			}
			return theMethod.invoke(object, params);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public static Operator getOperator(String userId) {
		HttpSession session = FlexContext.getHttpRequest().getSession();
		return (Operator) session.getAttribute(userId);
	}
}
