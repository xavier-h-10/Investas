package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundManagerInfoDao;
import com.fundgroup.backend.dto.ManagerInfo;
import com.fundgroup.backend.service.FundManagerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundManagerInfoServiceImpl implements FundManagerInfoService {
	@Autowired
	FundManagerInfoDao fundManagerInfoDao;

	@Override
	public ManagerInfo getFundManagerInfoById(String id) {
		return fundManagerInfoDao.getFundManagerInfoById(id);
	}
}
