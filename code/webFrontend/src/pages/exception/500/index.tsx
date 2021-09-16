import { Link } from 'umi';
import { Result, Button } from 'antd';

export default () => (
  <Result
    status="500"
    title="500"
    style={{
      background: 'none',
    }}
    subTitle="抱歉，交我赚服务器向我们报告了一个错误，请联系交我赚管理员。"
    extra={
      <Link to="/">
        <Button type="primary">回到首页</Button>
      </Link>
    }
  />
);
