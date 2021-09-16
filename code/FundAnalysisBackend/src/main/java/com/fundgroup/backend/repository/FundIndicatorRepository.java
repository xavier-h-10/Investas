package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface FundIndicatorRepository extends JpaRepository<FundIndicator, String> {

  @Query(value="select * from fund_indicator where fund_code=?1",nativeQuery = true)
  FundIndicator getFundIndicatorByFundCode(@Param("fundCode") String fundCode);

  @Query(value = "select avg(SHARP1) from fund_indicator where fund_type=?1 and SHARP1 is not null", nativeQuery = true)
  Double getAverageSHARP1(@Param("fundType") Integer fundType);

  @Query(value = "select avg(SHARP3) from fund_indicator where fund_type=?1 and SHARP3 is not null", nativeQuery = true)
  Double getAverageSHARP3(@Param("fundType") Integer fundType);

  @Query(value = "select avg(SHARP5) from fund_indicator where fund_type=?1 and SHARP5 is not null", nativeQuery = true)
  Double getAverageSHARP5(@Param("fundType") Integer fundType);

  @Query(value = "select avg(MAXRETRA1) from fund_indicator where fund_type=?1 and MAXRETRA1 is not null", nativeQuery = true)
  Double getAverageMAXRETRA1(@Param("fundType") Integer fundType);

  @Query(value = "select avg(MAXRETRA3) from fund_indicator where fund_type=?1 and MAXRETRA3 is not null", nativeQuery = true)
  Double getAverageMAXRETRA3(@Param("fundType") Integer fundType);

  @Query(value = "select avg(MAXRETRA5) from fund_indicator where fund_type=?1 and MAXRETRA5 is not null", nativeQuery = true)
  Double getAverageMAXRETRA5(@Param("fundType") Integer fundType);

  @Query(value = "select avg(STDDEV1) from fund_indicator where fund_type=?1 and STDDEV1 is not null", nativeQuery = true)
  Double getAverageSTDDEV1(@Param("fundType") Integer fundType);

  @Query(value = "select avg(STDDEV3) from fund_indicator where fund_type=?1 and STDDEV3 is not null", nativeQuery = true)
  Double getAverageSTDDEV3(@Param("fundType") Integer fundType);

  @Query(value = "select avg(STDDEV5) from fund_indicator where fund_type=?1 and STDDEV5 is not null", nativeQuery = true)
  Double getAverageSTDDEV5(@Param("fundType") Integer fundType);

  @Query(value = "select avg(PROFIT_Y) from fund_indicator where fund_type=?1 and PROFIT_Y is not null", nativeQuery = true)
  Double getAveragePROFIT_Y(@Param("fundType") Integer fundType);

  @Query(value = "select avg(PROFIT_1N) from fund_indicator where fund_type=?1 and PROFIT_1N is not null", nativeQuery = true)
  Double getAveragePROFIT_1N(@Param("fundType") Integer fundType);


  @Query(value = "SELECT @current_rank\\:= CASE WHEN @current_rank = fund_type AND PROFIT_Y IS NOT NULL THEN  @tmp_rank\\:=@tmp_rank +1 ELSE @tmp_rank\\:=1 END  tmp_rank, @current_rank\\:=fund_indicator.fund_type, fund_code FROM fund_indicator, (SELECT @tmp_rank\\:=0,@current_rank\\:=0) r ORDER BY fund_type desc, PROFIT_Y desc", nativeQuery = true)
  List<Map<String, Object>> getPROFIT_YRank();

  @Query(value = "SELECT @current_rank\\:= CASE WHEN @current_rank = fund_type AND PROFIT_1N IS NOT NULL THEN  @tmp_rank\\:=@tmp_rank +1 ELSE @tmp_rank\\:=1 END  tmp_rank, @current_rank\\:=fund_indicator.fund_type, fund_code FROM fund_indicator, (SELECT @tmp_rank\\:=0,@current_rank\\:=0) r ORDER BY fund_type desc, PROFIT_1N desc", nativeQuery = true)
  List<Map<String, Object>> getPROFIT_1NRank();

  @Query(value="select count(1) from fund_indicator where fund_type=?1",nativeQuery = true)
  Integer getTypeNumber(@Param("fundType") Integer fundType);

  @Query(value = "select f from FundIndicator f order by f.fundCode")
  List<FundIndicator> getAll();

  @Query("select new com.fundgroup.backend.dto.HomeFundIndicator(b.fundCode, b.fundName, r.lastOneYear, i.MAXRETRA1) " +
          "from FundBasicInfo b, FundRate r, FundIndicator i " +
          "where b.fundCode = r.fundCode and r.fundCode = i.fundCode and i.SHARP_1NRANK is not null and " +
          "i.MAXRETRA_1NRANK is not null and i.STDDEV_1NRANK is not null and i.PROFIT_1NRANK is not null and " +
          "r.lastOneYear <> -1.00 " +
          "order by i.SHARP_1NRANK * :sharp + i.MAXRETRA_1NRANK * :maxRet + i.STDDEV_1NRANK * :stdDev + " +
          "i.PROFIT_1NRANK * :profit")
  List<HomeFundIndicator> getHomeFundIndicator(@Param("sharp") Integer sharp, @Param("maxRet") Integer maxRet,
                                               @Param("stdDev") Integer stdDev, @Param("profit") Integer profit,
                                               Pageable pageable);
}
