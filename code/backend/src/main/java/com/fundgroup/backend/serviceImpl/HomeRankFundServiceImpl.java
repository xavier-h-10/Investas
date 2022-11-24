package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.dto.HomeRankFund;
import com.fundgroup.backend.service.HomeRankFundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class HomeRankFundServiceImpl implements HomeRankFundService {

	private HashMap<String, List<HomeRankFund>> dataMap = new HashMap<>();
	private HashMap<String, LocalDate> dateMap = new HashMap<>();
	private static final Integer MAX_SIZE = 100000;

	private final Logger logger = LoggerFactory.getLogger(HomeRankFundServiceImpl.class);

	private String historyKey(Integer type) {
		return "h" + type.toString();
	}

	private String predictionKey(Integer type) {
		return "p" + type.toString();
	}

	@Autowired
	HomeRankFundDao homeRankFundDao;

	@Override
	public void clearCache() {
		dataMap.clear();
		dateMap.clear();
		logger.info("Cache Cleared.");
	}

	@Override
	public List<HomeRankFund> getHistoryTop(Integer page, Integer size, Integer type) {
		if (type <= 10 && type >= 0) {
			String key = historyKey(type);
			List<HomeRankFund> data;
			if (dataMap.get(key) == null || !Objects.equals(dateMap.get(key), LocalDate.now())) {
				data = homeRankFundDao.getHistoryTop(0, MAX_SIZE, type);
				dateMap.put(key, LocalDate.now());
				dataMap.put(key, data);
			} else {
				data = dataMap.get(key);
			}
			try {
				return data.subList(page * size, (page + 1) * size);
			} catch (IndexOutOfBoundsException ex) {
				logger.info("Catch IndexOutOfBoundsException.");
				return null;
			}
		}
		return null;
	}

	@Override
	public List<HomeRankFund> getPredictionTop(Integer page, Integer size, Integer type) {
		if (type <= 3 && type >= 1) {
			String key = predictionKey(type);
			List<HomeRankFund> data;
			if (dataMap.get(key) == null || !Objects.equals(dateMap.get(key), LocalDate.now())) {
				data = homeRankFundDao.getPredictionTop(0, MAX_SIZE, type);
				dateMap.put(key, LocalDate.now());
				dataMap.put(key, data);
			} else {
				data = dataMap.get(key);
			}
			try {
				return data.subList(page * size, (page + 1) * size);
			} catch (IndexOutOfBoundsException ex) {
				logger.info("Catch IndexOutOfBoundsException.");
				return null;
			}
		}
		return null;
	}
}
