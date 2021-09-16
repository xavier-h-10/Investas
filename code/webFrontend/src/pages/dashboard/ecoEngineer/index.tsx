import type { FC } from 'react';
import { Button, Card, Col, message, Row } from 'antd';
import styles from './style.less';
import { updatePredictionDescription } from './service';
import RuleMatching from '@/pages/dashboard/ecoEngineer/components/RuleMatching';
import RuleSetSituation from '@/pages/dashboard/ecoEngineer/components/RuleSetSituation';
import { PlayCircleOutlined } from '@ant-design/icons';
import moment from 'moment';


const EcoWorkplace: FC = () => {
  const clickUpdatePredictionDescription = () => {
    const time: string = moment().format('MMMM Do YYYY, h:mm:ss a');
    updatePredictionDescription().then((r) => {
      message.success(`From${time} begins, Task Finished.`);
      message.success(r.message);
    });
  };

  return (
    <Row gutter={24}>
      <Col xl={24} lg={24} md={24} sm={24} xs={24}>
        <RuleMatching />
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
                <Card bodyStyle={{ padding: 0 }} bordered={false} >
                  <Card.Meta
                    title="更新预测解释"
                    description="此操作会即刻调用服务器更新超过13000只基金的预测值解释，随
                      优先级遍历规则集。会占用中量资源约35分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button
                      danger
                      icon={<PlayCircleOutlined />}
                      onClick={clickUpdatePredictionDescription}
                    >
                      立即调用
                    </Button>
                  </div>
                </Card>
              </Card.Grid>

              <Card.Grid className={styles.projectGrid}>
                <Card bodyStyle={{ padding: 0 }} bordered={false} >
                  <Card.Meta
                    title="更新预测误差"
                    description="此操作会即刻调用服务器更新超过13000只基金的最新预测误差并写入数据库。
                      会占用少量资源约15分钟。"
                  />
                  <br />
                  <div className={styles.projectItemContent}>
                    <Button danger icon={<PlayCircleOutlined />}>立即调用</Button>
                  </div>
                </Card>
              </Card.Grid>
            </>
          }
        </Card>
      </Col>

      <Col xl={24} lg={24} md={24} sm={24} xs={24}>
        <Card
          style={{ marginBottom: 24, maxHeight: 11000 }}
          bordered={false}
          title="当前采用不同规则的基金详情"
        >
          <div>
            <RuleSetSituation />
          </div>
        </Card>
      </Col>
    </Row>
  );
};

export default EcoWorkplace;
