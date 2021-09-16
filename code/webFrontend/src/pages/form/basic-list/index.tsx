import type { FC } from 'react';
import { Avatar, Card, List, Menu, Modal } from 'antd';

import { PageContainer } from '@ant-design/pro-layout';

import { queryCompetitionList, removeCompetition, updateCompetitionList } from './service';
import type { BasicListItemDataType } from './data.d';
import styles from './style.less';
import { logo } from '@/services/config';
import { useRequest } from '@@/plugin-request/request';

const ListContent = ({
  data: { creatorId, capacity, initialCapital, startDate, endDate },
}: {
  data: BasicListItemDataType;
}) => (
  <div className={styles.listContent}>
    <div className={styles.listContentItem}>
      <span>模拟比赛创建者</span>
      <p>{creatorId}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>参赛人数</span>
      <p>{capacity === 0 ? '∞' : capacity}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>起始资金（人民币元）</span>
      <p>{initialCapital}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>开始时间</span>
      <p>{startDate}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>结束时间</span>
      <p>{endDate}</p>
    </div>
  </div>
);

export const BasicList: FC = () => {
  const { data, loading, mutate } = useRequest(() => {
    return queryCompetitionList();
  });

  const { run: postRun } = useRequest(
    (method, params) => {
      if (method === 'remove') {
        return removeCompetition(params);
      }
      return updateCompetitionList(params);
    },
    {
      manual: true,
      onSuccess: (result) => {
        mutate(result);
      },
    },
  );

  const list = data?.list || [];

  const deleteItem = (id: number) => {
    // eslint-disable-next-line no-console
    postRun('remove', id);
  };

  // const updateList = (type: string) => {
  //   // eslint-disable-next-line no-console
  //   postRun('remove', { type }).then(r => {
  //     return r;
  //   });
  // };

  const editAndDelete = (key: string | number, currentItem: BasicListItemDataType) => {
    if (key === 'delete') {
      Modal.confirm({
        title: '删除比赛',
        content: '确定删除该比赛吗？',
        okText: '确认',
        cancelText: '取消',
        onOk: () => deleteItem(currentItem.competitionId),
      });
    }
  };

  return (
    <div>
      <PageContainer>
        <div className={styles.standardList}>
          <Card
            className={styles.listCard}
            bordered={false}
            title="交我赚™基金模拟投资比赛列表"
            style={{ marginTop: 24 }}
            bodyStyle={{ padding: '0 32px 40px 32px' }}
          >
            <List
              size="large"
              rowKey="competitionId"
              loading={loading}
              dataSource={list}
              renderItem={(item) => (
                <List.Item
                  actions={[
                    <Menu onClick={({ key }) => editAndDelete(key, item)}>
                      <Menu.Item key="delete">删除</Menu.Item>
                    </Menu>,
                  ]}
                >
                  <List.Item.Meta
                    avatar={<Avatar src={logo} shape="square" size="small" />}
                    title={<a href={item.href}>{item.competitionName}</a>}
                    description={item.competitionDescription}
                  />
                  <ListContent data={item} />
                </List.Item>
              )}
            />
          </Card>
        </div>
      </PageContainer>
    </div>
  );
};

export default BasicList;
