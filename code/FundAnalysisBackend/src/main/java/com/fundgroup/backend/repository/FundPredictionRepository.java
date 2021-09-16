package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.HomeRankFund;
import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.dto.ShowPredictionDescription;
import com.fundgroup.backend.entity.FundPrediction;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

public interface FundPredictionRepository extends JpaRepository<FundPrediction, String> {

	FundPrediction getFundPredictionByFundCode(String fundCode);

	@Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, p.futureOneDayChange) " +
			"from FundBasicInfo b, FundPrediction p " +
			"where b.fundCode = p.fundCode and b.fundType <> 6 " +
			"order by p.futureOneDayChange DESC")
	List<HomeRankFund> getTopByOneDay(Pageable pageable);

	@Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, p.futureTwoDaysChange) " +
			"from FundBasicInfo b, FundPrediction p " +
			"where b.fundCode = p.fundCode and b.fundType <> 6 " +
			"order by p.futureTwoDaysChange DESC")
	List<HomeRankFund> getTopByTwoDays(Pageable pageable);

	@Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, p.futureThreeDaysChange) " +
			"from FundBasicInfo b, FundPrediction p " +
			"where b.fundCode = p.fundCode and b.fundType <> 6 " +
			"order by p.futureThreeDaysChange DESC")
	List<HomeRankFund> getTopByThreeDays(Pageable pageable);

	@Query("select p.lastUpdateTimestamp, count(p.fundCode)"
			+ "from FundPrediction p group by p.lastUpdateTimestamp order by p.lastUpdateTimestamp DESC")
	List<Object> getMonitorDate(LocalDateTime localDateTime);

	@Modifying
	@Transactional
	@Query(value = "update fundSystem.fund_prediction set fundSystem.fund_prediction.description_id = "
			+ ":descriptionId where fundSystem.fund_prediction.fund_code = :fundCode",
					nativeQuery = true)
	void updateDescription(@RequestParam(value = "fundCode")String fundCode,
			@RequestParam(value = "descriptionId")Integer descriptionId);

	@Query("select new com.fundgroup.backend.dto.ShowPredictionDescription(f.descriptionId, count(f.fundCode)) " +
			"from FundPrediction f group by f.descriptionId")
	List<ShowPredictionDescription> getFundPredictionDescription();
}
