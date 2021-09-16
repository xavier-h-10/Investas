// https://umijs.org/config/
import { defineConfig } from 'umi';
import { join } from 'path';
import defaultSettings from './defaultSettings';
import proxy from './proxy';
const { REACT_APP_ENV } = process.env;

export default defineConfig({
  hash: true,
  antd: {},
  dva: {
    hmr: true,
  },
  layout: {
    // https://umijs.org/zh-CN/plugins/plugin-layout
    siderWidth: 208,
    ...defaultSettings,
  },
  // https://umijs.org/zh-CN/plugins/plugin-locale
  locale: {
    // default zh-CN
    default: 'zh-CN',
    antd: true,
    // default true, when it is true, will use `navigator.language` overwrite default
    baseNavigator: true,
  },
  dynamicImport: {
    loading: '@ant-design/pro-layout/es/PageLoading',
  },
  targets: {
    ie: 11,
  },
  // umi routes: https://umijs.org/docs/routing
  routes: [
    {
      path: '/user',
      layout: false,
      routes: [
        {
          path: '/user/login',
          layout: false,
          name: 'login',
          component: './user/Login',
        },
        {
          path: '/user',
          redirect: '/user/login',
        },
        {
          name: 'register-result',
          icon: 'smile',
          path: '/user/register-result',
          component: './user/register-result',
        },
        {
          name: 'register',
          icon: 'smile',
          path: '/user/register',
          component: './user/register',
        },
        {
          component: '404',
        },
      ],
    },
    {
      path: '/analysis',
      name: '系统监控区',
      icon: 'dashboard',
      access: 'canAdmin',
      routes: [
        {
          name: '数据面板',
          icon: 'smile',
          path: '/analysis/dashboard',
          component: './dashboard/analysis',
          access: 'canAdmin',

        },
        {
          name: '服务面板',
          icon: 'smile',
          path: '/analysis/monitor',
          component: './dashboard/monitor',
          access: 'canAdmin',
        },

      ],
    },
    {
      path: '/workplace',
      name: '工程师工作区',
      icon: 'dashboard',
      access: 'canAdmin',
      routes: [
        {
          name: 'ML工程师工作区',
          icon: 'smile',
          path: '/workplace/MLEngineer',
          component: './dashboard/workplace',
          access: 'canAdmin',
        },
        {
          name: '金融工程师工作区',
          icon: 'smile',
          path: '/workplace/ecoEngineer',
          component: './dashboard/ecoEngineer',
          access: 'canAdmin',
        },
      ],
    },
    {
      path: '/content',
      icon: 'form',
      name: '基金赛事',
      access: 'canSuperUser',
      routes: [
        {
          name: '创建基金赛事',
          icon: 'smile',
          path: '/content/create',
          component: './form/basic-form',
          access: 'canSuperUser',
        },
        {
          name: '创建基金赛事成功',
          hideInMenu: true,
          icon: 'smile',
          path: '/content/createSuccess',
          component: './form/basic-form/success',
          access: 'canSuperUser',
        },
        {
          name: '基金赛事管理',
          icon: 'smile',
          path: '/content/manager',
          component: './form/basic-list',
          access: 'canAdmin',
        },
      ],
    },
    {
      name: '用户管理',
      icon: 'user',
      path: '/manage',
      access: 'canAdmin',
      routes: [
        {
          name: '普通用户管理',
          icon: 'user',
          path: '/manage/user',
          component: './userManagement/user-list',
          access: 'canAdmin',
        },
        {
          name: '管理员管理',
          icon: 'user',
          path: '/manage/admin',
          component: './userManagement/admin-list',
          access: 'canSuperAdmin',
        },
      ],
    },
    {
      name: '您的账户',
      icon: 'user',
      path: '/account',
      routes: [
        {
          name: '个人设置',
          icon: 'smile',
          path: '/account/settings',
          component: './account/settings',
        },
      ],
    },
    {
      path: '/',
      redirect: '/account/settings',
    },
    {
      component: './exception/404',
    },
  ],
  // Theme for antd: https://ant.design/docs/react/customize-theme-cn
  theme: {
    'primary-color': defaultSettings.primaryColor,
  },
  // esbuild is father build tools
  // https://umijs.org/plugins/plugin-esbuild
  esbuild: {},
  title: false,
  ignoreMomentLocale: true,
  proxy: proxy[REACT_APP_ENV || 'dev'],
  manifest: {
    basePath: '/',
  },
  // Fast Refresh 热更新
  fastRefresh: {},
  openAPI: [
    {
      requestLibPath: "import { request } from 'umi'",
      // 或者使用在线的版本
      // schemaPath: "https://gw.alipayobjects.com/os/antfincdn/M%24jrzTTYJN/oneapi.json"
      schemaPath: join(__dirname, 'oneapi.json'),
      mock: false,
    },
    {
      requestLibPath: "import { request } from 'umi'",
      schemaPath: 'https://gw.alipayobjects.com/os/antfincdn/CA1dOm%2631B/openapi.json',
      projectName: 'swagger',
    },
  ],
  nodeModulesTransform: {
    type: 'none',
  },
  mfsu: {},
  webpack5: {},
  exportStatic: {},
});
