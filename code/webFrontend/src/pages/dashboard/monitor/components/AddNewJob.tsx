import { useRef, useState } from 'react';
import ProForm, { ProFormInstance, ProFormList } from '@ant-design/pro-form';
import { ProFormSelect, ProFormText, StepsForm } from '@ant-design/pro-form';
import ProCard from '@ant-design/pro-card';
import { Result, message, Popover, Button } from 'antd';
import cronstrue from 'cronstrue';
import { SubmitJobType } from '@/pages/dashboard/monitor/data';
import { submitNewJobService } from '@/pages/dashboard/monitor/service';

export type PorpType = {
  name: string;
  value: string;
};

const JobMonitor = () => {
  const formRef = useRef<ProFormInstance>();
  const [tab, setTab] = useState('tab1');
  const [type, setType] = useState<number>(1);
  const [cronVal, setCronValue] = useState<string>('');
  const [newProps, setNewProps] = useState<PorpType[]>([]);

  const updateCronValue = (value: string): void => {
    try {
      setCronValue(cronstrue.toString(value));
    } catch (e) {
      // eslint-disable-next-line no-console
      console.log(e);
      setCronValue('此格式无法解析。请确认您的时间是Cron格式。');
    }
  };

  const updateNewProps = async (value: any) => {
    let data: PorpType[] = [];
    for (let i = 0; i < value.var.length; i += 1) {
      data = [...data, { name: value.var[i].varName, value: value.var[i].varValue }];
    }
    await setNewProps(data);
  };

  const submitNewJob = (value: any): string => {
    // eslint-disable-next-line no-console
    console.log(value);
    if (value.name === '1') {
      const data: SubmitJobType = {
        scheduleType: value.name,
        cronStrings: [value.time],
        arguments: newProps,
      };
      console.log(data);
      submitNewJobService(data, value.useMode).then((r) => {
        if (Number(r.status) !== 100) {
          message.error('任务调度失败');
          return;
        }
        const uuid = r.data.uuid.length === 0 ? '' : r.data.uuid[0];
        message.success(`任务提交成功。唯一标识为：${uuid}`);
      });
    } else {
      const data: SubmitJobType = {
        scheduleType: value.name,
        cronStrings: [value.time],
        arguments: newProps,
      };
      submitNewJobService(data, value.useMode).then((r) => {
        if (Number(r.status) !== 100) {
          message.error('任务调度失败');
          return;
        }
        const uuid = r.data.uuid.length === 0 ? '' : r.data.uuid[0];
        message.success(`任务提交成功。唯一标识为：${uuid}`);
      });
    }
    return '';
  };

  // @ts-ignore

  return (
    <ProCard
      title="添加新任务"
      tabs={{
        tabPosition: 'top',
        activeKey: tab,
        onChange: (key) => {
          setTab(key);
        },
      }}
    >
      <ProCard.TabPane key="tab1" tab="定时任务">
        {/* 表格：分步骤 */}
        <StepsForm<{
          name: string;
        }>
          formRef={formRef}
          onFinish={async (value) => {
            submitNewJob(value);
          }}
          formProps={{
            validateMessages: {
              required: '此项为必填项',
            },
          }}
        >
          {/* 第一步 */}
          <StepsForm.StepForm<{
            name: string;
            useMode: string;
          }>
            name="base"
            title="选择任务类型"
            stepProps={{
              description: '选择任务的基本类型',
            }}
            // @ts-ignore
            onFinish={(formData: { name: string }): boolean => {
              setType(Number(formData.name));
              return true;
            }}
          >
            <ProFormSelect
              options={[
                {
                  value: '0',
                  label: '启动爬虫服务',
                },
                {
                  value: '1',
                  label: '股票指数更新',
                },
                {
                  value: '2',
                  label: '更新所有个股数据',
                },
                {
                  value: '3',
                  label: '每日流水线默认行为',
                },
                {
                  value: '4',
                  label: '专业指标数据更新',
                },
                {
                  value: '5',
                  label: '更新沪深300指数',
                },
                {
                  value: '6',
                  label: '基金数据缺省值补充',
                },
              ]}
              name="name"
              label="任务类型"
              width="md"
              tooltip="到达约定的时间，会执行任务。"
              placeholder="请选择类型"
              rules={[{ required: true }]}
            />

            <ProFormSelect
              width="md"
              options={[
                { label: '高性能并发执行', value: 'custom' },
                { label: '流水线执行', value: 'pipeline' },
              ]}
              name="useMode"
              label="任务执行方式"
              placeholder="请选择执行方式"
              tooltip="在流水线模式中，时间靠后的任务一定比时间靠前的任务后执行。在并发模式中，任务将乱序并发执行。"
              rules={[{ required: true }]}
            />
          </StepsForm.StepForm>

          {/* 第二步 */}
          <StepsForm.StepForm<{
            checkbox: string;
          }>
            name="checkbox"
            title="设置参数"
            stepProps={{
              description: '对于某些任务，可能需要设置参数',
            }}
            onFinish={async () => {
              console.log(formRef.current?.getFieldsValue());
              return true;
            }}
          >
            {type === 0 ? (
              <ProForm
                onFinish={(value) => {
                  return updateNewProps(value);
                }}
              >
                <ProFormList
                  name="var"
                  label="参数列表"
                  creatorButtonProps={{
                    position: 'bottom',
                  }}
                >
                  <ProForm.Group size={15}>
                    <ProFormText name="varName" rules={[{ required: true }]} label="参数名" />
                    <ProFormText name="varValue" rules={[{ required: true }]} label="参数值" />
                  </ProForm.Group>
                </ProFormList>
              </ProForm>
            ) : (
              <div>
                <Result
                  status="success"
                  title="您选择的任务没有额外的参数需要指定。"
                  subTitle="点击“下一步”以继续。"
                />
                {/* <Progress type="circle" percent={100}  style={{margin: 20}}/> */}
                {/* <div style={{marginTop: 20, marginBottom: 20}}> */}
                {/*  您选择的任务没有额外的参数需要指定。点击“下一步”以继续。 */}
                {/* </div> */}
              </div>
            )}
          </StepsForm.StepForm>

          {/* 第三步 */}
          <StepsForm.StepForm
            name="time"
            title="选择执行时间"
            stepProps={{
              description: '填写定时任务的执行时间',
            }}
          >
            <div>
              <ProFormText
                name="time"
                label="时间"
                width="md"
                tooltip='以Cron格式输入，示例："0 30/1 9-11 * * 1-5".'
                placeholder="请输入时间"
                rules={[{ required: true }]}
                extra={
                  <Popover content={cronVal} title="您输入的时间" trigger="click">
                    <Button
                      style={{ marginTop: 10, marginBottom: 10 }}
                      onClick={() =>
                        updateCronValue(String(formRef.current?.getFieldsValue().time) || '')
                      }
                    >
                      即刻检查格式
                    </Button>
                  </Popover>
                }
              />
            </div>
          </StepsForm.StepForm>
        </StepsForm>
      </ProCard.TabPane>
    </ProCard>
  );
};

export default JobMonitor;
