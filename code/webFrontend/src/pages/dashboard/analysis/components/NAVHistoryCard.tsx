import { Card, Col,  Row, Tabs } from 'antd';
import type { RangePickerProps } from 'antd/es/date-picker/generatePicker';
import type moment from 'moment';
import { Column } from '@ant-design/charts';
import styles from '../style.less';

type RangePickerValue = RangePickerProps<moment.Moment>['value'];
export type TimeType = 'today' | 'week' | 'month' | 'year';

const { TabPane } = Tabs;


const NAVHistoryCard = ({
  rangePickerValue,
  salesData,
  isActive,
  handleRangePickerChange,
  loading,
  selectDate,
}: {
  rangePickerValue: RangePickerValue;
  isActive: (key: TimeType) => string;
  salesData: {localDate: string, num: number}[];
  loading: boolean;
  handleRangePickerChange: (dates: RangePickerValue, dateStrings: [string, string]) => void;
  selectDate: (key: TimeType) => void;
}) => {

  for(let i = 0; i < salesData.length; i += 1){
    // eslint-disable-next-line no-param-reassign
    salesData[i].localDate = (salesData[i].localDate.substr(0, 10));
  }
  return(
    <Card loading={loading} bordered={false} bodyStyle={{ padding: 0 }}>
      <div className={styles.salesCard}>
        <Tabs
          tabBarExtraContent={
            <div className={styles.salesExtraWrap}>
              如果数量与往日发生重大变化，请联系工程师。
            </div>
          }
          size='large'
          tabBarStyle={{ marginBottom: 24 }}
        >
          <TabPane tab='NAV更新情况' key='sales'>
            <Row>
              <Col xl={24} lg={24} md={24} sm={24} xs={24}>
                <div className={styles.salesBar}>
                  <Column
                    height={300}
                    forceFit
                    data={salesData as any}
                    xField='localDate'
                    yField='num'
                    xAxis={{
                      visible: true,
                      title: {
                        visible: false,
                      },
                    }}
                    yAxis={{
                      visible: true,
                      title: {
                        visible: false,
                      },
                    }}
                    title={{
                      visible: true,
                      text: '每日更新NAV条数',
                      style: {
                        fontSize: 14,
                      },
                    }}
                    meta={{
                      y: {
                        alias: '条',
                      },
                    }}
                  />
                </div>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </div>
    </Card>
  );
}

export default NAVHistoryCard;
