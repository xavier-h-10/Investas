package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundCodeType;
import com.fundgroup.backend.dto.FundArchive;
import com.fundgroup.backend.dto.FundArchiveDetail;
import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.dto.ManagerInfoFund;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FundBasicInfoRepository extends JpaRepository<FundBasicInfo, String> {
        @Query("select f from FundBasicInfo f where ((f.fundCode like concat('%',:searchStr,'%')) or (f.fundName like concat('%',:searchStr,'%')))")
        List<FundBasicInfo> searchFundByCodeOrName(@Param("searchStr") String searchStr, Pageable pageable);

        @Query("select f.fundCode from FundBasicInfo f")
        List<String> getAllCode();

        @Query("select f from FundBasicInfo f")
        List<FundBasicInfo> getAllFund();

        @Query(value = "select new com.fundgroup.backend.dto.FundCodeType(f.fundCode, f.fundType, f.has_model) from "
                        + "FundBasicInfo f")
        List<FundCodeType> getAllFundCodeType();

        @Query("select new com.fundgroup.backend.dto.ManagerInfoFund(f.fundCode, f.fundName, m.startTime, m.repayRate) "
                        + "from FundBasicInfo f, FundManagerTakeOfficeInfo m "
                        + "where m.managerId = :managerId and f.fundCode = m.fundCode")
        List<ManagerInfoFund> getFundByManagerId(@Param("managerId") String managerId);
  
        @Query("select f.fundCode, f.fundType  from FundBasicInfo f")
        List<Object> getAllFundInfo();
  
        @Query("select new com.fundgroup.backend.dto.FundArchive(f.fundCode, f.assetSize, f.fundEstablishDate) "
                        + "from FundBasicInfo f " + "where f.fundCode = :fundCode")
        FundArchive getFundArchiveByFundCode(@Param("fundCode") String fundCode);

        @Query("select new com.fundgroup.backend.dto.FundArchiveDetail(f.fundName, f.fundCode, f.fundEstablishDate,"
                        + " f.assetSize, f.fundManagerCode, f.custodianName) " + "from FundBasicInfo f "
                        + "where f.fundCode = :fundCode")
        FundArchiveDetail getFundArchiveDetailByFundCode(@Param("fundCode") String fundCode);

        @Query("select f.fundName from FundBasicInfo f where f.fundCode = :fundCode")
        String getFundNameByFundCode(@Param("fundCode") String fundCode);

    @Query("select count(f) from FundBasicInfo f where (f.fundEstablishDate<=:beginDate and f.fundEndDate>=:endDate)")
    Integer countFundNumTimeRange(@Param("beginDate") LocalDate beginDate, @Param("endDate")LocalDate endDate);

    @Query("select count(f) from FundBasicInfo f")
    Integer getFundNumber();

  @Query("select p.fundType, count(p.fundCode)"
      + "from FundBasicInfo as p group by p.fundType order by p.fundType")
    List<Object> getTypeAndNum();
}
