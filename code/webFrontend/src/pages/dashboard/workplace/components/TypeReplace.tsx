import React, { useEffect, useState } from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import { ProFormField } from '@ant-design/pro-form';
import ProCard from '@ant-design/pro-card';
import { ModelType } from '@/pages/dashboard/workplace/data';
import { fundTypeTransHelper } from '@/utils/fundTypeTransHelper';
import { deleteModel, modifyModel } from '@/pages/dashboard/workplace/service';

const TypeReplace = ({ modelData }: { modelData: ModelType[] }) => {
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);
  const [dataSource, setDataSource] = useState<ModelType[]>(modelData);

  console.log(modelData);
  useEffect(() => {
    setDataSource(modelData);
  }, [modelData]);

  for (let i = 0; i < modelData?.length || 0; i += 1) {
    // eslint-disable-next-line no-param-reassign
    modelData[i].fundTypeName = fundTypeTransHelper(modelData[i].fundType);

    if (modelData[i].running) {
      // eslint-disable-next-line no-param-reassign
      modelData[i].state = 'running';
    }
  }

  const deleteItem = (param: string) => {
    console.log(param);
    // eslint-disable-next-line no-console
    deleteModel(param).then((r) => {
      console.log(r);
    });
  };

  const columns: ProColumns<ModelType>[] = [
    {
      title: '基金类型',
      dataIndex: 'fundTypeName',
      width: '30%',
      editable: false,
    },
    {
      title: '状态',
      key: 'state',
      dataIndex: 'state',
      valueType: 'select',
      valueEnum: {
        closed: {
          text: '未生效',
          status: 'error',
        },
        running: {
          text: '运行中',
          status: 'success',
        },
      },
    },
    {
      title: '代表型基金代码',
      dataIndex: 'fundCode',
      editable: () => {
        return true;
      },
    },
    {
      title: '操作',
      valueType: 'option',
      render: (text, record, _, action) => [
        <a
          key="editable"
          onClick={() => {
            action?.startEditable?.(record.fundType || 0);
          }}
        >
          编辑
        </a>,

        <a
          key="delete"
          onClick={() => {
            setDataSource(dataSource.filter((item) => item.fundType !== record.fundType));
            deleteItem(record.fundType || '');
          }}
        >
          删除
        </a>,
      ],
    },
  ];

  return (
    <>
      <EditableProTable<ModelType>
        rowKey="fundType"
        headerTitle="LSTM模型的替代方案设置"
        tooltip="当某新成立基金尚未建立对应模型时，将采用同类型基金模型代替。"
        maxLength={15}
        columns={columns}
        value={dataSource}
        onChange={setDataSource}
        editable={{
          type: 'multiple',
          editableKeys,
          onSave: async (rowKey, data, row) => {
            const fundType = data.fundType || '0';
            const fundCode = data.fundCode || '000001';
            console.log(fundCode, fundType);
            await modifyModel(fundType, fundCode);
            setDataSource([
              ...dataSource.filter((value) => {
                return value.fundType !== rowKey;
              }),
              row,
            ]);
          },
          onChange: setEditableRowKeys,
        }}
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

export default TypeReplace;
