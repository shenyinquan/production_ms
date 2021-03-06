package org.hqu.production_ms.service;

import org.hqu.production_ms.domain.COrder;
import org.hqu.production_ms.domain.ProcessCountCheck;
import org.hqu.production_ms.domain.custom.CustomResult;
import org.hqu.production_ms.domain.custom.EUDataGridResult;
import org.hqu.production_ms.domain.po.COrderPO;

public interface PCountCheckService {
	
	EUDataGridResult getList(int page, int rows, ProcessCountCheck processCountCheck) throws Exception;
	
	COrder get(String string) throws Exception;
	
	CustomResult delete(String string) throws Exception;

	CustomResult deleteBatch(String[] ids) throws Exception;

	CustomResult insert(ProcessCountCheck processCountCheck) throws Exception;

	//更新部分字段，用的是updateSelective判断非空的字段进行更新
    CustomResult update(COrderPO cOrder) throws Exception;
    
    //更新全部字段，不判断非空，直接进行更新
    CustomResult updateAll(ProcessCountCheck processCountCheck) throws Exception;
    
    CustomResult updateNote(ProcessCountCheck processCountCheck) throws Exception;

    CustomResult changeStatus(String[] ids) throws Exception;
    
    EUDataGridResult searchPCountCheckByPCountCheckId(int page, int rows, 
			String pCountCheckId) throws Exception;
}
