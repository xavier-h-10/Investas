package com.fundgroup.backend.serviceTest;

import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.SEQ_LEN;

import com.fundgroup.backend.dao.FundPredictionDao;
import com.fundgroup.backend.service.TensorflowService;
import com.fundgroup.backend.utils.tensorflowUtils.TensorflowUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class TensorflowServiceTest {
  @Mock
  FundPredictionDao fundPredictionDao;

  @Mock
  TensorflowService tensorflowService;

  @Test
  public void test(){
    TensorflowUtils tensorflowUtils = new TensorflowUtils();
    String fund_code = "000166";
    Integer fund_type = 1;

    tensorflowUtils.startDockerService(fund_code, fund_type);
    List<Double> inputData = new ArrayList<>();
    for(Double i = 0.0; i < 30.0; ++i){
      inputData.add(i);
    }

    if(inputData.size() < SEQ_LEN){
      fundPredictionDao.updatePredictedNAV(fund_type, fund_code, -1.0,
          -1.0, -1.0, -1.0);
    }
    else {
      for (int i = 0; i < 3; ++i) {
        Double ret = inputData.get(SEQ_LEN - 1);

        try {
          tensorflowUtils.execPrediction(inputData);
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println(inputData.size());
      }

      fundPredictionDao.updatePredictedNAV(fund_type, fund_code, inputData.get(SEQ_LEN - 4),
          inputData.get(SEQ_LEN - 3), inputData.get(SEQ_LEN - 2), inputData.get(SEQ_LEN - 1));
    }
  }

  @Test
  void testService(){
    return;
  }
}
