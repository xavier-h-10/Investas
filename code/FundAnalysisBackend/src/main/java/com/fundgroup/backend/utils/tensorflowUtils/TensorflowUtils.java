package com.fundgroup.backend.utils.tensorflowUtils;

import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.ELE_NUM;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.HOST;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.INPUT_NAME;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.INPUT_TYPE;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.MODEL_NAME;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.OUTPUT_NAME;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.PORT;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.SEQ_LEN;
import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.SIGNATURE_NAME;

import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundPredictionDao;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundModelRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.PredictionServiceGrpc;

@Component
public class TensorflowUtils {

  @Autowired
  FundBasicInfoRepository fundBasicInfoRepository;

  @Autowired
  FundDailyInfoDao fundDailyInfoDao;

  @Autowired
  FundPredictionDao fundPredictionDao;

  @Autowired
  FundModelRepository fundModelRepository;

  private static final Logger logger = LoggerFactory.getLogger(TensorflowUtils.class);

  //  Usage: input seq_len list of double and return a float
  public List<Double> execPrediction(List<Double> inputData) throws Exception {

//    ---Begin: handling data now---
    logger.info("new prediction begins");
    Double max_val = Collections.max(inputData);
    Double min_val = Collections.min(inputData);
    double delta_val = max_val - min_val;

    if (delta_val != 0) {
      // ---Begin: do some gRPC init settings----
      // create a channel for gRPC
      ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
      PredictionServiceGrpc.PredictionServiceBlockingStub stub = PredictionServiceGrpc
          .newBlockingStub(channel);

      // create a model spec
      Model.ModelSpec.Builder modelSpecBuilder = Model.ModelSpec.newBuilder();
      modelSpecBuilder.setName(MODEL_NAME);
      modelSpecBuilder.setSignatureName(SIGNATURE_NAME);

      Predict.PredictRequest.Builder builder = Predict.PredictRequest.newBuilder();
      builder.setModelSpec(modelSpecBuilder);

      // create the input TensorProto and request
      TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
      tensorProtoBuilder.setDtype(INPUT_TYPE);
      // ---End: do some gRPC init settings----

      for (int i = 0; i < inputData.size(); ++i) {
        inputData.set(i, ((inputData.get(i) - min_val) / delta_val));
      }

      for (Double intDatum : inputData) {
        tensorProtoBuilder.addDoubleVal(intDatum);
      }
      //    ---End: handling data now---

      // build input TensorProto shape
      TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();

      tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
      tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(SEQ_LEN));
      tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(ELE_NUM));
      tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());

      TensorProto tp = tensorProtoBuilder.build();
      builder.putInputs(INPUT_NAME, tp);
      Predict.PredictRequest request = builder.build();

      // get response
      Predict.PredictResponse response = stub.predict(request);
      Double ret = ((Float) ((response.getOutputsOrThrow(OUTPUT_NAME)).getFloatVal(0)))
          .doubleValue();

      inputData.remove(0);
      inputData.add(inputData.size(), ret);

      for (int i = 0; i < inputData.size(); ++i) {
        inputData.set(i, (inputData.get(i) * delta_val) + min_val);
      }

      channel.shutdownNow().awaitTermination(30, TimeUnit.SECONDS);
    }
    return inputData;
  }

  public void startDockerService(String fund_code, Integer fund_type) {
//    input a fund code and return future 3 Days prediction
//    start tensorflow serving

    Runtime run_1 = Runtime.getRuntime();
    String cmd_1 = "docker container kill $(docker container ls -q)";
    try {
      run_1.exec(cmd_1);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String cmd = String.format(
        "docker run --memory=\"4g\" --cpus=\"0.5\" -p 9500:8500 -p 9501:8501 --mount type=bind,source=/mnt/sdc/fund_models/F%s_%s,target=/models/my_model -e MODEL_NAME=my_model -t emacski/tensorflow-serving",
        fund_type, fund_code);

    Runtime run = Runtime.getRuntime();
    Process pr = null;
    try {
      pr = run.exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
