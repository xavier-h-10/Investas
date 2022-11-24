package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.FundCard;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.service.FundBasicInfoService;
import com.fundgroup.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FundBasicInfoServiceImpl implements FundBasicInfoService {

	@Autowired
	private FundBasicInfoDao fundBasicInfoDao;

	@Autowired
	private FundAssemblyCache fundAssemblyCache;

	@Override
	public List<FundBasicInfo> searchFundByCodeOrName(String searchStr, Integer pageIdx, Integer pageSize) {
		return fundBasicInfoDao.searchFundByCodeOrName(searchStr, pageIdx, pageSize);
	}

	@Override
	public FundCard searchFundCardByCode(String fundCode) {
		FundAssembly fundAssembly = fundAssemblyCache.getFundAssembly(fundCode);
		System.out.println("card:" + fundAssembly.getFundCode());
		return new FundCard(fundAssembly.getFundCode(), fundAssembly.getFundName(), fundAssembly.getFundType(),
				fundAssembly.getNAV(), fundAssembly.getAccumulativeNAV(), fundAssembly.getFundRating(), fundAssembly.getUpdateDate(),
				fundAssembly.getLastOneDayRate(), fundAssembly.getLastOneYearRate(), fundAssembly.getFromBeginningRate());
	}
}
