package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.FundCompetition;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FundCompetitionDao {

  Integer createCompetition(Map<String, Object> value);

  List<FundCompetition> getAll();

  List<FundCompetition> removeById(Integer id);

  List<FundCompetition> getActivePublicCompetition(LocalDate date);

  List<FundCompetition> getActiveCompetition(LocalDate date);

  FundCompetition getFundCompetitionByCompeId(Integer competitionId);

  FundCompetition getActivePublicCompetitionByCompeId(Integer competitionId);
}
