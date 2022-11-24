package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundArchiveManager;
import com.fundgroup.backend.entity.FundManagerInfo;
import com.fundgroup.backend.dto.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundManagerInfoRepository extends JpaRepository<FundManagerInfo, String> {

	@Query("select new com.fundgroup.backend.dto.ManagerInfo(m.managerId, m.managerName, m.startTime, " +
			"m.managerDescription, m.managerUrl) from FundManagerInfo m where m.managerId=:id")
	ManagerInfo getManagerInfoDtoById(@Param("id") String id);

	@Query("select new com.fundgroup.backend.dto.FundArchiveManager(m.managerId, m.managerName, m.startTime, " +
			"f.startTime, f.repayRate, m.managerDescription, m.managerUrl) " +
			"from FundManagerInfo m, FundManagerTakeOfficeInfo f " +
			"where m.managerId = f.managerId and f.fundCode = :fundCode")
	List<FundArchiveManager> getFundArchiveManagerByFundCode(@Param("fundCode") String fundCode);

	@Query("select m.managerDescription " +
			"from FundManagerInfo m, FundManagerTakeOfficeInfo f " +
			"where f.fundCode = :fundCode and m.managerId = f.managerId")
	List<String> getManagerDescriptionByFundCode(@Param("fundCode") String fundCode);
}
