import React, { useEffect, useState } from 'react';
import { Button, message } from 'antd';
import type { ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { DescriptionType, RuleType } from '@/pages/dashboard/ecoEngineer/data';
import { useRequest } from '@@/plugin-request/request';
import { deleteDescriptionData, queryDescriptionData } from '@/pages/dashboard/ecoEngineer/service';
import { Modal, Result } from 'antd';
import { EditableProTable } from '@ant-design/pro-table';
import { ProFormDigit, ProFormText, ProFormTextArea, StepsForm } from '@ant-design/pro-form';
import { addDescriptionData } from '@/pages/dashboard/ecoEngineer/service';

const expandedRowRender = (value: any) => {
  for (let i = 0; i < value.ruleList.length; i += 1) {
    // eslint-disable-next-line no-param-reassign
    value.ruleList[i].ruleOrientationToShow =
      value.ruleList[i].orientation === '0' ? 'smaller' : 'larger';

    // eslint-disable-next-line no-param-reassign
    value.ruleList[i].ruleTypeToShow = `T${value.ruleList[i].ruleType}`;
  }

  return (
    <ProTable
      columns={[
        {
          title: '本条规则项目',
          tooltip: '如有同时>= & <= 即 ==',
          dataIndex: 'ruleTypeToShow',
          valueType: 'select',
          valueEnum: {
            T1: {
              text: '基金类型',
            },
            T2: {
              text: '过去1交易日涨跌幅（含%）',
            },
            T3: {
              text: '过去1周交易日涨跌幅（含%）',
            },
            T4: {
              text: '过去1月交易日涨跌幅（含%）',
            },
            T5: {
              text: '过去6月交易日涨跌幅（含%）',
            },
            T6: {
              text: '过去1年交易日涨跌幅（含%）',
            },
            T7: {
              text: '过去3年交易日涨跌幅（含%）',
            },
            T8: {
              text: '未来1日交易日涨跌幅（含%）',
            },
            T9: {
              text: '未来3日交易日涨跌幅（含%）',
            },
          },
          width: '30%',
        },
        {
          title: '符号方向',
          dataIndex: 'ruleOrientationToShow',
          valueType: 'select',
          valueEnum: {
            larger: {
              text: '>=',
            },
            smaller: {
              text: '<=',
            },
          },
          width: '10%',
        },
        {
          title: '值',
          tooltip:
            "如果您选择了基金类型，对应表为：'债券型-混合债': 0, '混合型-偏股': 1, '指数型-股票': 2, '混合型-灵活':" +
            " 3, 'QDII': 4, '债券型-长债': 5,\n" +
            "    '货币型': 6, '股票型': 7， '混合型-偏债': 8, '混合型-平衡': " +
            "9, '债券型-中短债': 10, '商品（不含QDII）': 11,\n" +
            "    '债券型-可转债': 12, 'REITs': 13, ''(other type): 14",
          dataIndex: 'ruleValue',
        },
      ]}
      headerTitle="规则细则"
      options={false}
      search={false}
      dataSource={value?.ruleList}
      pagination={false}
    />
  );
};

const RuleMatching = () => {
  const [visible, setVisible] = useState(false);
  const [rules, setRules] = useState<RuleType[]>([]);
  const [descriptionData, setDescriptionData] = useState<DescriptionType[]>([]);
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);

  const { data } = useRequest(() => {
    return queryDescriptionData();
  });

  useEffect(() => {
    setDescriptionData(data?.src || []);
  }, [data?.src]);

  const FormColumns: ProColumns<RuleType>[] = [
    {
      title: '本条规则项目',
      tooltip: '相等方法：同时勾选大于等于和小于等于',
      dataIndex: 'ruleType',
      valueType: 'select',
      valueEnum: {
        T1: {
          text: '基金类型',
        },
        T2: {
          text: '过去1交易日涨跌幅（含%）',
        },
        T3: {
          text: '过去1周交易日涨跌幅（含%）',
        },
        T4: {
          text: '过去1月交易日涨跌幅（含%）',
        },
        T5: {
          text: '过去6月交易日涨跌幅（含%）',
        },
        T6: {
          text: '过去1年交易日涨跌幅（含%）',
        },
        T7: {
          text: '过去3年交易日涨跌幅（含%）',
        },
        T8: {
          text: '未来1日交易日涨跌幅（含%）',
        },
        T9: {
          text: '未来3日交易日涨跌幅（含%）',
        },
      },
      // 第二行不允许编辑
      editable: () => {
        return true;
      },
      width: '30%',
    },
    {
      title: '符号方向',
      key: 'orientation',
      dataIndex: 'orientation',
      valueType: 'select',
      valueEnum: {
        larger: {
          text: '>=',
        },
        smaller: {
          text: '<=',
        },
      },
    },
    {
      title: '值',
      tooltip:
        "如果您选择了基金类型，对应表为：'债券型-混合债': 0, '混合型-偏股': 1, '指数型-股票': 2, '混合型-灵活':" +
        " 3, 'QDII': 4, '债券型-长债': 5,\n" +
        "    '货币型': 6, '股票型': 7， '混合型-偏债': 8, '混合型-平衡': " +
        "9, '债券型-中短债': 10, '商品（不含QDII）': 11,\n" +
        "    '债券型-可转债': 12, 'REITs': 13, ''(other type): 14",
      dataIndex: 'ruleValue',
    },

    {
      title: '操作',
      valueType: 'option',
      width: 200,
      render: (text, record, _, action) => [
        <a
          key="editable"
          onClick={() => {
            action?.startEditable?.(_);
          }}
        >
          编辑
        </a>,
        <a
          key="delete"
          onClick={() => {
            setRules(rules.filter((item) => item !== record));
          }}
        >
          删除
        </a>,
      ],
    },
  ];

  const columns: ProColumns<DescriptionType>[] = [
    {
      title: '规则集名称',
      dataIndex: 'name',
      width: '15%',
    },
    {
      title: '优先级',
      key: 'priority',
      dataIndex: 'priority',
      valueType: 'select',
      width: '5%',
    },
    {
      title: '规则展示内容',
      dataIndex: 'text',
      width: '68%',
    },
    {
      title: '操作',
      valueType: 'option',
      width: 100,
      render: (text, record) => [
        <a
          key="delete"
          onClick={() => {
            deleteDescriptionData(record.descriptionId || -1).then((r) => {
              // eslint-disable-next-line no-console
              message.success(r.message).then((s) => console.log(s));
            });
            setDescriptionData(
              descriptionData.filter((item) => item.descriptionId !== record.descriptionId),
            );
          }}
        >
          删除规则集
        </a>,
      ],
    },
  ];

  return (
    <>
      {/* 外面的表格，展示现在有的，不能修改，给一个可以删除的选项 */}
      <ProTable<DescriptionType>
        columns={columns}
        dataSource={descriptionData}
        rowKey="descriptionId"
        expandable={{ expandedRowRender }}
        search={false}
        dateFormatter="string"
        headerTitle="所有规则集"
        options={false}
        toolBarRender={() => [
          <Button key="primary" type="primary" onClick={() => setVisible(true)}>
            创建规则集
          </Button>,
        ]}
      />

      <>
        {/* 里面的表格，用于新建规则，第一页是规则添加，第二页是起名字和正式输入文本，给定优先级，第三页是创建成功页面 */}
        <StepsForm
          onFinish={async (values) => {
            const FinalData = {
              rule: rules,
              basicInfo: values,
            };
            await addDescriptionData(FinalData).then((value) => {
              setDescriptionData(value.data.src);
            });
            setVisible(false);
            message.success('提交成功');
          }}
          formProps={{
            validateMessages: {
              required: '此项为必填项',
            },
          }}
          stepsFormRender={(dom, submitter) => {
            return (
              <Modal
                title="创建新的解释匹配规则"
                width={1100}
                onCancel={() => setVisible(false)}
                visible={visible}
                footer={submitter}
                destroyOnClose
              >
                {dom}
              </Modal>
            );
          }}
        >
          {/* 第一步 */}
          <StepsForm.StepForm
            name="rule"
            title="建立规则"
            onFinish={async () => {
              return true;
            }}
          >
            <>
              <EditableProTable<RuleType>
                headerTitle="规则之间的关系是 AND &"
                maxLength={100}
                columns={FormColumns}
                value={rules}
                onChange={setRules}
                editable={{
                  type: 'single',
                  editableKeys,
                  onSave: async (rowKey, SPData) => {
                    // eslint-disable-next-line no-param-reassign
                    setRules([...rules, SPData]);
                  },
                  onChange: setEditableRowKeys,
                }}
              />
            </>
          </StepsForm.StepForm>

          {/* 第二步 */}
          <StepsForm.StepForm
            name="checkbox"
            title="制定匹配文本"
            onFinish={async () => {
              return true;
            }}
          >
            {/* 表单，用于写三个重要的东西 */}
            <ProFormText
              name="name"
              width="md"
              label="方案名字"
              tooltip="输入此规则方案的名字，这个名字永远不会向客户展示。"
              placeholder="请输入名称"
              rules={[{ required: true }]}
            />
            <ProFormTextArea
              name="description"
              label="具体机器学习结果说明"
              tooltip="Attention! 此说明会被直接显示在Fund View上。"
              placeholder="请输入名称"
              rules={[{ required: true }]}
            />
            <ProFormDigit
              label="方案优先级"
              name="priority"
              width="xs"
              min={1}
              max={100}
              fieldProps={{ precision: 0 }}
              tooltip="1-100级，越大优先级越大。程序会从高到低扫描所有方案，选择第一个命中方案为每个模型结果产生解释并向客户展示。"
              rules={[{ required: true }]}
            />
          </StepsForm.StepForm>

          {/* 第三步 */}
          <StepsForm.StepForm name="time" title="加入新规则">
            <Result
              status="success"
              title="可以提交。"
              subTitle="您的规则与已有规则没有冲突，也符合要求的制定规则的规定。"
            />
          </StepsForm.StepForm>
        </StepsForm>
      </>
    </>
  );
};

export default RuleMatching;
