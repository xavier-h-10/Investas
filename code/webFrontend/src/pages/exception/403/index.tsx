import { Link } from 'umi';
import { Result, Button } from 'antd';

export default () => (
  <Result
    status="403"
    title="403"
    style={{
      background: 'none',
    }}
    subTitle="抱歉，您似乎没有足够的权限访问目标页面，请联系交我赚管理员。"
    extra={
      <Link to="/">
        <Button type="primary">回到首页</Button>
      </Link>
    }
  />
);
