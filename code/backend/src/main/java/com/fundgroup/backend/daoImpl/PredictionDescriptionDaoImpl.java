package com.fundgroup.backend.daoImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.PredictionDescriptionDao;
import com.fundgroup.backend.entity.PredictionDescription;
import com.fundgroup.backend.entity.PredictionDescriptionRule;
import com.fundgroup.backend.repository.PredictionDescriptionRepository;
import com.fundgroup.backend.repository.PredictionDescriptionRuleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class PredictionDescriptionDaoImpl implements PredictionDescriptionDao {

  @Autowired
  PredictionDescriptionRepository predictionDescriptionRepository;

  @Autowired
  PredictionDescriptionRuleRepository predictionDescriptionRuleRepository;

  @Override
  public void createDescription(JSONObject basicInfo, JSONArray rules) {
    String name = basicInfo.getString("name");
    String description = basicInfo.getString("description");
    Integer priority = basicInfo.getInteger("priority");
    List<PredictionDescriptionRule> ruleList = new ArrayList<>();
    for(int i = 0; i < rules.size(); ++i){
      JSONObject jsonObject = rules.getJSONObject(i);
      PredictionDescriptionRule predictionDescriptionRule = new PredictionDescriptionRule();
      predictionDescriptionRule.setRuleValue(jsonObject.getDoubleValue("ruleValue"));
      String type = jsonObject.getString("ruleType");
      if(type.charAt(0) == 'T'){
        type = type.substring(1);
      }
      predictionDescriptionRule.setRuleType(Integer.valueOf(type));
      String ori = jsonObject.getString("orientation");
      if(Objects.equals(ori, "smaller")){
        predictionDescriptionRule.setRuleOrientation(0);
      }else{
        predictionDescriptionRule.setRuleOrientation(1);
      }

      ruleList.add(predictionDescriptionRule);
    }

    PredictionDescription predictionDescription = new PredictionDescription();
    predictionDescription.setRuleList(ruleList);
    predictionDescription.setPriority(priority);
    predictionDescription.setText(description);
    predictionDescription.setName(name);

    predictionDescription = predictionDescriptionRepository.saveAndFlush(predictionDescription);


  }

  @Override
  public List<PredictionDescription> getAll() {
    return predictionDescriptionRepository.findAll();
  }

  @Override
  public void deleteDescription(Integer id) {
    PredictionDescription predictionDescription = predictionDescriptionRepository.getById(id);
    predictionDescription.setPriority(-1);
    predictionDescriptionRepository.saveAndFlush(predictionDescription);
  }

  @Override
  public String getNameById(Integer id) {
    return predictionDescriptionRepository.getById(id).getName();
  }
}
