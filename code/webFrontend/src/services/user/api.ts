// @ts-ignore
/* eslint-disable */
import request from '@/services/Request';
import { prefix } from '@/services/config';

/** 获取当前的用户 GET /api/currentUser */
export async function currentUser() {
  return request<{ status: number; message: string; data: API.CurrentUser }>(
    `${prefix}/user/getUserInfo`,
    {
      method: 'POST',
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    },
  );
}

/** 退出登录接口 POST /api/login/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>(`${prefix}/logout`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  });
}

/** 登录接口 POST /api/login/account */
export async function login(params: any, options?: { [key: string]: any }) {
  const data: string = JSON.stringify(params);
  return request(prefix + '/doLogin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: data,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
