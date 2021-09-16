
import type { BasicListItemDataType } from './data.d';
import { prefix } from '@/services/config';
import request from '@/services/Request';

export async function queryCompetitionList():
  Promise<{ status: string, message: string, data: {list: BasicListItemDataType[]} }> {
  const data = {};
  return request (
    (`${prefix}/user/competition/getAllCompetition`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}

export async function removeCompetition(params: number):
  Promise<{ status: string, message: string, data: {list: BasicListItemDataType[]} }> {
  const data2trans = {"id": params};
  const data = JSON.stringify(data2trans);
  return request (
    (`${prefix}/user/competition/removeCompetition`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}

export async function updateCompetitionList(params: number):
  Promise<{ status: string, message: string, data: {list: BasicListItemDataType[]} }> {
  const data = JSON.stringify(params);
  return request (
    (`${prefix}/user/competition/updateCompetitionList`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: { 'Content-Type': 'application/json' },
    });
}

