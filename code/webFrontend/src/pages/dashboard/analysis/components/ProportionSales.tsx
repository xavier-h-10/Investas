import { Card,  Typography } from 'antd';
import numeral from 'numeral';
import type { RadioChangeEvent } from 'antd/es/radio';
import { Donut } from '@ant-design/charts';
import type { DonutConfig } from '@ant-design/charts/es/donut';
import React from 'react';
import type { DataItem } from '../data.d';
import styles from '../style.less';
import { fundTypeTransHelper } from '@/utils/fundTypeTransHelper';

const { Text } = Typography;

const ProportionSales = ({
  dropdownGroup,
  salesType,
  loading,
  salesPieData,
  handleChangeSalesType,
}: {
  loading: boolean;
  dropdownGroup: React.ReactNode;
  salesType: 'all' | 'online' | 'stores';
  salesPieData: DataItem[];
  handleChangeSalesType?: (e: RadioChangeEvent) => void;

}) =>{
  const data: any = [];
  for(let i: number = 0; i < salesPieData.length; i += 1){
    data.push({
      x: fundTypeTransHelper(salesPieData[i][0]),
      y: salesPieData[i][1]
    })
  }
  return  (
  <Card
    loading={loading}
    className={styles.salesCard}
    bordered={false}
    title="数据库基金总量"
    style={{
      height: '100%',
    }}
  >
    <div>
      <Text>基金数量</Text>
      <Donut
        forceFit
        height={340}
        radius={0.8}
        angleField="y"
        colorField="x"
        data={data}
        legend={{
          visible: false,
        }}
        label={{
          visible: true,
          type: 'spider',
          formatter: (text, item) => {
            // eslint-disable-next-line no-underscore-dangle
            return `${item._origin.x}: ${numeral(item._origin.y).format('0,0')}`;
          },
        }}
        statistic={
          {
            totalLabel: '基金数量',
          } as DonutConfig['statistic']
        }
      />
    </div>
  </Card>
);}

export default ProportionSales;
