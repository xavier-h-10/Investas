import { prefix } from '@/services/config';
import request from '@/services/Request';
import type { NoticeType, DescriptionType, ShowRuleSetUsingType } from './data';

export async function queryProjectNotice(): Promise<{ data: NoticeType[] }> {
  return request('/api/project/notice');
}

export async function addDescriptionData(
  dataSource: any,
): Promise<{ status: string; message: string; data: { src: DescriptionType[] } }> {
  const data: string = JSON.stringify(dataSource);
  return request(`${prefix}/anyone/description/createDescription`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function queryDescriptionData(): Promise<{
  status: string;
  message: string;
  data: { src: DescriptionType[] };
}> {
  return request(`${prefix}/anyone/description/getAllDescription`, {
    method: 'GET',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function deleteDescriptionData(
  param: number,
): Promise<{ status: string; message: string }> {
  const data = JSON.stringify({ value: param });
  return request(`${prefix}/anyone/description/deleteDescription`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function queryDescriptionMonitorData(): Promise<{
  status: string;
  message: string;
  data: { src: ShowRuleSetUsingType[] };
}> {
  const data = '';
  return request(`${prefix}/anyone/description/getReportData`, {
    method: 'GET',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function updatePredictionDescription(): Promise<{ status: string; message: string }> {
  return request(`${prefix}/anyone/description/updateDescription`, {
    method: 'POST',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}
