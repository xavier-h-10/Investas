import type { BasicListItemDataType } from './data.d';
import { prefix } from '@/services/config';
import request from '@/services/Request';

export async function queryUserList(): Promise<{
  status: string;
  message: string;
  data: { list: BasicListItemDataType[] };
}> {
  const data = {};
  return request(`${prefix}/user/managerUser/getFullUserInfo`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function updateUser(): Promise<{
  status: string;
  message: string;
  data: { list: BasicListItemDataType[] };
}> {
  const data = {};
  return request(`${prefix}/user/managerUser/getFullUserInfo`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function removeUser(userId: number): Promise<{ status: number; message: string }> {
  const data = JSON.stringify({ userId: userId });
  return request(`${prefix}/user/managerUser/deleteUser`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function restoreUser(userId: number): Promise<{ status: number; message: string }> {
  const data = JSON.stringify({ userId: userId });
  return request(`${prefix}/user/managerUser/restoreUser`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function queryAdminList(
  params: number,
): Promise<{ status: string; message: string; data: { list: BasicListItemDataType[] } }> {
  const data2trans = { id: params };
  const data = JSON.stringify(data2trans);
  return request(`${prefix}/user/managerUser/getFullAdminInfo`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}
