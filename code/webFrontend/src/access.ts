/**
 * 权限控制插件
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser}) {
  // @ts-ignore
  const currentUser = initialState.currentUser || {auth: { authority: 'ROLE_NONE' }};
  // eslint-disable-next-line no-console
  console.log(initialState);
  // eslint-disable-next-line no-console
  console.log(currentUser.auth);
  return {
    canSuperAdmin: currentUser && (currentUser?.auth.authority === 'ROLE_superAdmin'),

    canAdmin: currentUser && (currentUser.auth.authority === 'ROLE_admin' ||
      currentUser?.auth.authority === 'ROLE_superAdmin'),

    canSuperUser: currentUser && (currentUser.auth.authority === 'ROLE_superUser'||
      currentUser.auth.authority === 'ROLE_admin' ||
      currentUser?.auth.authority === 'ROLE_superAdmin'),

    canUser: currentUser && (currentUser.auth.authority === 'ROLE_user' ||
      currentUser.auth.authority === 'ROLE_superUser' ||
      currentUser.auth.authority === 'ROLE_admin' ||
      currentUser?.auth.authority === 'ROLE_superAdmin'),
  };
}
