from datetime import time
import os
import csv
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error, mean_absolute_error
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout, Bidirectional
from tensorflow.keras.callbacks import ModelCheckpoint, TensorBoard
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from collections import deque
import time
import numpy as np
import pandas as pd
import math


def load_data(curr_set, predict_x_len, need_reshuffle):
    x_ret = []
    y_ret = []

    for i in range(predict_x_len, len(curr_set)):
        x_ret.append(curr_set[i - predict_x_len:i, 0])
        y_ret.append(curr_set[i, 0])

    if need_reshuffle:
        np.random.seed(11)
        np.random.shuffle(x_ret)
        np.random.seed(11)
        np.random.shuffle(y_ret)

    x_ret, y_ret = np.array(x_ret), np.array(y_ret)

    # 适合LSTM的输入格式，即升维1
    x_ret = np.reshape(x_ret, (x_ret.shape[0], predict_x_len, 1))

    return x_ret, y_ret


# 抽取可调参数，创建了一个函数来制造我们的模型方便进行调参
def create_model(sequence_length, n_features, units=256, cell=LSTM, n_layers=2, dropout=0.3,
                 loss="mean_absolute_error", optimizer="rmsprop", bidirectional=False,
                 output_div=1):
    model = Sequential()
    for i in range(n_layers):
        if i == 0:
            # first layer
            if bidirectional:
                model.add(Bidirectional(cell(units, return_sequences=True),
                                        batch_input_shape=(None, sequence_length, n_features)))
            else:
                model.add(cell(units, return_sequences=True,
                               batch_input_shape=(None, sequence_length, n_features)))
        elif i == n_layers - 1:
            # last layer
            if bidirectional:
                model.add(Bidirectional(cell(units, return_sequences=False)))
            else:
                model.add(cell(units, return_sequences=False))
        else:
            # hidden layers
            if bidirectional:
                model.add(Bidirectional(cell(units, return_sequences=True)))
            else:
                model.add(cell(units, return_sequences=True))

        # add dropout after each layer
        model.add(Dropout(dropout))

    model.add(Dense(output_div, activation="linear"))
    model.compile(loss=loss, metrics=["mean_absolute_error"], optimizer=optimizer)
    return model


