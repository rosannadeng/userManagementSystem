import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = '灯灯出品 必是残品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/rosannadeng',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Ant Design',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
