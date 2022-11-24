package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundRateRank;
import com.fundgroup.backend.dto.HomeRankFund;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.dto.FundRateRecent;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FundRateRepository extends JpaRepository<FundRate,String> {

    @Query("select new com.fundgroup.backend.dto.FundRateRecent(f.fundCode,f.lastOneWeek,f.lastOneMonth,f.lastThreeMonths,f.lastSixMonths,f.lastOneYear)" +
            " from FundRate f where f.fundCode=:fundCode")
    FundRateRecent getRateInOneYear(@Param("fundCode") String fundCode);

    FundRate getFundRateByFundCode(String fundCode);

    @Query(value = "select * from fund_yield_rate order by "
        + "last_one_month DESC LIMIT 8", nativeQuery = true)
    List<FundRate> getTopFundRateByMonth();

    @Query(value = "select * from fund_yield_rate order by "
        + "last_one_year DESC LIMIT 8", nativeQuery = true)
    List<FundRate> getTopFundRateByYear();

    @Query(value="select fund_code0 as fund_code,cast(last_one_day_rank as unsigned),cast(last_one_week_rank as unsigned),cast(last_one_month_rank as unsigned),cast(last_three_months_rank as unsigned),cast(last_six_months_rank as unsigned),cast(last_one_year_rank as unsigned)," +
            "cast(last_two_years_rank as unsigned),cast(last_three_years_rank as unsigned),cast(last_five_years_rank as unsigned),cast(from_beginning_rank as unsigned),cast(from_this_year_rank as unsigned) from" +
            "(" +
            "select fund_code0,last_one_day_rank,last_one_week_rank,last_one_month_rank,last_three_months_rank,last_six_months_rank,last_one_year_rank " +
            "from " +
            "(select fund_code as fund_code0, @curRank0\\:=@curRank0+1 AS last_one_day_rank from fund_yield_rate f,(SELECT @curRank0\\:= 0) r order by last_one_day desc) as LAST_ONE_DAY " +
            "join (select fund_code, @curRank1\\:=@curRank1+1 AS last_one_week_rank from fund_yield_rate f,(SELECT @curRank1\\:= 0) r order by last_one_week desc) as LAST_ONE_WEEK " +
            "join (select fund_code, @curRank2\\:=@curRank2+1 AS last_one_month_rank from fund_yield_rate f,(SELECT @curRank2\\:= 0) r order by last_one_month desc) as LAST_ONE_MONTH " +
            "join (select fund_code, @curRank3\\:=@curRank3+1 AS last_three_months_rank from fund_yield_rate f,(SELECT @curRank3\\:= 0) r order by last_three_months desc) as LAST_THREE_MONTHS " +
            "join (select fund_code, @curRank4\\:=@curRank4+1 AS last_six_months_rank from fund_yield_rate f,(SELECT @curRank4\\:= 0) r order by last_six_months desc) as LAST_SIX_MONTHS " +
            "join (select fund_code, @curRank5\\:=@curRank5+1 AS last_one_year_rank from fund_yield_rate f,(SELECT @curRank5\\:= 0) r order by last_one_year desc) as LAST_ONE_YEAR " +
            "on LAST_ONE_DAY.fund_code0=LAST_ONE_WEEK.fund_code and LAST_ONE_DAY.fund_code0=LAST_ONE_MONTH.fund_code and LAST_ONE_DAY.fund_code0=LAST_THREE_MONTHS.fund_code  " +
            "and LAST_ONE_DAY.fund_code0=LAST_SIX_MONTHS.fund_code and LAST_ONE_DAY.fund_code0=LAST_ONE_YEAR.fund_code " +
            ") AS T0 " +
            "join" +
            "(" +
            "select fund_code1,last_two_years_rank,last_three_years_rank,last_five_years_rank,from_beginning_rank,from_this_year_rank " +
            "from " +
            "(select fund_code as fund_code1, @curRank6\\:=@curRank6+1 AS last_two_years_rank from fund_yield_rate f,(SELECT @curRank6\\:= 0) r order by last_two_years desc) as LAST_TWO_YEARS " +
            "join (select fund_code, @curRank7\\:=@curRank7+1 AS last_three_years_rank from fund_yield_rate f,(SELECT @curRank7\\:= 0) r order by last_three_years desc) as LAST_THREE_YEARS " +
            "join (select fund_code, @curRank8\\:=@curRank8+1 AS last_five_years_rank from fund_yield_rate f,(SELECT @curRank8\\:= 0) r order by last_five_years desc) as LAST_FIVE_YEARS " +
            "join (select fund_code, @curRank9\\:=@curRank9+1 AS from_beginning_rank from fund_yield_rate f,(SELECT @curRank9\\:= 0) r order by from_beginning desc) as FROM_BEGINNING " +
            "join (select fund_code, @curRank10\\:=@curRank10+1 AS from_this_year_rank from fund_yield_rate f,(SELECT @curRank10\\:= 0) r order by from_this_year desc) as FROM_THIS_YEAR " +
            "on LAST_TWO_YEARS.fund_code1=LAST_THREE_YEARS.fund_code and LAST_TWO_YEARS.fund_code1=LAST_FIVE_YEARS.fund_code and LAST_TWO_YEARS.fund_code1=FROM_BEGINNING.fund_code and LAST_TWO_YEARS.fund_code1=FROM_THIS_YEAR.fund_code " +
            ") AS T1 " +
            "on T0.fund_code0=T1.fund_code1",nativeQuery = true)
    List<Object[]> getAllFundRateRank();

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastOneDay) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastOneDay DESC")
    List<HomeRankFund> getTopByOneDay(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastOneWeek) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastOneWeek DESC")
    List<HomeRankFund> getTopByOneWeek(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastOneMonth) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastOneMonth DESC")
    List<HomeRankFund> getTopByOneMonth(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastThreeMonths) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastThreeMonths DESC")
    List<HomeRankFund> getTopByThreeMonths(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastSixMonths) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastSixMonths DESC")
    List<HomeRankFund> getTopBySixMonths(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastOneYear) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastOneYear DESC")
    List<HomeRankFund> getTopByOneYear(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastTwoYears) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastTwoYears DESC")
    List<HomeRankFund> getTopByTwoYears(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastThreeYears) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastThreeYears DESC")
    List<HomeRankFund> getTopByThreeYears(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.lastFiveYears) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.lastFiveYears DESC")
    List<HomeRankFund> getTopByFiveYears(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.fromBeginning) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.fromBeginning DESC")
    List<HomeRankFund> getTopFromBeginning(Pageable pageable);

    @Query("select new com.fundgroup.backend.dto.HomeRankFund(b.fundCode, b.fundName, r.fromThisYear) " +
            "from FundBasicInfo b, FundRate r " +
            "where b.fundCode = r.fundCode and b.fundType <> 6 " +
            "order by r.fromThisYear DESC")
    List<HomeRankFund> getTopFromThisYear(Pageable pageable);

    @Query("select count(f) from FundRate f where f.lastUpdateTimestamp>=:date")
    Integer getDailyInfoCountByUpdateDate(@Param("date") LocalDate date);

    @Query("select p.lastUpdateTimestamp, count(p.fundCode)"
        + "from FundRate p group by p.lastUpdateTimestamp order by p.lastUpdateTimestamp DESC")
    Object getMonitorData();
}
