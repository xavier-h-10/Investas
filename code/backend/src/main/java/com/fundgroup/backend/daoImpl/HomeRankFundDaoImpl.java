package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.dto.FundPredictionView;
import com.fundgroup.backend.dto.HomeRankFund;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.entity.PredictionDescription;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundPredictionRepository;
import com.fundgroup.backend.repository.FundRateRepository;
import com.fundgroup.backend.repository.PredictionDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HomeRankFundDaoImpl implements HomeRankFundDao {

	@Autowired
	FundRateRepository fundRateRepository;

	@Autowired
	FundPredictionRepository fundPredictionRepository;

	@Autowired
	FundBasicInfoRepository fundBasicInfoRepository;

	@Autowired
	PredictionDescriptionRepository predictionDescriptionRepository;

	@Override
	public List<HomeRankFund> getHistoryTop(Integer page, Integer size, Integer type) {
		if (type <= 10 && type >= 0) {
			Pageable pageable = PageRequest.of(page, size);
			TimeType timeType = TimeType.values()[type];
			if (timeType == TimeType.LAST_ONE_DAY) {
				return fundRateRepository.getTopByOneDay(pageable);
			} else if (timeType == TimeType.LAST_ONE_WEEK) {
				return fundRateRepository.getTopByOneWeek(pageable);
			} else if (timeType == TimeType.LAST_ONE_MONTH) {
				return fundRateRepository.getTopByOneMonth(pageable);
			} else if (timeType == TimeType.LAST_THREE_MONTHS) {
				return fundRateRepository.getTopByThreeMonths(pageable);
			} else if (timeType == TimeType.LAST_SIX_MONTHS) {
				return fundRateRepository.getTopBySixMonths(pageable);
			} else if (timeType == TimeType.LAST_ONE_YEAR) {
				return fundRateRepository.getTopByOneYear(pageable);
			} else if (timeType == TimeType.LAST_TWO_YEARS) {
				return fundRateRepository.getTopByTwoYears(pageable);
			} else if (timeType == TimeType.LAST_THREE_YEARS) {
				return fundRateRepository.getTopByThreeYears(pageable);
			} else if (timeType == TimeType.LAST_FIVE_YEARS) {
				return fundRateRepository.getTopByFiveYears(pageable);
			} else if (timeType == TimeType.FROM_BEGINNING) {
				return fundRateRepository.getTopFromBeginning(pageable);
			} else if (timeType == TimeType.FROM_THIS_YEAR) {
				return fundRateRepository.getTopFromThisYear(pageable);
			}
		}
		return null;
	}

	@Override
	public List<HomeRankFund> getPredictionTop(Integer page, Integer size, Integer type) {
		if (type <= 3 && type >= 1) {
			Pageable pageable = PageRequest.of(page, size);
			if (type == 1) {
				return fundPredictionRepository.getTopByOneDay(pageable);
			} else if (type == 2) {
				return fundPredictionRepository.getTopByTwoDays(pageable);
			} else {
				return fundPredictionRepository.getTopByThreeDays(pageable);
			}
		}
		return null;
	}

	@Override
	public FundPredictionView getPredictionViewByFundCode(String fundCode) {
		FundPredictionView fundPredictionView = new FundPredictionView();
		if(!fundBasicInfoRepository.findById(fundCode).isPresent()||!fundPredictionRepository.findById(fundCode).isPresent())
			return null;

		FundBasicInfo fundBasicInfo = fundBasicInfoRepository.getById(fundCode);
		FundPrediction fundPrediction = fundPredictionRepository.getById(fundCode);

		fundPredictionView.setHasModel(fundBasicInfo.getHas_model());
		fundPredictionView.setLastUpdateDate(fundPrediction.getLastUpdateTimestamp());

		fundPredictionView.setOneDayNAV(fundPrediction.getFutureOneDayNAV());
		fundPredictionView.setTwoDayNAV(fundPrediction.getFutureTwoDaysNAV());
		fundPredictionView.setThreeDayNAV(fundPrediction.getFutureThreeDaysNAV());
		fundPredictionView.setOneDayQuote(fundPrediction.getFutureOneDayChange());
		fundPredictionView.setTwoDayQuote(fundPrediction.getFutureTwoDaysChange());
		fundPredictionView.setThreeDayQuote(fundPrediction.getFutureThreeDaysChange());

		PredictionDescription predictionDescription =
				predictionDescriptionRepository.getById(fundPrediction.getDescriptionId());
		fundPredictionView.setText(predictionDescription.getText());

		return fundPredictionView;
	}
}
