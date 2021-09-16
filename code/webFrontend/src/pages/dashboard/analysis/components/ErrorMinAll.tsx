import { useRequest } from '@@/plugin-request/request';
import {
  queryPredictionWorst,
} from '@/pages/dashboard/analysis/service';
import ProCard from '@ant-design/pro-card';
import ProTable, { ProColumns } from '@ant-design/pro-table';
import { PredictionErrorType } from '@/pages/dashboard/analysis/data';
import { Tooltip } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';

const ErrorMinAll = () => {

  const { data, loading } = useRequest(() => {
    return queryPredictionWorst();
  });

  console.log(data);

  const columns: ProColumns<PredictionErrorType>[] = [
    {
      title: '基金代码',
      dataIndex: 'fundCode',
      render: (_) => <div style={{color: 'pink'}}>{_}</div>,
    },
    {
      title: '基金类型',
      dataIndex: 'fundType',
      valueEnum: {
        0: '债券型-混合债',
        1: '混合型-偏股',
        2: '指数型-股票',
        3: '混合型-灵活',
        4: 'QDII',
        5: '债券型-长债',
        7: '股票型',
        8: '混合型-偏债',
        9: '混合型-平衡',
        10: '债券型-中短债',
        11: '商品（不含QDII）',
        12: '债券型-可转债',
        13: 'REITs',
        14: '商品（不含QDII）',
      },
    },
    {
      title: (
        <>
          MSE
          <Tooltip placement="top" title="计算公式：| 实际NAV - 昨日预测NAV | ^ 2">
            <QuestionCircleOutlined style={{ marginLeft: 4 }} />
          </Tooltip>
        </>
      ),
      dataIndex: 'todayMSE',
      align: 'right',
      sorter: (a, b) => a.todayMSE - b.todayMSE,
    },
    {
      title: (
        <>
          MAE * 1000
          <Tooltip placement="top" title="计算公式：| 实际NAV - 昨日预测NAV | * 1000">
            <QuestionCircleOutlined style={{ marginLeft: 4 }} />
          </Tooltip>
        </>
      ),
      dataIndex: 'todayMAE',
      align: 'right',
      render: (_) => <div>{_}</div>,
      sorter: (a, b) => a.todayMAE - b.todayMAE,
    },
    {
      title: (
        <>
          RMSE
          <Tooltip placement="top" title="计算公式：sqrt (| 实际NAV - 昨日预测NAV | ^ 2">
            <QuestionCircleOutlined style={{ marginLeft: 4 }} />
          </Tooltip>
        </>
      ),
      dataIndex: 'todayRMSE',
      align: 'right',
      sorter: (a, b) => a.todayRMSE - b.todayRMSE,
    },
    {
      title: (
        <>
          绝对差距
          <Tooltip placement="top" title="计算公式：| 实际NAV - 昨日预测NAV | / 实际NAV">
            <QuestionCircleOutlined style={{ marginLeft: 4 }} />
          </Tooltip>
        </>
      ),
      dataIndex: 'todayAbsDelta',
      align: 'right',
      render: (_) => <div>{_}‰</div>,
      sorter: (a, b) => a.todayAbsDelta - b.todayAbsDelta,
    },
  ];
  return (
    <ProCard title="预测效果最差"
             loading={loading}
             headerBordered
             tooltip='预测效果最好的几只基金。'
    >

      <ProTable <PredictionErrorType>
        columns={columns}
        dataSource={data?.src}

        rowKey="key"
        pagination={{
          showQuickJumper: true,
        }}
        search={false}
        dateFormatter="string"
        headerTitle="原始数据根据绝对误差排序"
      />

    </ProCard>
  )
}

export default ErrorMinAll;
