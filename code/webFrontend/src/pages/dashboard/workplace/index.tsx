import type { FC } from 'react';
import { Card, Col, Row, Button, message } from 'antd';

import { useRequest } from 'umi';
import styles from './style.less';
import {
  updatePredictionError,
  updatePrediction,
  updateCompetitionData,
  fillFundData,
  queryWorkPlaceData,
  deleteFromEstimateData,
} from './service';
import TypeReplace from '@/pages/dashboard/workplace/components/TypeReplace';
import moment from 'moment';
import { PlayCircleOutlined } from '@ant-design/icons';
import ProForm, { ModalForm, ProFormSelect } from '@ant-design/pro-form';

const Workplace: FC = () => {
  const { data } = useRequest(() => {
    return queryWorkPlaceData();
  });

  const clickUpdatePrediction = () => {
    const time: string = moment().format('MMMM Do YYYY, h:mm:ss a');
    updatePrediction().then((r) => {
      message.success(`From${time} begins, Task Finished.`);
      message.success(r);
    });
  };

  const clickUpdatePredictionError = () => {
    const time: string = moment().format('MMMM Do YYYY, h:mm:ss a');
    updatePredictionError().then((r) => {
      message.success(`From${time} begins, Task Finished.`);
      message.success(r);
    });
  };

  const clickUpdateCompetitionData = () => {
    const time: string = moment().format('MMMM Do YYYY, h:mm:ss a');
    updateCompetitionData().then((r) => {
      message.success(`From${time} begins, Task Finished.`);
      message.success(r);
    });
  };

  const clickFillFundData = () => {
    const time: string = moment().format('MMMM Do YYYY, h:mm:ss a');
    fillFundData().then((r) => {
      message.success(`From${time} begins, Task Finished.`);
      message.success(r);
    });
  };

  const deleteFundEstimate = async (values: any) => {
    console.log(values.timeType);
    deleteFromEstimateData(values.timeType).then((r) => {
      console.log(r);
      if (r.status === 100) {
        message.success('删除成功');
      } else {
        message.error('出现错误');
      }
    });
  };

  const renderModalForm = () => {
    return (
      <ModalForm
        title="选择需要删除的数据"
        trigger={
          <Button danger icon={<PlayCircleOutlined />}>
            立即调用
          </Button>
        }
        onFinish={deleteFundEstimate}
      >
        <ProForm.Group>
          <ProFormSelect
            options={[
              {
                value: 11,
                label: '删除直到一天前',
              },
              {
                value: 12,
                label: '删除直到一周前',
              },
              {
                value: 13,
                label: '删除直到一月前',
              },
            ]}
            width="lg"
            name="timeType"
            label="删除数据范围"
          />
        </ProForm.Group>
      </ModalForm>
    );
  };

  return (
    <Row gutter={24}>
      <Col xl={24} lg={24} md={24} sm={24} xs={24}>
        <TypeReplace modelData={data?.modelTypeData || []} />
      </Col>
      <Col xl={24} lg={24} md={24} sm={24} xs={24}>
        <Card
          className={styles.projectList}
          style={{ marginBottom: 24 }}
          title="Restricted Area 危险操作区域"
          bordered={false}
          extra={
            <div style={{ color: 'red' }}>
              执行这些操作，可能导致服务器负载提高，用户视图信息改变等。
            </div>
          }
          bodyStyle={{ padding: 0 }}
        >
          {
            <>
              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title="更新预测数据"
                    description="此操作会即刻调用服务器更新超过13000只基金的全部预测数据，计算量极其庞
                      大。会占用大量资源约2小时30分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button onClick={clickUpdatePrediction} danger icon={<PlayCircleOutlined />}>
                      立即调用
                    </Button>
                  </div>
                </Card>
              </Card.Grid>

              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title="更新预测误差"
                    description="此操作会即刻调用服务器更新超过13000只基金的最新预测误差并写入数据库。
                      会占用少量资源约15分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button
                      danger
                      onClick={clickUpdatePredictionError}
                      icon={<PlayCircleOutlined />}
                    >
                      立即调用
                    </Button>
                  </div>
                </Card>
              </Card.Grid>

              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title="更新比赛信息"
                    description="此操作会即刻调用服务器更新所有比赛的用户最新收益和全球排名。
                      会占用中量资源约10分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button
                      danger
                      onClick={clickUpdateCompetitionData}
                      icon={<PlayCircleOutlined />}
                    >
                      立即调用
                    </Button>
                  </div>
                </Card>
              </Card.Grid>

              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title="补充基金缺省信息"
                    description="此操作会即刻调用服务器更新超过13000只基金的历史缺失数据。
                      会占用少量资源约60分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button danger onClick={clickFillFundData} icon={<PlayCircleOutlined />}>
                      立即调用
                    </Button>
                  </div>
                </Card>
              </Card.Grid>

              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title="删除基金估值信息"
                    description="此操作会即刻删除数据库中相应的基金估值"
                  />
                  <br />
                  <div className={styles.projectItemContent}>{renderModalForm()}</div>
                </Card>
              </Card.Grid>
            </>
          }
        </Card>
      </Col>
    </Row>
  );
};

export default Workplace;
