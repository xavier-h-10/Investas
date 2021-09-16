package com.fundgroup.backend.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundIndicatorDao;
import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import com.fundgroup.backend.entity.FundIndicatorAverage;
import com.fundgroup.backend.repository.FundIndicatorAverageRepository;
import com.fundgroup.backend.repository.FundIndicatorRepository;
import com.fundgroup.backend.service.FundIndicatorService;
import com.fundgroup.backend.utils.BatchUtils;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class FundIndicatorDaoImpl implements FundIndicatorDao {

	private FundBasicInfoDao fundBasicInfoDao;
	private FundIndicatorAverageRepository fundIndicatorAverageRepository;
	private FundIndicatorRepository fundIndicatorRepository;
	private BatchUtils batchUtils;

	@Autowired
	void setFundBasicInfoDao(FundBasicInfoDao fundBasicInfoDao) {
		this.fundBasicInfoDao = fundBasicInfoDao;
	}

	@Autowired
	void setFundIndicatorAverageRepository(
			FundIndicatorAverageRepository fundIndicatorAverageRepository) {
		this.fundIndicatorAverageRepository = fundIndicatorAverageRepository;
	}

	@Autowired
	void setFundIndicatorRepository(FundIndicatorRepository fundIndicatorRepository) {
		this.fundIndicatorRepository = fundIndicatorRepository;
	}

	@Autowired
	void setBatchUtils(BatchUtils batchUtils) {
		this.batchUtils = batchUtils;
	}

	@Override
	public void fetchFundIndicator() {
		String version = "6.4.6";
		List<Object> fund = fundBasicInfoDao.getAllFundInfo();
		RestTemplate restTemplate = new RestTemplate();
		int len = fund.size();

		for (int i = 0; i < len; i++) {
			Object[] row = (Object[]) fund.get(i);
			if (row[0] == null || row[1] == null) {
				continue;
			}
			String code = (String) row[0];
			Integer type = (Integer) row[1];
			if (code == null || type == null) {
				continue;
			}

			String deviceId = RandomStringUtils.randomAlphanumeric(10); //随机生成deviceId
			String url =
					"https://fundmobapi.eastmoney.com/FundMNewApi/FundMNUniqueInfo?product=EFund&appVersion="
							+ version + "&serverVersion=" + version + "&FCODE="
							+ code + "&deviceid=" + deviceId + "&version=" + deviceId
							+ "&userId=uid&cToken=ctoken&MobileKey="
							+ deviceId + "&OSVersion=10&plat=Android&uToken=utoken&passportid=1234567890";
			String result = restTemplate.getForObject(url, String.class);
			JSONObject jsonObject = JSON.parseObject(result).getJSONObject("Datas");

			if (jsonObject == null) {
				continue;
			}

			FundIndicator tmp = fundIndicatorRepository.getFundIndicatorByFundCode(code);
			if (tmp == null) {
				tmp = new FundIndicator();
				tmp.setFundType(type);
				tmp.setFundCode(code);
			}

			FundIndicatorAverage tmp1 = fundIndicatorAverageRepository.getFundIndicatorAverageByFundType(
					type);
			if (tmp1 == null) {
				tmp1 = new FundIndicatorAverage();
				tmp1.setFundType(type);
			}

			//正则表达式, 分别限定int double
			Pattern pattern = Pattern.compile("^[0-9]*$");
			Pattern pattern1 = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");

			String str;
			str = jsonObject.get("SHARP1").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSHARP1(Double.valueOf(str));
			}

			str = jsonObject.get("SHARP3").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSHARP3(Double.valueOf(str));
			}

			str = jsonObject.get("SHARP5").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSHARP5(Double.valueOf(str));
			}

			str = jsonObject.get("SHARP_1NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSHARP_1NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("SHARP_3NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSHARP_3NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("SHARP_5NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSHARP_5NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("MAXRETRA1").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setMAXRETRA1(Double.valueOf(str));
			}

			str = jsonObject.get("MAXRETRA3").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setMAXRETRA3(Double.valueOf(str));
			}

			str = jsonObject.get("MAXRETRA5").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setMAXRETRA5(Double.valueOf(str));
			}

			str = jsonObject.get("MAXRETRA_1NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setMAXRETRA_1NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("MAXRETRA_3NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setMAXRETRA_3NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("MAXRETRA_5NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setMAXRETRA_5NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("STDDEV1").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSTDDEV1(Double.valueOf(str));
			}

			str = jsonObject.get("STDDEV3").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSTDDEV3(Double.valueOf(str));
			}

			str = jsonObject.get("STDDEV5").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setSTDDEV5(Double.valueOf(str));
			}

			str = jsonObject.get("STDDEV_1NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSTDDEV_1NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("STDDEV_3NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSTDDEV_3NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("STDDEV_5NRANK").toString();
			if (str != null && str != "--" && pattern.matcher(str).matches()) {
				tmp.setSTDDEV_5NRANK(Integer.parseInt(str));
			}

			str = jsonObject.get("PROFIT_Y").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setPROFIT_Y(Double.valueOf(str));
			}

			str = jsonObject.get("PROFIT_1N").toString();
			if (str != null && str != "--" && pattern1.matcher(str).matches()) {
				tmp.setPROFIT_1N(Double.valueOf(str));
			}

			//由于个数为冷数据，不用每次更新，因此此处注释加快速度  20210829

