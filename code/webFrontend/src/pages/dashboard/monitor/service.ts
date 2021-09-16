import { request } from 'umi';
import type { JobType, SubmitJobType, TagType } from './data';
import {prefix} from "@/services/config";

export async function queryTags(): Promise<{ data: { list: TagType[] } }> {
  return request('/api/tags');
}

export async function queryJobStatus(): Promise<{
  status: string;
  message: string;
  data: { job: JobType[] };
}> {
  // TODO: Fix to prefix url.
  return request(`${prefix}/anyone/schedule/custom/status`, {
    method: 'GET',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function queryJobStatusPipeline(): Promise<{
  status: string;
  message: string;
  data: { job: JobType[] };
}> {
  // TODO: Fix to prefix url.
  return request(`${prefix}/anyone/schedule/pipeline/status`, {
    method: 'GET',
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function deleteJob(param: string): Promise<{ status: number }> {
  // TODO: Fix to prefix url.
  const js = JSON.stringify({ job: [param] });
  console.log(js);

  return request(`${prefix}/anyone/schedule/custom/cancel`, {
    method: 'POST',
    data: js,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function deleteJobPipeline(param: string): Promise<{ status: string }> {
  // TODO: Fix to prefix url.
  const js = JSON.stringify({ job: [param] });
  console.log(js);

  return request(`${prefix}/anyone/schedule/pipeline/cancel`, {
    method: 'POST',
    data: js,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}

export async function submitNewJobService(
  data: SubmitJobType,
  taskType: string,
): Promise<{ status: string; message: string; data: { uuid: string[] } }> {
  // TODO: Fix to prefix url.

  const js: string = JSON.stringify(data);
  console.log(js);
  let url: string = '';
  if (taskType === 'custom') {
    url = `${prefix}/anyone/schedule/custom/start`;
  } else {
    url = `${prefix}/anyone/schedule/pipeline/start`;
  }


  return request(url, {
    method: 'POST',
    data: js,
    dataType: 'json',
    headers: { 'Content-Type': 'application/json' },
  });
}
