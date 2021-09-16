import type { FC } from 'react';
import { useEffect, useState } from 'react';
import { Avatar, Card, List, Menu, message, Modal } from 'antd';

import { PageContainer } from '@ant-design/pro-layout';
import { queryUserList, removeUser, restoreUser } from './service';
import type { BasicListItemDataType } from './data.d';
import styles from './style.less';
import { logo } from '@/services/config';
import { useRequest } from '@@/plugin-request/request';

const ListContent = ({
  data: { userId, username, nickname, email, riskLevel },
}: {
  data: BasicListItemDataType;
}) => (
  <div className={styles.listContent}>
    <div className={styles.listContentItem}>
      <span>用户标识符</span>
      <p>{userId}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>系统内用户名</span>
      <p>{username}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>用户昵称</span>
      <p>{nickname}</p>
    </div>
    <div className={styles.listContentItem}>
      <span>用户邮箱</span>
      <p>{email}</p>
    </div>

    <div className={styles.listContentItem}>
      <span>风险等级</span>
      {/* eslint-disable-next-line no-nested-ternary */}
      <p>
        {riskLevel === 1
          ? '稳健型'
          : // eslint-disable-next-line no-nested-ternary
          riskLevel === 2
          ? '中低风险型'
          : // eslint-disable-next-line no-nested-ternary
          riskLevel === 3
          ? '中高风险型'
          : riskLevel === 4
          ? '高风险型'
          : '激进型'}
      </p>
    </div>
  </div>
);

export const BasicList: FC = () => {
  const {
    run: getInfo,
    data,
    loading,
  } = useRequest(() => {
    return queryUserList();
  });

  const { run } = useRequest(
    (params: number) => {
      return removeUser(params);
    },
    {
      manual: true,
    },
  );

  const { run: restore } = useRequest(
    (params: number) => {
      return restoreUser(params);
    },
    {
      manual: true,
    },
  );

  const [list, setList] = useState<BasicListItemDataType[]>([]);

  const deleteItem = async (id: number) => {
    // eslint-disable-next-line no-console
    await run(id).then(() => message.success('禁用成功'));
    await getInfo().then((r) => {
      return setList(r?.list || []);
    });
  };

  const restoreItem = async (id: number) => {
    // eslint-disable-next-line no-console
    await restore(id).then(() => message.success('解禁成功'));
    await getInfo().then((r) => {
      return setList(r?.list || []);
    });
  };

  useEffect(() => {
    setList(data?.list || []);
  }, [data]);

  // const updateList = (type: string) => {
  //   // eslint-disable-next-line no-console
  //   postRun('remove', { type }).then(r => {
  //     return r;
  //   });
  // };

  const editAndDelete = (key: string | number, currentItem: BasicListItemDataType) => {
    if (key === 'delete') {
      Modal.confirm({
        title: '禁用用户',
        content: '确定禁用此用户吗？',
        okText: '确认',
        cancelText: '取消',
        onOk: () => deleteItem(currentItem.userId),
      });
    } else {
      Modal.confirm({
        title: '解禁用户',
        content: '确定解禁此用户吗？',
        okText: '确认',
        cancelText: '取消',
        onOk: () => restoreItem(currentItem.userId),
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
            title="交我赚™用户列表"
            style={{ marginTop: 24 }}
            bodyStyle={{ padding: '0 32px 40px 32px' }}
          >
            <List
              size="large"
              rowKey="userId"
              loading={loading}
              dataSource={list}
              renderItem={(item) => {
                return (
                  <List.Item
                    actions={
                      item.accountNonExpired
                        ? [
                            <Menu onClick={({ key }) => editAndDelete(key, item)}>
                              <Menu.Item key="delete">禁用</Menu.Item>
                            </Menu>,
                          ]
                        : [
                            <Menu onClick={({ key }) => editAndDelete(key, item)}>
                              <Menu.Item key="restore">解禁</Menu.Item>
                            </Menu>,
                          ]
                    }
                  >
                    <List.Item.Meta
                      avatar={<Avatar src={logo} shape="square" size="small" />}
                      title={<p>{item.username}</p>}
                      description={item.introduction}
                    />
                    <ListContent data={item} />
                  </List.Item>
                );
              }}
            />
          </Card>
        </div>
      </PageContainer>
    </div>
  );
};

export default BasicList;
