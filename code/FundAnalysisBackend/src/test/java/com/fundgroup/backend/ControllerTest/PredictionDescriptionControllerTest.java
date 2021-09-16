package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.controller.PredictionDescriptionController;
import com.fundgroup.backend.entity.PredictionDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PredictionDescriptionControllerTest {
	@Autowired
	PredictionDescriptionController predictionDescriptionController;

	@Test
	public void test() {
		JSONObject params = new JSONObject();

		JSONObject basicInfo = new JSONObject();
		basicInfo.put("description", "dd");
		basicInfo.put("name", "dd");
		basicInfo.put("priority", 10);
		params.put("basicInfo", basicInfo);

		JSONArray rule = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("index", 0);
		jsonObject.put("orientation", "larger");
		jsonObject.put("ruleType", "T2");
		jsonObject.put("ruleValue", "11");
		rule.add(jsonObject);
		params.put("rule", rule);

		System.out.println(basicInfo);
		System.out.println(rule);

		predictionDescriptionController.createDescription(params);

//		predictionDescriptionController.updateDescription();
		predictionDescriptionController.getReportData();

		JSONObject ret = predictionDescriptionController.getAllDescription().getData();
		List<PredictionDescription> predictionDescriptionList =
				JSONObject.toJavaObject(ret.getJSONArray("src"), List.class);
		for (PredictionDescription predictionDescription: predictionDescriptionList) {
			if (!predictionDescription.getName().equals("dd")) continue;
			System.out.println(predictionDescription);
			params.clear();
			params.put("value", predictionDescription.getDescriptionId());
			predictionDescriptionController.deleteDescription(params);
		}
	}
}
