package com.fundgroup.backend.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.fundgroup.backend.dao.FundIndicatorDao;
import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import com.fundgroup.backend.service.FundIndicatorService;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundIndicatorServiceImpl implements FundIndicatorService {

	@Autowired
	private FundIndicatorDao fundIndicatorDao;

//	@Autowired
//	void setFundIndicatorDao(FundIndicatorDao fundIndicatorDao) {
//		this.fundIndicatorDao = fundIndicatorDao;
//	}

	@Override
	public void fetchFundIndicator() {
		fundIndicatorDao.fetchFundIndicator();
	}

	@Override
	public JSONArray getFundIndicatorByCode(String fundCode) {
		return fundIndicatorDao.getFundIndicatorByCode(fundCode);
	}

	@Override
	public void processData() {
		fundIndicatorDao.processData();
	}


	@Override
	public Runnable wrappedUpdateService() {
		return () -> {
			System.out.println(LocalDateTime.now());
			System.out.println("updating fund indicator...");
			fundIndicatorDao.fetchFundIndicator();
			fundIndicatorDao.processData();
		};
	}

	@Override
	public List<Integer> getFundIndicatorNumber(String fundCode) {
		return fundIndicatorDao.getFundIndicatorNumber(fundCode);
	}

	@Override
	public List<HomeFundIndicator> getHomeFundIndicator(Integer sharp, Integer maxRet, Integer stdDev, Integer profit,
														Integer page, Integer size) {
		return fundIndicatorDao.getHomeFundIndicator(sharp, maxRet, stdDev, profit, page, size);
	}
}
