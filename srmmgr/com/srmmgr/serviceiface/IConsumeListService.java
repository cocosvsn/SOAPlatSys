package com.srmmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.common.IBaseService;

/**
 * Title 		:the Interface IConsumeListService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IConsumeListService extends IBaseService {
	public List ExportConsumeList();
}
