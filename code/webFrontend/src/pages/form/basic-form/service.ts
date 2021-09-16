import request from '@/services/Request';
import { prefix } from '@/services/config';

export async function fakeSubmitForm(params: any) {
  return request('/api/basicForm', {
    method: 'POST',
    data: params,
  });
}

export async function createCompetition(params: any) {
  const data: string = JSON.stringify(params);
  // eslint-disable-next-line no-console
  console.log(data);
  return request <API.Message> (
    (`${prefix}/user/competition/createCompetition`),
    {
      method: 'POST',
      data,
      dataType: 'json',
      headers: {'Content-Type': 'application/json'}
    });
}
