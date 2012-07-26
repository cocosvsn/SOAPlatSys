package com.soaplat.cmsmgr.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.soaplat.sysmgr.bean.Operator;

public class SessionListener implements HttpSessionListener {
	private static Logger cmsLogger = Logger.getLogger("Cms");
	private static Logger operatLogger = Logger.getLogger("operation");

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession session = se.getSession();
		cmsLogger.debug("Session创建! ID: " + session.getId());
		Operator operator = (Operator) session.getAttribute("user");
		cmsLogger.info("用户[ " + operator.getOperatorname() + " ] 登陆!");
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession session = se.getSession();
		cmsLogger.debug("Session销毁, ID: " + session.getId());
		Operator operator = (Operator) session.getAttribute("user");
		operatLogger.info("用户[ " + operator.getOperatorname() + " ] 登陆超时, 自动登出!");
	}

}
