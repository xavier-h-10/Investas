package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundDescription;
import com.fundgroup.backend.entity.FundMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface FundMessageRepository extends JpaRepository<FundMessage, BigInteger> {
	@Query("select new com.fundgroup.backend.dto.FundDescription(f.messageTitle, f.messageContent) " +
			"from FundMessage f " +
			"where f.fundCode = :fundCode")
	List<FundDescription> getFundDescriptionByFundCode(@Param("fundCode") String fundCode);
}
