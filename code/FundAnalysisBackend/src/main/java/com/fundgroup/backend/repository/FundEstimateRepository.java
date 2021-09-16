package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundEstimateSimp;
import com.fundgroup.backend.entity.FundEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FundEstimateRepository extends JpaRepository<FundEstimate, BigInteger> {
    @Query("select new com.fundgroup.backend.dto.FundEstimateSimp(f.estimateTimestamp,f.NAVEstimate,f.increaseEstimate) from FundEstimate f where (f.fundCode=:fundCode and (f.estimateTimestamp>=:beginTime and f.estimateTimestamp<=:endTime)) order by f.estimateTimestamp")
    List<FundEstimateSimp> getFundEstimateRange(@Param("fundCode") String fundCode, @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);

    @Query("select new com.fundgroup.backend.dto.FundEstimateSimp(f.estimateTimestamp, f.NAVEstimate, f.increaseEstimate) " +
            "from FundEstimate f " +
            "where f.fundCode = :fundCode " +
            "order by f.estimateTimestamp DESC ")
    List<FundEstimateSimp> getFundEstimateRecent(@Param("fundCode") String fundCode, Pageable pageable);

    @Modifying
    @Query("delete from FundEstimate f where f.estimateTimestamp>=:beginTime and f.estimateTimestamp<=:endTime")
    void deleteRange(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
