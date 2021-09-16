import { prefix } from '@/services/config';
import request from '@/services/Request';
import type { monitorDataType, PredictionErrorType, ShowDateNumType } from '@/pages/dashboard/analysis/data';

export async function queryMonitorData():
  Promise<{ status: string, message: string, data: monitorDataType }> {
  return (request(
    (`${prefix  }/anyone/monitor/getMonitorData`),
    {
      method: 'GET',
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    }));
}

export async function queryPredictionErrorData():
  Promise<{ status: string, message: string, data: { src: ShowDateNumType [] }}> {
  return (request(
    (`${prefix  }/anyone/predictionError/getErrorMonitorData`),
    {
      method: 'GET',
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    }));
}

export async function queryPredictionErrorByCode(fundCode: string):
  Promise<{ status: string, message: string, data: { src: PredictionErrorType [] }}> {
  return (request(
    (`${prefix  }/anyone/predictionError/getErrorByCode?fundCode=${fundCode}`),
    {
      method: 'GET',
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    }));
}

export async function queryPredictionBest():
  Promise<{ status: string, message: string, data: { src: PredictionErrorType [] }}> {
  return (request(
    (`${prefix  }/anyone/predictionError/getErrorMin`),
    {
      method: 'GET',
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    }));
}


export async function queryPredictionWorst():
  Promise<{ status: string, message: string, data: { src: PredictionErrorType [] }}> {
  return (request(
    (`${prefix  }/anyone/predictionError/getErrorMax`),
    {
      method: 'GET',
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    }));
}

