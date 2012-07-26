package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListManager extends IBaseManager {

	public List getProgListsByIdActAndPop(String idAct, String pop);
}
