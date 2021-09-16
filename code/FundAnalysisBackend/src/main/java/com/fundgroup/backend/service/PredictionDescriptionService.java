package com.fundgroup.backend.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.PredictionDescription;
import java.util.List;

public interface PredictionDescriptionService {

  void createDescription(JSONObject basicInfo, JSONArray rules);

  List<PredictionDescription> getAll();

  void deleteDescription(Integer id);

  void updateAllPredictionDescription();

  String getNameById(Integer id);
}
