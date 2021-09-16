import { useEffect, useState } from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import { ProFormField } from '@ant-design/pro-form';
import ProCard from '@ant-design/pro-card';
import { useRequest } from '@@/plugin-request/request';
import { JobType } from '@/pages/dashboard/monitor/data';
import { Badge, message } from 'antd';
import cronstrue from 'cronstrue';
import { deleteJobPipeline, queryJobStatusPipeline } from '@/pages/dashboard/monitor/service';

const JobMonitorPipeline = () => {
  const [dataSource, setDataSource] = useState<JobType[]>([]);

  // TODO: 没写好的部分，完全复制的两套代码
  const { data } = useRequest(() => {
    return queryJobStatusPipeline();
  });

  useEffect(() => {
    setDataSource(data?.job || []);
  }, [data]);

  for (let i = 0; i < (data === undefined ? 0 : data?.job.length); i += 1) {
    if (data !== undefined) {
      data.job[i].cronWord = cronstrue.toString(data.job[i].cron);
    }
  }

  const deleteItem = (param: string) => {
    // eslint-disable-next-line no-console
    if (param !== '') {
      deleteJobPipeline(param).then((r) => {
        if (Number(r.status) === 100) {
          message.success('取消成功。刷新页面查看。');
        }
      });
    }
  };

  const columns: ProColumns<JobType>[] = [
    {
      title: '任务唯一标识',
      dataIndex: 'uuid',
      width: '30%',
      editable: false,
    },
    {
      title: '任务状态',
      dataIndex: 'scheduleStatus',
      valueType: 'select',
      valueEnum: {
        JOB_DONE: <Badge color="green" text="任务完成" />,
        JOB_WAIT: <Badge color="blue" text="任务等待开始" />,
        JOB_RUN: <Badge color="blue" text="任务执行中" />,
        JOB_PASS: <Badge color="green" text="任务通过" />,
        JOB_NOT_EXIST: <Badge color="yellow" text="任务不存在" />,
        JOB_CANCELED: <Badge color="yellow" text="任务取消" />,
        JOB_CANCEL_FAIL: <Badge color="red" text="任务取消失败" />,
        JOB_ERROR: <Badge color="red" text="任务失败" />,
      },
    },
    {
      title: '任务类型',
      dataIndex: 'scheduleType',
      valueEnum: {
        SpiderStart: <Badge color="grey" text="启动爬虫服务" />,
        PredictionUpdate: <Badge color="grey" text="更新所有预测数据" />,
        StockIndexUpdate: <Badge color="grey" text="股票指数更新" />,
        StockAllUpdate: <Badge color="grey" text="更新所有个股数据" />,
        pipelineDailyDefault: <Badge color="grey" text="更新所有基金净值" />,
        FundIndicatorUpdate: <Badge color="grey" text="更新基金经济数据" />,
        StockDaily000300: <Badge color="grey" text="更新沪深300指数" />,
        FundDailyInfoInterpolation: <Badge color="grey" text="基金数据缺省值补充" />,
      },
      editable: () => {
        return true;
      },
    },
    {
      title: '任务定时时间',
      dataIndex: 'cronWord',
      editable: () => {
        return true;
      },
    },
    {
      title: '操作',
      valueType: 'option',
      width: 200,
      render: (text, record) => [
        <a
          key="delete"
          onClick={() => {
            deleteItem(record.uuid || '');
          }}
        >
          取消
        </a>,
      ],
    },
  ];

  return (
    <>
      <EditableProTable<JobType>
        rowKey="fundType"
        headerTitle="交我赚 服务器流水线任务列表"
        tooltip="在此您可以查看交我赚后台的执行任务的线程表，了解每一个后台支持线程的工作状态。"
        maxLength={15}
        columns={columns}
        value={dataSource}
        onChange={setDataSource}
      />

      <ProCard title="后台数据" headerBordered collapsible defaultCollapsed>
        <ProFormField
          ignoreFormItem
          fieldProps={{
            style: {
              width: '100%',
            },
          }}
          mode="read"
          valueType="jsonCode"
          text={JSON.stringify(dataSource)}
        />
      </ProCard>
    </>
  );
};

export default JobMonitorPipeline;
