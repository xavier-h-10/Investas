package com.fundgroup.backend.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.PredictionDescription;
import java.util.List;

public interface PredictionDescriptionDao {

  void createDescription(JSONObject basicInfo, JSONArray rules);

  List<PredictionDescription> getAll();

  void deleteDescription(Integer id);

  String getNameById(Integer id);
}
