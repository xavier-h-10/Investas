import {postRequest,getRequest} from "../utils/ajax";
import '../../config.js';
import {fetchPrefix} from "../../config";


export function login(data, callback) {
    const url = fetchPrefix + 'login';
    postRequest(url, data, callback);
}

export const getSimpFundInfo=(searchStr,pageIdx,callback)=>{
    const url = fetchPrefix + '/anyone/search/fund/simplified?searchStr='+searchStr+'&pageIdx='+pageIdx.toString();
    getRequest(url,callback);
}

export function getFundPrice(data,callback) {
    const url='https://uni-fundts.1234567.com.cn/dataapi/fund/FundVPageAcc?INDEXCODE=&CODE=004314&FCODE=004314&RANGE=n&CustomerNo=&UserId=&Uid=&CToken=&UToken=&MobileKey=41ecc8babe2fb37ca075b6a863a59ddb%7C%7Ciemi_tluafed_me&zone=1&DATES=&POINTCOUNT=&deviceid=5C1164BA-3D4A-4953-A488-00E47222D4BB&plat=Iphone&AppType=Iphone&product=EFund&version=6.2.5&Serverversion=6.2.5&appversion=6.2.5';
    postRequest(url,data,callback);
}

export function getFundView(code, callback) {
    const url = fetchPrefix + '/anyone/fund/view?code=' + code;
    getRequest(url, callback)
}

export function getFundStock(code, callback) {
    const url = fetchPrefix + '/anyone/fund/stock?code=' + code;
    getRequest(url, callback)
}

//2-近1月 3-近3月 4-近6月 5-近1年 7-近3年
export function getFundNAV(code, type, callback) {
    const url = fetchPrefix + `/anyone/fund/dailyinfo?fundCode=${code}&timeType=${type}`
    getRequest(url, callback)
}

export function getFundNAVPage(code, page, callback) {
    const url = fetchPrefix + `/anyone/fund/daily/info?fundCode=${code}&page=${page}`
    getRequest(url, callback)
}

// TODO:此处为api,需要改成从数据库中调数据
export function getFundEstimate(code,callback) {
    const url='http://fundmobapi.eastmoney.com/FundMApi/FundVarietieValuationDetail.ashx?FCODE='+code+'&CustomerNo=&UserId=&Uid=&CToken=&UToken=&MobileKey=41ecc8babe2fb37ca075b6a863a59ddb%7C%7Ciemi_tluafed_me&zone=1&DATES=&POINTCOUNT=&deviceid=5C1164BA-3D4A-4953-A488-00E47222D4BB&plat=Iphone&AppType=Iphone&product=EFund&version=6.2.5&Serverversion=6.2.5&appversion=6.2.5';
    console.log(url);
    getRequest(url,callback);
}

export function getFundPrediction(code, callback) {
    const url = `${fetchPrefix}/anyone/fund/prediction?code=${code}`
    getRequest(url, callback)
}

// get stock value
export function getStockValue(callback) {
    const url=`https://push2.eastmoney.com/api/qt/ulist.np/get?product=EFund&appVersion=10&ServerVersion=6.4.7&igggggnoreburst=true&deviceid=41ecc8babe2fb37ca075b6a863a59ddb%7C%7Ciemi_tluafed_me&version=10&DeviceOS=Android10&ctoken=unf6r1jquf616eudk8hqqnfrh1qkjr1n.13&marketchannel=HuaweiApps&MobileKey=41ecc8babe2fb37ca075b6a863a59ddb%7C%7Ciemi_tluafed_me&Version=6.4.7&fltt=2&UserId=5b2b98f0cb244d359777e707cb44fb3c&UserAgent=android&utoken=6-rdcqnqru6ujkhh1jrrudeurkkdfnrj10c9uhcc.13&plat=Iphone&fields=f1%2Cf2%2Cf3%2Cf4%2Cf12%2Cf13%2Cf14&secids=1.000001%2C0.399001%2C0.399006%2C1.000300%2C0.399005%2C1.000016&passportid=4203336295982002&customerNo=5b2b98f0cb244d359777e707cb44fb3c`;
    getRequest(url, callback);
}
