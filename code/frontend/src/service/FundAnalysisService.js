import {getRequest, postRequest} from "../utils/ajax";
import {fetchPrefix} from "../../config";

export function getFundAnalysis(id, callback) {
  let code = id.toString();
  let s = 6 - code.length;
  for (let i = 1; i <= s; i++) {
    code = "0" + code;
  }
  let deviceId = '41ecc';
  const url = 'https://fundmobapi.eastmoney.com/FundMNewApi/FundMNUniqueInfo?product=EFund&appVersion=6.4.6&serverVersion=6.4.6&FCODE='
      + code + '&deviceid=' + deviceId
      + '&version=6.4.6&userId=uid&cToken=ctoken&MobileKey=' + deviceId
      + '&OSVersion=10&plat=Android&uToken=utoken&passportid=1234567890';
  getRequest(url, callback);
}

export function getFundIndicatorNumber(fundCode, callback) {
  const url = fetchPrefix + '/anyone/getFundIndicatorNumber?fundCode='
      + fundCode;
  getRequest(url, callback);
}

export function getFundIndicator(fundCode, callback) {
  const url = fetchPrefix + '/anyone/getFundIndicator?fundCode=' + fundCode;
  getRequest(url, callback);
}

export function getHomeIndicator(sharp, maxRet, stdDev, profit, page, size, callback) {
  const url = `${fetchPrefix}/anyone/fund/indicator/rank?sharp=${sharp}&maxRet=${maxRet}&stdDev=${stdDev}&profit=${profit}&page=${page}&size=${size}`
  getRequest(url, callback)
}
