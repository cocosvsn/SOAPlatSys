package com.cbs.cbsmgr.serviceiface;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.dto.AccountDTO;

public interface IAccountService extends IBaseService {

	public Account createAccount(Long customerId, AccountDTO accountDTO,
            Long accountType)/* throws CBSException*/ ;
}
