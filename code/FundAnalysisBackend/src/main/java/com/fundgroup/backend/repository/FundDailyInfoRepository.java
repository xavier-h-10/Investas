package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundDailyInfo;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface FundDailyInfoRepository extends JpaRepository<FundDailyInfo, BigInteger> {
    @Query("select f from FundDailyInfo f where f.fundCode=:fundCode order by f.updateDate")
    List<FundDailyInfo> getFundDailyInfoByCode(@Param("fundCode") String fundCode, Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.FundDailySimp(f.updateDate,f.NAV, f.accumulativeNAV) from FundDailyInfo f where f.fundCode=:fundCode order by f.updateDate DESC")
    List<FundDailySimp> getFundDailyDetailFourByCode(@Param("fundCode") String fundCode, Pageable pageable);

    @Query("select f from FundDailyInfo f where f.fundCode=:fundCode and f.updateDate=:date")
    FundDailyInfo getFundDailyInfoByCodeDate(@Param("fundCode") String fundCode,@Param("date") LocalDate date);

    @Query("select new com.fundgroup.backend.dto.FundDailySimp(f.updateDate,f.NAV,f.accumulativeNAV) from FundDailyInfo f where (f.fundCode=:fundCode and f.updateDate>=:beginDate and f.updateDate<=:endDate) order by f.updateDate")
    List<FundDailySimp> getFundDailyRangeSimp(@Param("fundCode") String fundCode,@Param("beginDate") LocalDate beginDate,@Param("endDate") LocalDate endDate);

    @Query("select f from FundDailyInfo f where (f.fundCode=:fundCode and f.updateDate>=:beginDate and f.updateDate<=:endDate) order by f.updateDate")
    List<FundDailyInfo> getFundDailyRange(@Param("fundCode") String fundCode,@Param("beginDate") LocalDate beginDate,@Param("endDate") LocalDate endDate);

    @Query(value = "select NAV from fund_daily_info where fund_code = :fundCode order by update_date desc limit :num", nativeQuery = true)
    List<Double> getNewestNAVs(@Param("fundCode") String fundCode, @Param("num") Integer num);

    @Query("select count(f) from FundDailyInfo f where f.updateDate=:updateDate")
    Integer getDailyInfoCountByUpdateDate(@Param("updateDate") LocalDate updateDate);

    FundDailyInfo findFirstByFundCodeOrderByUpdateDate(String fundCode);

//    @Query(value = "select update_date, count(fund_code)"
//        + "from fundSystem.fund_daily_info where update_date in (select update_date from "
//        + "fundSystem.fund_daily_info order by update_date limit 1)", nativeQuery = true)
//    Object getMonitorDate();

    @Query("select new com.fundgroup.backend.dto.MonitorTimeNum(p.updateDate, count(p.fundCode))"
        + "from FundDailyInfo p group by p.updateDate order by p.updateDate DESC")
    List<MonitorTimeNum> getMonitorHistory(Pageable pageable);



}
