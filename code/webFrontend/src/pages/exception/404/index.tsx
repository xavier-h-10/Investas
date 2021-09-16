import { Link } from 'umi';
import { Result, Button } from 'antd';

export default () => (
  <Result
    status="404"
    title="404"
    style={{
      background: 'none',
    }}
    subTitle="抱歉，您访问的页面不存在或不可被访问。"
    extra={
      <Link to="/">
        <Button type="primary">回到首页</Button>
      </Link>
    }
  />
);
