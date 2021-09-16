import {  Col, Row } from 'antd';
import type { FC } from 'react';
import { GridContent } from '@ant-design/pro-layout';
import JobMonitor from '@/pages/dashboard/monitor/components/JobMonitor';
import AddNewJob from '@/pages/dashboard/monitor/components/AddNewJob';
import JobMonitorPipeline from '@/pages/dashboard/monitor/components/JobMonitorPipeline';



const Monitor: FC = () => {
  return (
    <GridContent>
      <>
        <Row gutter={24}>
          <Col xl={24} lg={24} md={24} sm={24} xs={24} style={{ marginBottom: 24 }}>
            <JobMonitor/>
          </Col>

          <Col xl={24} lg={24} md={24} sm={24} xs={24} style={{ marginBottom: 24 }}>
            <JobMonitorPipeline/>
          </Col>

          <Col xl={24} lg={24} md={24} sm={24} xs={24} style={{ marginBottom: 24 }}>
            <AddNewJob/>
          </Col>
        </Row>
      </>
    </GridContent>
  );
};

export default Monitor;
