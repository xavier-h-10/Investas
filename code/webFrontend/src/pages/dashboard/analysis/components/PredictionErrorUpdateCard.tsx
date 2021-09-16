import { Bar } from '@ant-design/charts';
import moment from 'moment';
import { useRequest } from '@@/plugin-request/request';
import {  queryPredictionErrorData } from '@/pages/dashboard/analysis/service';

const PredictionErrorUpdateCard = () => {

  const { data, loading } = useRequest(() => {
    return queryPredictionErrorData();
  });

  const tableData: { localDate: string; num: string | number | number[] | null | undefined }[] = [];
  for (let i = 0; i < (data?.src || []).length; i += 1) {
    tableData.push({
      localDate: moment(data?.src[i].localDate).format('YYYY-MM-DD'),
      num: data?.src[i].num === undefined ? 0 : data?.src[i].num,
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
    color: ['#d62728', '#2ca02c', '#25a29d', '#148DF8'],
    title: {
      visible: true,
      text: '基金预测误差更新日期',
      style: {
        fontSize: 14,
      },
    },
    legend: { position: 'top-left' },
    loading,
  };

  // @ts-ignore
  return <Bar {...config} />;
};

export default PredictionErrorUpdateCard;
