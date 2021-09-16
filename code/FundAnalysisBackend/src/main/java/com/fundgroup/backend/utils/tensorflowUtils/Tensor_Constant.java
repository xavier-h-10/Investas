package com.fundgroup.backend.utils.tensorflowUtils;

import org.tensorflow.framework.DataType;

// run tf_serving's necessary paras, you should use saved_model o confirm these paras
// just refer to "tfs_config.md" and "link_java_tfs.md" to config
public class Tensor_Constant {
    public static final int SEQ_LEN = 30;
    public static final int PORT = 9500;
    public static final String HOST = "127.0.0.1";
    public static final String MODEL_NAME = "my_model";
    public static final String SIGNATURE_NAME = "serving_default";

    public static final DataType INPUT_TYPE = DataType.DT_DOUBLE;
    public static final int ELE_NUM = 1;

    public static final String INPUT_NAME = "input";
    public static final String OUTPUT_NAME = "output";
}
