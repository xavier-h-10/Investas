import { Card, message } from 'antd';
import ProForm, {
  ProFormDateRangePicker,
  ProFormDigit,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';

import type { FC } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import { createCompetition } from './service';
import { history } from 'umi';

const BasicForm: FC<Record<string, any>> = () => {

  const onFinish = async (values: Record<string, any>) => {
    // eslint-disable-next-line no-console
    // await run(values);
    createCompetition(values).then((value) => {
      // eslint-disable-next-line no-console
      console.log(value);
      if(value.status === 0) {
        message.success('创建成功。您可以通过比赛识别码邀请参赛者。');
        history.push({
          pathname: '/content/createSuccess',
          query: {
            'id': value.message === undefined? '发生未知错误，请联系交我赚管理员。': value.message,
          },
        });
      }
      else{
        message.error('抱歉，创建比赛遇见了错误。您或许需要重新登陆。如果仍然有问题，请联系管理员。', 5);
      }

    }).catch((error) => {
      // eslint-disable-next-line no-console
      console.log(`error occurs: ${  error}`);
    })
  };


  return (
    <PageContainer content="欢迎您创建交我赚™基金模拟投资比赛或者您组织的私人基金模拟投资比赛。">
      <Card bordered={false}>
        <ProForm
          hideRequiredMark
          style={{ margin: 'auto', marginTop: 8, maxWidth: 600 }}
          name="basic"
          layout="vertical"
          initialValues={{ public: '1' }}
          onFinish={onFinish}
        >
          <ProFormText
            width="md"
            label="基金投资模拟比赛赛题"
            name="title"
            rules={[
              {
                required: true,
                message: '请输入基金投资模拟比赛赛题',
              },
            ]}
            placeholder="为基金投资模拟比赛起个名字"
          />
          <ProFormDateRangePicker
            label="起止日期"
            width="md"
            name="date"
            rules={[
              {
                required: true,
                message: '请选择起止日期',
              },
            ]}
            placeholder={['开始日期', '结束日期']}
          />
          <ProFormTextArea
            label="赛事主旨"
            width="xl"
            name="goal"
            rules={[
              {
                required: true,
                message: '请输入赛事主旨',
              },
            ]}
            placeholder="在这里，您可以设置本基金比赛的赛事主旨，即本赛事倡导怎样的比赛理念，希望达到怎样的目标。"
          />

          <ProFormDigit
            label={
              <>
              <span>
                参赛人数(最大1000人)
              </span>
              </>
            }
            name="people"
            placeholder="请输入参赛人数"
            min={0}
            max={1000}
            // @ts-ignore
            width="s"
          />

          <ProFormDigit
            label={
              <>
              <span>
                起始资金
              </span>
              </>
            }
            name="initial"
            placeholder="请输入起始资金"
            min={0}
            max={10000000}
            // @ts-ignore
            width="s"
          />

          <ProFormSelect
            name="select-type"
            label="请选择限定的基金类型"
            valueEnum={{
              T0: '债券型-混合债',
              T1: '混合型-偏股',
              T2: '指数型-股票',
              T3: '混合型-灵活',
              T4: 'QDII',
              T5: '债券型-长债',
              T7: '股票型',
              T8: '混合型-偏债',
              T9: '混合型-平衡',
              T10: '债券型-中短债',
              T11: '商品（不含QDII）',
            }}
            fieldProps={{
              mode: 'multiple',
            }}
            placeholder="请在此选择本赛事所允许购买的基金类型。"
            rules={[
              { required: true, message: '请在此选择本赛事所允许购买的基金类型。',
                type: 'array' },
            ]}
          />

          <ProFormRadio.Group
            options={[
              {
                value: '0',
                label: '公开赛事',
              },
              {
                value: '1',
                label: '私有赛事',

              }
            ]}
            label="赛事属性"
            help="您需要付费创建私有赛事。"
            name="publicType"
          />
        <br />
        </ProForm>
      </Card>
    </PageContainer>
  );
};

export default BasicForm;
