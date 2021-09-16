import { prefix } from '@/services/config';
import request from '@/services/Request';
import type { CurrentUser } from './data';

export async function queryCurrent():
  Promise<{ status: string, message: string, data: {user: CurrentUser} }> {
  const data = {};
  return request (
    (`${prefix}/user/currentUserInfo`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}

export async function changeUserData(value: any):
  Promise<{ status: string, message: string }> {
  const data = JSON.stringify(value);

  console.log(data)
  return request (
    (`${prefix}/user/changeUserInfo`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}

export async function upgradeSuperUser(value: number):
  Promise<{ status: string, message: string }> {
  const data = JSON.stringify({'userId': value});

  console.log(data)
  return request (
    (`${prefix}/user/upgradeToSuperUser`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}



