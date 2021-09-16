import React from 'react';
import { Pie } from '@ant-design/charts';
import { useRequest } from '@@/plugin-request/request';
import { queryDescriptionMonitorData } from '@/pages/dashboard/ecoEngineer/service';

const RuleSetSituation: React.FC = () => {
  const { data } = useRequest(() => {
    return queryDescriptionMonitorData();
  });

  const config = {
    appendPadding: 10,
    data: data?.src || [],
    angleField: 'num',
    colorField: 'name',
    radius: 0.7,
    label: {
      type: 'spider',
      labelHeight: 28,
      content: '{name}\n{percentage}',
    },
    interactions: [{ type: 'element-selected' }, { type: 'element-active' }],
  };

  // @ts-ignore
  return <Pie {...config} />;
};

export default RuleSetSituation;
