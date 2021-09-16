package com.fundgroup.backend.daoImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.FundCompetitionDao;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.repository.FundCompetitionRepository;
import com.fundgroup.backend.security.UserInfoHelper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FundCompetitionDaoImpl implements FundCompetitionDao {

	@Autowired
	FundCompetitionRepository fundCompetitionRepository;

	@Autowired
	UserInfoHelper userInfoHelper;

//  @Autowired
//  void setUserInfoHelper(UserInfoHelper userInfoHelper){
//    this.userInfoHelper = userInfoHelper;
//  }
//
//  @Autowired
//  void setFundCompetitionRepository(FundCompetitionRepository fundCompetitionRepository){
//    this.fundCompetitionRepository = fundCompetitionRepository;
//  }


	@Override
	public Integer createCompetition(Map<String, Object> value) {

//    data parse
		String title = String.valueOf(value.get("title"));
		String description = String.valueOf(value.get("goal"));
		BigInteger people = BigInteger.valueOf(Long.parseLong(String.valueOf(value.get("people"))));
		BigInteger initial = BigInteger.valueOf(Long.parseLong(String.valueOf(value.get("initial"))));

		String date = String.valueOf(value.get("date"));
		date = date.substring(1, date.length() - 1);
		String[] start_end = date.split(",");

		Long userId = userInfoHelper.getUserId();
		if (userId == -1L) {
			return -1;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate start = LocalDate.parse(start_end[0], formatter);
		LocalDate end = LocalDate.parse(start_end[1].substring(1), formatter);

		boolean isPublic = Objects.equals(String.valueOf(value.get("publicType")), "0");

		String allowedString = String.valueOf(value.get("select-type"));
		Vector<Integer> allowedType = new Vector<>();
		String[] allowed = allowedString.substring(0, allowedString.length() - 1).split(",");
		for (String type : allowed) {
			type = type.substring(2);
			if (!Objects.equals(type, "6") && !Objects.equals(type, "13")
					&& !Objects.equals(type, "14")) {
				allowedType.add(Integer.valueOf(type));
			}
		}
		Integer allowedTypeInt = FundCompetition.vector2Bigint(allowedType);
		System.out.println(allowedTypeInt);

		FundCompetition fundCompetition = new FundCompetition(
				userId, title, start, end, description, people, initial, allowedTypeInt, !isPublic);

		fundCompetition = fundCompetitionRepository.saveAndFlush(fundCompetition);
		return fundCompetition.getCompetitionId();
	}

	@Override
	public List<FundCompetition> getAll() {
		return fundCompetitionRepository.findAll();
	}

	@Override
	public List<FundCompetition> removeById(Integer id) {
		fundCompetitionRepository.deleteById(id);
		fundCompetitionRepository.flush();
		return fundCompetitionRepository.findAll();
	}

	@Override
	public List<FundCompetition> getActivePublicCompetition(LocalDate date) {
		return fundCompetitionRepository.getActivePublicCompetition(date);
	}

	@Override
	public List<FundCompetition> getActiveCompetition(LocalDate date) {
		return fundCompetitionRepository.getActiveCompetition(date);
	}

	@Override
	public FundCompetition getFundCompetitionByCompeId(Integer competitionId) {
		return fundCompetitionRepository.getFundCompetitionByCompeId(competitionId);
	}

	@Override
	public FundCompetition getActivePublicCompetitionByCompeId(Integer competitionId) {
		return fundCompetitionRepository.getActivePublicCompetitionByCompeId(competitionId, LocalDate.now());
	}
}
