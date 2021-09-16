import React, { useState } from 'react';
import { Avatar, Button, message, Modal, Row, Tooltip, Upload } from 'antd';
import ProForm, { ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { changeUserData, queryCurrent, upgradeSuperUser } from '../service';
import styles from './BaseView.less';
import { Comment } from 'antd';
import moment from 'moment';
import { logo } from '@/services/config';
import { useRequest } from '@@/plugin-request/request';

// 头像组件 方便以后独立，增加裁剪之类的功能
const AvatarView = ({ avatar }: { avatar: string }) => (
  <>
    <div className={styles.avatar}>
      <img src={avatar} alt="avatar" />
    </div>
    <Upload showUploadList={false}></Upload>
  </>
);

const BaseView: React.FC = () => {
  const [showPricing, setShowPricing] = useState<boolean>(false);

  const { data: currentUser, loading } = useRequest(() => {
    return queryCurrent();
  });

  const handleOk = () => {
    upgradeSuperUser(currentUser?.user.userId || 0).then((r) => message.success(r.message));
    // eslint-disable-next-line no-console
    message.success('请重新登陆确认您的权限。');
    setShowPricing(false);
  };

  const handleCancel = () => {
    setShowPricing(false);
  };

  if (currentUser !== undefined) {
    // eslint-disable-next-line no-nested-ternary
    currentUser.user.riskLevelName =
      // eslint-disable-next-line no-nested-ternary
      currentUser.user.riskLevel === 1
        ? '稳健型 R1'
        : // eslint-disable-next-line no-nested-ternary
        currentUser.user.riskLevel === 2
        ? '中风险型 R2'
        : // eslint-disable-next-line no-nested-ternary
        currentUser.user.riskLevel === 3
        ? '中高风险型 R3'
        : currentUser.user.riskLevel === 4
        ? '中风险型 R4'
        : '极高风险型 R5';
  }

  if (currentUser !== undefined) {
    // eslint-disable-next-line no-nested-ternary
    currentUser.user.statusName = currentUser.user.status === 0 ? '正常' : '封禁中';
    currentUser.user.roleName = currentUser.user.roles[0].roleNameZH;
  }

  const getAvatarURL = () => {
    return 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png';
  };

  const handleFinish = async (value: any) => {
    await changeUserData(value);
    message.success('更新基本信息成功');
  };

  return (
    <div className={styles.baseView}>
      {loading ? null : (
        <>
          <div className={styles.left}>
            <ProForm
              layout="vertical"
              onFinish={handleFinish}
              submitter={{
                resetButtonProps: {
                  style: {
                    display: 'none',
                  },
                },
                submitButtonProps: {
                  children: '更新基本信息',
                },
              }}
              initialValues={{
                ...currentUser?.user,
              }}
              hideRequiredMark
            >
              <ProFormText width="md" name="userId" label="用户识别码" disabled={true} />
              <ProFormText width="md" name="email" label="邮箱" disabled={true} />
              <ProFormText width="md" name="statusName" label="用户状态" disabled={true} />
              <ProFormText
                width="md"
                name="nickname"
                label="昵称"
                rules={[
                  {
                    required: true,
                    message: '请输入您的昵称!',
                  },
                ]}
              />
              <ProFormText
                width="md"
                name="riskLevelName"
                label="个人风险等级"
                disabled={true}
                help="可以在App端重新测评"
              />

              <br />

              <Row>
                <ProFormText
                  width="md"
                  name="roleName"
                  label="会员等级"
                  disabled={true}
                  tooltip="超级会员可创建私有比赛"
                  extra={
                    currentUser?.user.roles[0].roleId === 4 ? (
                      <Button
                        onClick={() => {
                          setShowPricing(true);
                        }}
                        style={{ marginTop: 5 }}
                      >
                        即刻升级
                      </Button>
                    ) : (
                      <></>
                    )
                  }
                />
              </Row>

              <ProFormTextArea
                name="introduction"
                label="个人简介"
                rules={[
                  {
                    required: true,
                    message: '请输入个人简介!',
                  },
                ]}
                placeholder="个人简介"
              />
            </ProForm>
          </div>
          <div className={styles.right}>
            <AvatarView avatar={getAvatarURL()} />
          </div>

          <Modal
            title="升级为超级会员"
            visible={showPricing}
            onOk={handleOk}
            okText="立刻升级！"
            cancelText="再想想"
            onCancel={handleCancel}
          >
            <Comment
              author={<a>交我赚团队-LEE</a>}
              avatar={<Avatar src={logo} alt="Han Solo" />}
              content={
                <p>
                  欢迎使用交我赚付费会员服务。您可以创建私有比赛，通过比赛ID邀请您的参赛者。您还可以享受
                  JAI的特别服务，自由下载交我赚独家数据。
                </p>
              }
              datetime={
                <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                  <span>{moment().fromNow()}</span>
                </Tooltip>
              }
            />
            <Comment
              author={<a>交我赚团队-钱老师</a>}
              avatar={<Avatar src={logo} alt="Han Solo" />}
              content={
                <p>
                  我安泰选修课的老师用交我赚我们创建了私人投资比赛，投资比赛结果可以方便的在App上看到。
                  他用交我赚为我们评分。
                </p>
              }
              datetime={
                <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                  <span>{moment().fromNow()}</span>
                </Tooltip>
              }
            />
            <Comment
              author={<a>交我赚团队-汪总</a>}
              avatar={<Avatar src={logo} alt="Han Solo" />}
              content={<p>我用交我赚获取了最新的一手基金信息，为我在交大附近赚了一套房！</p>}
              datetime={
                <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                  <span>{moment().fromNow()}</span>
                </Tooltip>
              }
            />
            <Comment
              author={<a>交我赚团队-黄总</a>}
              avatar={<Avatar src={logo} alt="Han Solo" />}
              content={
                <p>
                  交我赚团队的交AI非常给力！我旗下的私募公司现在都会参考JAI的结果做投资。客户很满意。
                </p>
              }
              datetime={
                <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                  <span>{moment().fromNow()}</span>
                </Tooltip>
              }
            />
          </Modal>
        </>
      )}
    </div>
  );
};

export default BaseView;
