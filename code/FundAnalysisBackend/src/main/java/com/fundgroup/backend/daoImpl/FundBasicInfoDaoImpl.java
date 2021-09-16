package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.mail.Session;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class FundBasicInfoDaoImpl implements FundBasicInfoDao {

  @Autowired
  private FundBasicInfoRepository fundBasicInfoRepository;

  @Override
  public List<FundBasicInfo> searchFundByCodeOrName(String searchStr, Integer pageIdx,
      Integer pageSize) {
    Pageable pageable = PageRequest.of(pageIdx, pageSize);
    return fundBasicInfoRepository.searchFundByCodeOrName(searchStr, pageable);
  }

  @Override
  public List<String> getAllCode() {
    return fundBasicInfoRepository.getAllCode();
  }

  @Override
  public List<FundBasicInfo> getAllFund() {
    return fundBasicInfoRepository.getAllFund();
  }

  @Override
  public FundBasicInfo getOne(String fundCode) {
    return fundBasicInfoRepository.getById(fundCode);
  }

  @Override
  public Integer countFundNumTimeRange(TimeType timeType) {
    LocalDate endDate = LocalDate.now();
    LocalDate beginDate = DateUtils.minusDayByTimeType(endDate, timeType);
    return fundBasicInfoRepository.countFundNumTimeRange(beginDate, endDate);
  }

  @Override
  public List<Object> getAllFundInfo() {
    return fundBasicInfoRepository.getAllFundInfo();
  }

  @Override
  public Integer getFundNumber() {
    return fundBasicInfoRepository.getFundNumber();
  }
}
