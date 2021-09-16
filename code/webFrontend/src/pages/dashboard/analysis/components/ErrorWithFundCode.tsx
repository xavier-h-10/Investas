import { useRequest } from '@@/plugin-request/request';
import { queryPredictionErrorByCode } from '@/pages/dashboard/analysis/service';
import { Suspense, useState } from 'react';
import { PredictionErrorType } from '@/pages/dashboard/analysis/data';
import { ProFormText, QueryFilter } from '@ant-design/pro-form';
import ProCard from '@ant-design/pro-card';
import { Col, Empty, Row, Tooltip } from 'antd';
import { Column, Line, TinyArea, TinyColumn } from '@ant-design/charts';
import moment from 'moment';
import { ChartCard, Field } from '@/pages/dashboard/analysis/components/Charts';
import { InfoCircleOutlined } from '@ant-design/icons';

const topColResponsiveProps = {
  xs: 24,
  sm: 12,
  md: 12,
  lg: 12,
  xl: 6,
  style: { marginBottom: 24 },
};

const ErrorWithFundCode = () => {

  const [dataSource, setDataSource] = useState<PredictionErrorType []>([]);

  const { run, loading } = useRequest((fundCode) => {
    return queryPredictionErrorByCode(fundCode);
  }, {manual: true});

  const RMSEConfig = {
    data: dataSource,
    xField: 'lastUpdateTimestamp',
    yField: 'todayMSE',
    legend: false,
  };

  // useEffect( () => {
  //   return setDataSource(data?.src || []);
  // }, [data])

  const ABSDealtaConfig = {
    data: dataSource,
    xField: 'lastUpdateTimestamp',
    yField: 'todayAbsDelta',
    color: '#DC143C',
    yAxis: {
      label: {
        formatter: function formatter(v: number) {
          return ''.concat((v / 10).toFixed(4), ' ％');
        },
      },
    },
    title: '(实际值 - 预测值) / 实际值',
    annotations: [
      {
        type: 'line',
        start: ['min', 0],
        end: ['max', 0],
        text: {
          content: '实际准确线',
          position: 'right',
          offsetY: 18,
          style: { textAlign: 'right' },
        },
        style: {
          lineDash: [4, 4],
        },
      },
    ],
    legend: { position: 'top' },
    smooth: true,
    animation: {
      appear: {
        animation: 'path-in',
        duration: 4000,
      },
    },
  };

  return (
    <>
      <ProCard title="查看基金的最近预测误差"
               loading={loading}
               headerBordered
               tooltip='|（预测值 - 实际值）| / 实际值 是本图所展现的绝对误差比。'
               extra={<div style={{color: 'red'}}>
                 如果某基金的预测值偏差超过参考区间，请联系ML工程师。
               </div>}
               >
        <Row>
          <Col xl={24} lg={24} md={24} sm={24} xs={24}>
            <Suspense fallback={null}>
              <div>
                <QueryFilter
                  onFinish={(value) => {
                    return run(value.fundCode).then(r => {
                      // eslint-disable-next-line no-console
                      console.log(r);
                      for (let i = 0; i < r.src.length; i += 1) {
                        // eslint-disable-next-line no-param-reassign
                        r.src[i].lastUpdateTimestamp = moment(r.src[i].lastUpdateTimestamp).format('YYYY-MM-DD');
                      }
                      setDataSource(r.src);
                    });
                  }}
                >
                  <ProFormText
                    name="fundCode"
                    label="基金代码"
                  />
                </QueryFilter>
              </div>
            </Suspense>
          </Col>
        </Row>


        <Row gutter={24}>
          <Col {...topColResponsiveProps}>
            <ChartCard
              bordered={false}
              title="昨日预测偏差"
              action={
                <Tooltip title="计算公式：| 实际NAV - 昨日预测NAV | / 实际NAV">
                  <InfoCircleOutlined />
                </Tooltip>
              }
              loading={loading}
              total={() => (
                dataSource.length === 0?<>请输入基金</>:<>{dataSource[0].todayAbsDelta} ‰</>
              )}
              footer={<Field label="参考区间：" value={'-10‰ - 10‰'} />}
              contentHeight={46}
            >
              <TinyColumn xField="x" height={46} forceFit yField="y" />
            </ChartCard>
          </Col>

          <Col {...topColResponsiveProps}>
            <ChartCard
              bordered={false}
              loading={loading}
              title="昨日预测MSE"
              action={
                <Tooltip title="计算公式：| 实际NAV - 昨日预测NAV | ^ 2">
                  <InfoCircleOutlined />
                </Tooltip>
              }
              total={dataSource.length === 0?<>代码查询或</>:<>{dataSource[0].todayMSE}</>}
              footer={<Field label="参考区间" value={'0-1'} />}
              contentHeight={46}
            >
              <TinyArea
                color="#975FE4"
                xField="x"
                height={46}
                forceFit
                yField="y"
                smooth
              />
            </ChartCard>
          </Col>
          <Col {...topColResponsiveProps}>
            <ChartCard
              bordered={false}
              loading={loading}
              title="昨日预测RMSE"
              action={
                <Tooltip title="计算公式：sqrt (| 实际NAV - 昨日预测NAV | ^ 2)">
                  <InfoCircleOutlined />
                </Tooltip>
              }
              total={dataSource.length === 0?<>确认您输入的</>:<>{dataSource[0].todayRMSE}</>}
              footer={<Field label="参考区间" value={'0-1'} />}
              contentHeight={46}
            >
              <TinyColumn xField="x" height={46} forceFit yField="y" />
            </ChartCard>
          </Col>
          <Col {...topColResponsiveProps}>
            <ChartCard
              loading={loading}
              bordered={false}
              title="昨日预测MAE * 1000"
              action={
                <Tooltip title="计算公式：| 实际NAV - 昨日预测NAV | * 1000">
                  <InfoCircleOutlined />
                </Tooltip>
              }
              total={dataSource.length === 0?<>基金存在。</>:<>{(dataSource[0].todayMAE)}</>}
              footer={<Field label="参考区间" value={'0-1000'} />}
              contentHeight={46}
            >
              <TinyColumn xField="x" height={46} forceFit yField="y" />
            </ChartCard>
          </Col>
        </Row>

        <Row>
          <Col xl={24} lg={24} md={24} sm={24} xs={24}>
            <Suspense fallback={<Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />}>
              <Line {...ABSDealtaConfig} />
            </Suspense>
          </Col>
        </Row>
        <Row>
          <Col xl={24} lg={24} md={24} sm={24} xs={24}>
            <Suspense fallback={<Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />}>
              <Column {...RMSEConfig} />
            </Suspense>
          </Col>
        </Row>


      </ProCard>
    </>
  )

}

export default ErrorWithFundCode;
