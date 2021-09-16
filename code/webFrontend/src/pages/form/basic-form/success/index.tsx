
import {  Card,  Result } from 'antd';

import { GridContent } from '@ant-design/pro-layout';
import { useHistory } from 'umi';


export default () => {
  const history = useHistory();
  return (
    <GridContent>
      <Card bordered={false}>
        <Result
          status="success"
          title="创建成功"
          // @ts-ignore
          subTitle={`您创建的比赛识别码是： ${history.location.query.id}`}
          style={{ marginBottom: 16 }}
        >
        </Result>
    </Card>
  </GridContent>)
}