//      str = jsonObject.get("SHARP_1NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSHARP_1NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("SHARP_3NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSHARP_3NFSC(Integer.parseInt(str));
//      }

//      str = jsonObject.get("SHARP_5NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSHARP_5NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("MAXRETRA_1NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setMAXRETRA_1NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("MAXRETRA_3NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setMAXRETRA_3NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("MAXRETRA_5NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setMAXRETRA_5NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("STDDEV_1NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSTDDEV_1NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("STDDEV_3NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSTDDEV_3NFSC(Integer.parseInt(str));
//      }
//
//      str = jsonObject.get("STDDEV_5NFSC").toString();
//      if (str != null && str != "--" && pattern.matcher(str).matches()) {
//        tmp1.setSTDDEV_5NFSC(Integer.parseInt(str));
//      }
//      fundIndicatorAverageRepository.save(tmp1);

			fundIndicatorRepository.save(tmp);
			if (i % 1000 == 0) {
				System.out.println(i);
			}
		}
	}

	@Override
	//参考:https://www.cnblogs.com/mr-wuxiansheng/p/7768491.html
	public void processData() {
		//计算每类基金的平均数据
		List<FundIndicatorAverage> average = fundIndicatorAverageRepository.getAll();
		int len = average.size();
//    DecimalFormat df = new DecimalFormat("#.0000");  //保留四位小数
		for (int i = 0; i < len; i++) {
			FundIndicatorAverage tmp = average.get(i);
			int type = tmp.getFundType();
			tmp.setSHARP1_AVERAGE(fundIndicatorRepository.getAverageSHARP1(type));
			tmp.setSHARP3_AVERAGE(fundIndicatorRepository.getAverageSHARP3(type));
			tmp.setSHARP5_AVERAGE(fundIndicatorRepository.getAverageSHARP5(type));
			tmp.setMAXRETRA1_AVERAGE(fundIndicatorRepository.getAverageMAXRETRA1(type));
			tmp.setMAXRETRA3_AVERAGE(fundIndicatorRepository.getAverageMAXRETRA3(type));
			tmp.setMAXRETRA5_AVERAGE(fundIndicatorRepository.getAverageMAXRETRA5(type));
			tmp.setSTDDEV1_AVERAGE(fundIndicatorRepository.getAverageSTDDEV1(type));
			tmp.setSTDDEV3_AVERAGE(fundIndicatorRepository.getAverageSTDDEV3(type));
			tmp.setSTDDEV5_AVERAGE(fundIndicatorRepository.getAverageSTDDEV5(type));
			tmp.setPROFIT_Y_AVERAGE(fundIndicatorRepository.getAveragePROFIT_Y(type));
			tmp.setPROFIT_1N_AVERAGE(fundIndicatorRepository.getAveragePROFIT_1N(type));
			tmp.setPROFIT_1NFSC(fundIndicatorRepository.getTypeNumber(type));
			tmp.setPROFIT_YFSC(fundIndicatorRepository.getTypeNumber(type));
			fundIndicatorAverageRepository.save(tmp);
		}

		//计算profit_y, profit_1n的排名,写入数据库
		List<Map<String, Object>> result = fundIndicatorRepository.getPROFIT_YRank();
		List<Map<String, Object>> result1 = fundIndicatorRepository.getPROFIT_1NRank();
		List<FundIndicator> data = fundIndicatorRepository.getAll();
		Collections.sort(result, (o1, o2) -> {
			String code1 = (String) o1.get("fund_code");
			String code2 = (String) o2.get("fund_code");
			return code1.compareTo(code2);
		});

		Collections.sort(result1, (o1, o2) -> {
			String code1 = (String) o1.get("fund_code");
			String code2 = (String) o2.get("fund_code");
			return code1.compareTo(code2);
		});

		len = data.size();
		Map<String, Object> tmp1;
		for (int i = 0; i < len; i++) {
			tmp1 = result.get(i);
			if (tmp1.containsKey("tmp_rank")) {
				data.get(i).setPROFIT_YRANK((int) Double.parseDouble((tmp1.get("tmp_rank").toString())));
			}

			tmp1 = result1.get(i);
			if (tmp1.containsKey("tmp_rank")) {
				data.get(i).setPROFIT_1NRANK((int) Double.parseDouble((tmp1.get("tmp_rank").toString())));
			}
		}
		batchUtils.batchUpdate(data);
	}

	@Override
	public JSONArray getFundIndicatorByCode(String fundCode) {
		JSONArray jsonArray = new JSONArray();
		FundIndicator result = fundIndicatorRepository.getFundIndicatorByFundCode(fundCode);
		if (result != null) {
			jsonArray.add(result);
			Integer type = result.getFundType();
			if (type != null) {
				FundIndicatorAverage result1 = fundIndicatorAverageRepository.getFundIndicatorAverageByFundType(
						type);
				jsonArray.add(result1);
			}
		}
		return jsonArray;
	}

	@Override
	public List<Integer> getFundIndicatorNumber(String fundCode) {
		FundIndicator tmp = fundIndicatorRepository.getFundIndicatorByFundCode(fundCode);
		if (tmp == null) {
			return null;
		}
		FundIndicatorAverage tmp1 = fundIndicatorAverageRepository.getFundIndicatorAverageByFundType(
				tmp.getFundType());
		if (tmp1 == null) {
			return null;
		}
		List<Integer> result = new LinkedList<>();
		Integer tmp2;

		if (tmp1.getPROFIT_YFSC() != null && tmp.getPROFIT_YRANK() != null) {
			tmp2 = Math.min(100, 100 - tmp.getPROFIT_YRANK() * 100 / tmp1.getPROFIT_YFSC());
		} else {
			tmp2 = 50;
		}
		result.add(tmp2);

		if (tmp1.getMAXRETRA_1NFSC() != null && tmp.getMAXRETRA_1NRANK() != null) {
			tmp2 = Math.min(100, 100 - tmp.getMAXRETRA_1NRANK() * 100 / tmp1.getMAXRETRA_1NFSC());
		} else {
			tmp2 = 50;
		}
		result.add(tmp2);

		if (tmp1.getSHARP_1NFSC() != null && tmp.getSHARP_1NRANK() != null) {
			tmp2 = Math.min(100, 100 - tmp.getSHARP_1NRANK() * 100 / tmp1.getSHARP_1NFSC());
		} else {
			tmp2 = 50;
		}
		result.add(tmp2);
		return result;
	}

	@Override
	public List<HomeFundIndicator> getHomeFundIndicator(Integer sharp, Integer maxRet, Integer stdDev, Integer profit,
														Integer page, Integer size) {
		return fundIndicatorRepository.getHomeFundIndicator(sharp, maxRet, stdDev, profit, PageRequest.of(page, size));
	}
}
