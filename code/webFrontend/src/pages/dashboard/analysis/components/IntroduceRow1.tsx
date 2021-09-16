import { InfoCircleOutlined } from '@ant-design/icons';
import { Col, Row, Tooltip } from 'antd';

import { ChartCard, Field } from './Charts';
import type { DataItem } from '../data.d';

import Text from 'antd/es/typography/Text';

import moment from 'moment';

const topColResponsiveProps = {
  xs: 24,
  sm: 12,
  md: 12,
  lg: 12,
  xl: 6,
  style: { marginBottom: 24 },
};

const nowDate: string = moment().locale('zh-cn').format('YYYY-MM-DD');


const IntroduceRow1 = ({ loading }: { loading: boolean; visitData: DataItem[] }) => (
  <Row gutter={24}>
    <Col {...topColResponsiveProps}>
      <ChartCard
        bordered={false}
        title="北京时间"
        action={
          <Tooltip title="控制台标准时间">
            <InfoCircleOutlined />
          </Tooltip>
        }
        loading={loading}
        total={() =>
          <>
            <Text>{nowDate}</Text>
          </>
          }
        footer={<Field label="Tip:" value={`请根据时间监控系统运作`} />}
        contentHeight={60}
       />
    </Col>
  </Row>
);

export default IntroduceRow1;
