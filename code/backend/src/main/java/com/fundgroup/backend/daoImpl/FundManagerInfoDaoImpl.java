package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundManagerInfoDao;
import com.fundgroup.backend.dto.ManagerInfo;
import com.fundgroup.backend.dto.ManagerInfoFund;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundManagerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundManagerInfoDaoImpl implements FundManagerInfoDao {
	@Autowired
	FundManagerInfoRepository fundManagerInfoRepository;
	@Autowired
	FundBasicInfoRepository fundBasicInfoRepository;

	@Override
	public ManagerInfo getFundManagerInfoById(String id) {
		ManagerInfo ret = fundManagerInfoRepository.getManagerInfoDtoById(id);
		if (ret == null) {
			return null;
		}
		List<ManagerInfoFund> fundList = fundBasicInfoRepository.getFundByManagerId(id);
		ret.setFundList(fundBasicInfoRepository.getFundByManagerId(id));
		return ret;
	}
}
