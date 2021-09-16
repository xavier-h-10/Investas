import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';

export default () => {
  return (
    <DefaultFooter
      copyright={`2021 交我赚`}
      links={[
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/SJTU-WebAppDevs-Group',
          blankTarget: true,
        }
      ]}
    />
  );
};
