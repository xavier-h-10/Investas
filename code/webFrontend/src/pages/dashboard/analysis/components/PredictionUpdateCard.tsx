import { Bar } from '@ant-design/charts';
import type { DataItem } from '@/pages/dashboard/analysis/data';
import moment from 'moment';

const PredictionUpdateCard = ({ data }: { data: DataItem[]; loading: boolean }) => {
  const tableData: { localDate: string; num: string | number | number[] | null | undefined }[] = [];
  for (let i = 0; i < data.length; i += 1) {
    tableData.push({
      localDate: moment(data[i][0]).format('YYYY-MM-DD'),
      num: data[i][1] === undefined || data[i][1] === null ? '0' : data[i][1],
    });
  }

  const config = {
    data: tableData,
    xField: 'num',
    yField: 'localDate',
    xAxis: 'num',
    yAxis: 'localDate',
    seriesField: 'localDate',
    colorField: 'localDate',
    color: ['#d62728', '#2ca02c', '#25a29d'],
    title: {
      visible: true,
      text: '基金预测更新日期',
      style: {
        fontSize: 14,
      },
    },
    legend: { position: 'top-left' },
  };

  // @ts-ignore
  return <Bar {...config} />;
};

export default PredictionUpdateCard;
