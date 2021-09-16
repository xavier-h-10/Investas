package com.fundgroup.backend.serviceTest;

import com.fundgroup.backend.service.FundPredictionService;
import org.apache.spark.sql.catalyst.expressions.objects.AssertNotNull;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FundPredictionServiceTest {

  @Autowired
  FundPredictionService fundPredictionService;

  @Test
  public void TestFunction(){

  }
}
