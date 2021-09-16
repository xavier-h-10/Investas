package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.dto.*;
import com.fundgroup.backend.entity.FundPortfolio;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.repository.FundManagerInfoRepository;
import com.fundgroup.backend.repository.FundRateRepository;
import com.fundgroup.backend.service.FundBasicInfoService;
import com.fundgroup.backend.service.FundPortfolioService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FundViewController {
	@Autowired
	private FundRateRepository fundRateRepository;
	@Autowired
	private FundManagerInfoRepository fundManagerInfoRepository;
	@Autowired
	private FundPortfolioService fundPortfolioService;
	@Autowired
	private FundBasicInfoService fundBasicInfoService;
	@Autowired
	private FundAssemblyCache fundAssemblyCache;
	@Autowired
	private StockCache stockCache;

	private JSONArray fundPortfolio(String fundCode) {
		List<FundPortfolio> fundPortfolioList = fundPortfolioService.getFundPortfolioByCode(fundCode);
		JSONArray jsonArray = new JSONArray();
		for (FundPortfolio fundPortfolio : fundPortfolioList) {
			StockBasicInfo stockBasicInfo = stockCache.getStockInfoById(fundPortfolio.getStockId());
			Double value = stockBasicInfo.getNewestRate();
			JSONObject stockDetail = new JSONObject();
			if (value == null) {

				stockDetail.put("value", "");
			} else {
				stockDetail.put("value", value);
			}
			stockDetail.put("id", fundPortfolio.getStockId());
			stockDetail.put("name", fundPortfolio.getStockName());
			stockDetail.put("proportion", fundPortfolio.getPercentage());

			jsonArray.add(stockDetail);
		}
		return jsonArray;
	}

	private JSONArray stockDailyInfoArray(String stockId) {
		List<StockDailySimp> stockDailySimps = stockCache.getDailyInfoById(stockId);
		if (stockDailySimps.isEmpty())
			return null;
		JSONArray jsonArray = new JSONArray();
		for (StockDailySimp stockDailySimp : stockDailySimps) {
			JSONObject stockDetail = new JSONObject();
			stockDetail.put("date", stockDailySimp.getUpdateDate());
			stockDetail.put("price", stockDailySimp.getStockPrice());
			jsonArray.add(stockDetail);
		}
		return jsonArray;
	}

	/**
	 * TODO: optimize stock and archive 106/124
	 *
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/anyone/fund/view", method = RequestMethod.GET)
	public Message getFundView(@RequestParam("code") String code) {
		FundView ret = new FundView();

		FundAssembly fundAssembly = fundAssemblyCache.getFundAssembly(code);
		if (fundAssembly == null) {
			return new Message(MessageUtil.SUCCESS, (JSONObject) JSON.toJSON(new FundView()));
		}

//		FundArchive archive = fundAssemblyCache.getFundArchiveByFundCode(code);
		FundArchive archive = new FundArchive(fundAssembly.getFundCode(), fundAssembly.getAssetSize(),
				fundAssembly.getFundEstablishDate());

		List<FundArchiveManager> managerList = fundManagerInfoRepository.getFundArchiveManagerByFundCode(code);

		archive.setManagerList(managerList);
//		JSONArray stockList = fundPortfolio(code);

		archive.setStockList(null);

//		FundCard fundCard = fundBasicInfoService.searchFundCardByCode(code);
		FundCard fundCard = new FundCard(fundAssembly.getFundCode(), fundAssembly.getFundName(),
				fundAssembly.getFundType(), fundAssembly.getNAV(), fundAssembly.getAccumulativeNAV(),
				fundAssembly.getFundRating(), fundAssembly.getUpdateDate(), fundAssembly.getLastOneDayRate(),
				fundAssembly.getLastOneYearRate(), fundAssembly.getFromBeginningRate());
		ret.setCard(fundCard);
		ret.setArchive(archive);

//		FundRate fundRate = fundRateRepository.getFundRateByFundCode(code);
		FundRateRecent fundRate = new FundRateRecent(code, fundAssembly.getLastOneWeekRate(),
				fundAssembly.getLastOneMonthRate(), fundAssembly.getLastThreeMonthRate(),
				fundAssembly.getLastSixMonthRate(), fundAssembly.getLastOneYearRate());
		ret.setHistoryPerformance(fundRate);

//		FundRateRank fundRateRank = fundAssemblyCache.getFundRateRankHalfByCode(code);
		FundRateRank fundRateRank = new FundRateRank(null, fundAssembly.getLastOneDayRank(),
				fundAssembly.getLastOneWeekRank(), fundAssembly.getLastOneMonthRank(),
				fundAssembly.getLastThreeMonthRank(), fundAssembly.getLastSixMonthRank(),
				fundAssembly.getLastOneYearRank(), fundAssembly.getLastTwoYearsRank(),
				fundAssembly.getLastThreeYearsRank(), fundAssembly.getLastFiveYearsRank(),
				fundAssembly.getFromBeginningRank(), fundAssembly.getFromThisYearRank());
		ret.setFundRateRank(fundRateRank);

		FundRateRank fundRateTotalCount = fundAssemblyCache.getFundRateTotalCount();
		ret.setFundRateTotalCount(fundRateTotalCount);

//		FundPredictionView fundPredictionView = fundAssemblyCache.getFundPredictionView(code);
		FundPredictionView fundPredictionView = fundAssembly.getFundPredictionView();
		ret.setFundPredictionView(fundPredictionView);

		return new Message(MessageUtil.SUCCESS, (JSONObject) JSON.toJSON(ret));
	}

	@RequestMapping(value = "/anyone/fund/stock", method = RequestMethod.GET)
	public Message getStockList(@RequestParam("code") String code) {
		JSONArray stockList = fundPortfolio(code);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", stockList);
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	/**
	 * TODO:integrate into fund view and remove test
	 */
	@RequestMapping(value = "/anyone/fundview/test", method = RequestMethod.GET)
	public Message testGetStockDaily(@RequestParam("stockId") String stockId) {
		JSONArray jsonArray = stockDailyInfoArray(stockId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stock", jsonArray);
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}
}
