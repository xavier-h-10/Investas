// @ts-ignore
import request from './Request';

export async function queryRule(params: any) {
  const data: string = JSON.stringify(params);
  return request(
    '/fgemm/fundHaltTrading/getHaltTradingList',
    {
    method: 'POST',
    data,
    dataType: 'json',
    headers: {'Content-Type': 'application/json'}
  });
}