def predict(path,
            predict_x_len=40,
            output_div=1,
            N_LAYERS=2,
            CELL=LSTM,
            UNITS=256,
            DROPOUT=0.4,
            BIDIRECTIONAL=False,
            LOSS="huber_loss",
            OPTIMIZER="adam",
            BATCH_SIZE=64,
            EPOCHS=10):
    fund = pd.read_csv(path)

    # [2723, 7]
    # print(fund.shape)

    # training: validation: test = 8: 1: 1
    fund_data_len = fund.shape[0]
    training_set_len = round(fund_data_len * 0.8)
    validation_set_len = round(fund_data_len * 0.1)

    # only open price
    training_set = fund.iloc[0:training_set_len, 2:3].values
    validation_set = fund.iloc[training_set_len:(training_set_len + validation_set_len), 2:3].values
    test_set = fund.iloc[(training_set_len + validation_set_len):, 2:3].values

    # plot and see origin
    # plt.plot(fund.iloc[:, 0:1], fund.iloc[:, 2:3], color='red', label='open')
    # plt.title('fund price')
    # plt.ylabel('price')
    # plt.show()

    # 验证信息
    # fund.info()
    # fund.head()
    # maybe: 如果多参数训练，那要把剩下的全部去掉然后做数据处理
    # fund.drop(['volume'], 1, inplace=True)
    # fund.drop(['code'], 1, inplace=True)
    # fund.drop(['date'], 1, inplace=True)

    # norm def
    sc = MinMaxScaler(feature_range=(0, 1))

    # ser normalization paras
    training_set = sc.fit_transform(training_set)

    # use this paras to norm v/t sets
    validation_set = sc.transform(validation_set)
    test_set = sc.transform(test_set)

    x_train, y_train = load_data(training_set, predict_x_len, True)
    x_valid, y_valid = load_data(validation_set, predict_x_len, False)
    x_test, y_test = load_data(test_set, predict_x_len, False)

    # 验证信息
    # print('x_train.shape = ', x_train.shape)
    # print('y_train.shape = ', y_train.shape)
    # print('x_valid.shape = ', x_valid.shape)
    # print('y_valid.shape = ', y_valid.shape)
    # print('x_test.shape = ', x_test.shape)
    # print('y_test.shape = ', y_test.shape)

    # 创建保存结果的文件夹, result 保存断点继续训练的参数表
    # logs保存tensor board 记录的表
    # pics保存测试集的结果
    if not os.path.isdir("results"):
        os.mkdir("results")
    if not os.path.isdir("logs"):
        os.mkdir("logs")
    if not os.path.isdir("img"):
        os.mkdir("img")
    if not os.path.isdir("paras"):
        os.mkdir("paras")
    if not os.path.isdir("img/test"):
        os.mkdir("img/test")
    if not os.path.isdir("img/loss"):
        os.mkdir("img/loss")
    if not os.path.isdir("csv"):
        os.mkdir("csv")
        with open("/csv/summary.csv", "w") as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(["seq_length", "n_feature", "unit", "cell",
                             "n_layers", "dropout",
                             "loss", "optimizer", "output_div", "bidirectional",
                             "mse", "r-mse", "mae"])

    # 创建可变模型，参数说明如下：
    model = create_model(sequence_length=predict_x_len,  # 多少天为一组
                         n_features=1,  # 用到的参数个数
                         units=UNITS,  # 多少个units
                         cell=CELL,  # 使用什么单元 RNN / LSTM / GRU
                         n_layers=N_LAYERS,  # 做几层 RNN OR LSTM OR GRU
                         dropout=DROPOUT,  # 遗忘率
                         loss=LOSS,  # 损失函数选择
                         optimizer=OPTIMIZER,  # 优化器选择
                         output_div=output_div,  # 输出层的维度，决定softmax的体积
                         bidirectional=BIDIRECTIONAL)  # 要不要做双向的

    # 损失函数用均方误差
    # 没有准确率，metrics = null
    model.compile(optimizer=tf.keras.optimizers.Adam(0.001),
                  loss='mean_squared_error')

    curr_time = time.strftime("%m-%d-%H-%M")

    model_name = f"loss={LOSS}-op={OPTIMIZER}" \
                 f"-seq_len={predict_x_len}-layers={N_LAYERS}-units={UNITS}-" \
                 f"dropout={DROPOUT}-bi={BIDIRECTIONAL}-epo={EPOCHS}-" \
                 f"batch={BATCH_SIZE}-time={curr_time}"

    # model_name.encode(encoding="utf-8", errors="ignore").decode(encoding="utf-8", errors="ignore")

    checkpoint_save_path = os.path.join("results", model_name)
    visible_paras_save_path = os.path.join("paras", model_name + ".txt")

    loss_save_path = os.path.join("img", "loss", model_name) + ".png"
    test_save_path = os.path.join("img", "test", model_name) + ".png"
    # print(loss_save_path, test_save_path, visible_paras_save_path)

    tensorboard = TensorBoard(log_dir=os.path.join("logs", model_name))

    if os.path.exists(checkpoint_save_path + '.cp'):
        print('Loading past model')
        model.load_weights(checkpoint_save_path)

    # save best only = 1
    cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=checkpoint_save_path,
                                                     save_weights_only=True,
                                                     save_best_only=True,
                                                     monitor='val_loss',
                                                     verbose=1)

    history = model.fit(x_train, y_train, batch_size=BATCH_SIZE,
                        epochs=EPOCHS,
                        validation_data=(x_valid, y_valid),
                        validation_freq=1,
                        callbacks=[cp_callback, tensorboard],
                        verbose=1)

    model.summary()

    loss = history.history['loss']
    val_loss = history.history['val_loss']

    plt.plot(loss, label='Training Loss')
    plt.plot(val_loss, label='Validation Loss')
    plt.xlabel("ep times")
    plt.title('Loss pics')
    plt.legend()
    plt.savefig(loss_save_path)
    plt.cla()
    plt.clf()

    file = open(visible_paras_save_path, 'w')
    # write paras down
    for v in model.trainable_variables:
        file.write(str(v.name) + '\n')
        file.write(str(v.shape) + '\n')
        file.write(str(v.numpy()) + '\n')
    file.close()

    # predict test set
    # 测试集输入模型进行预测
    predicted_fund_price = model.predict(x_test)
    # 预测数据反归一化
    predicted_fund_price = sc.inverse_transform(predicted_fund_price)
    # 真实数据反归一化
    real_fund_price = sc.inverse_transform(test_set[predict_x_len:])

    plt.plot(real_fund_price, color='red', label='fund Price')
    plt.plot(predicted_fund_price, color='blue', label='Predicted fund Price')
    plt.title('fund Price Prediction')
    plt.xlabel('Time')
    plt.ylabel('fund Price')
    plt.legend()
    plt.savefig(test_save_path)

    # 量化记录误差
    # calculate MSE AND R_MSE AND MAE
    mse = mean_squared_error(predicted_fund_price, real_fund_price)
    rmse = math.sqrt(mean_squared_error(predicted_fund_price, real_fund_price))
    mae = mean_absolute_error(predicted_fund_price, real_fund_price)
    print('MSE: %.6f' % mse)
    print('R-MSE: %.6f' % rmse)
    print('MAE: %.6f' % mae)

    #     write csv
    with open("csv/summary.csv", "w") as csvFile:
        writer = csv.writer(csvFile)
        # writer.writerow(["seq_length", "n_feature", "unit", "cell", "n_layers", "dropout",
        #              "loss", "optimizer", "output_div", "bidirectional",
        #              "mse", "r-mse", "mae"])
        writer.writerow([predict_x_len, 1, UNITS, CELL.__name__, N_LAYERS, DROPOUT,
                         LOSS, OPTIMIZER, output_div, BIDIRECTIONAL,
                         mse, rmse, mae])


#             path, predict_x_len,
#             output_div=1,
#             N_LAYERS=2,
#             CELL=LSTM,
#             UNITS=256,
#             DROPOUT=0.4,
#             BIDIRECTIONAL=False,
#             LOSS="huber_loss",
#             OPTIMIZER="adam",
#             BATCH_SIZE=64,
#             EPOCHS=10):

data = './SH600519.csv'

# for i in range(50, 800, 100):
#     predict(data, EPOCHS=i, BIDIRECTIONAL=True)

for i in range(48, 256, 16):
    predict(data, BATCH_SIZE=i, EPOCHS=60, BIDIRECTIONAL=True)

# for i in range(10, 300, 5):
#     predict(data, BATCH_SIZE=50, EPOCHS=60, predict_x_len=i, BIDIRECTIONAL=True)
#
# for dropout in np.arange(0, 0.5, 0.05):
#     predict(data, BATCH_SIZE=50, EPOCHS=60, DROPOUT=dropout, BIDIRECTIONAL=True)


# predict(data, EPOCHS=1, BIDIRECTIONAL=True)
# for i in [True, False]:
#     predict(data, BIDIRECTIONAL=i)
