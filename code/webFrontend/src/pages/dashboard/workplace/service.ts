import { prefix } from '@/services/config';
import request from '@/services/Request';
import type {
  NoticeType,
  ActivitiesType,
  AnalysisData,
  WorkPlaceDataType,
  ModelType,
} from './data';

export async function queryProjectNotice(): Promise<{ data: NoticeType[] }> {
  return request('/api/project/notice');
}

export async function queryActivities(): Promise<{ data: ActivitiesType[] }> {
  return request('/api/activities');
}

export async function fakeChartData(): Promise<{ data: AnalysisData }> {
  return request('/api/fake_workplace_chart_data');
}

export async function queryWorkPlaceData(): Promise<{
  status: string;
  message: string;
  data: WorkPlaceDataType;
}> {
  const data = {};
  return request(`${prefix}/anyone/monitor/getWorkplaceData`, {
    method: 'GET',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function modifyModel(
  fundType: string,
  fundCode: string,
): Promise<{ status: string; message: string; data: ModelType[] }> {
  const data = {
    id: fundType,
    code: fundCode,
  };
  return request(`${prefix}/anyone/monitor/setTypeModel`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function deleteModel(param: string): Promise<{ status: string; message: string }> {
  const data = { fundType: param };

  return request(`${prefix}/anyone/monitor/deleteModel`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function updatePrediction(): Promise<{ status: string; message: string }> {
  return request(`${prefix}/anyone/updatePredictions`, {
    method: 'POST',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function updatePredictionError(): Promise<{ status: string; message: string }> {
  return request(`${prefix}/anyone/updatePredictionError`, {
    method: 'POST',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function updateCompetitionData(): Promise<{ status: string; message: string }> {
  return request(`${prefix}/anyone/competition/calculation`, {
    method: 'GET',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function fillFundData(): Promise<{ status: string; message: string }> {
  return request(`${prefix}/anyone/fund/daily/info/interpolation`, {
    method: 'POST',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function deleteFromEstimateData(param: number): Promise<{ status: number }> {
  const data: string = JSON.stringify({ timeType: param });
  return request(`${prefix}/anyone/fund/estimate/delete`, {
    method: 'POST',
    data,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}
