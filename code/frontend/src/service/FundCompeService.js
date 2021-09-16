import {fetchPrefix} from "../../config";
import {getRequest, postRequest} from "../utils/ajax";

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function getUserCompeHold(competitionId, callback){
    console.log(competitionId)
    const url = fetchPrefix + '/user/competition/hold?competitionId='+competitionId;
    getRequest(url, callback);
}

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function getFundCompeRank(competitionId, callback){
    console.log(competitionId)
    const url = fetchPrefix + '/user/competition/rank?competitionId='+competitionId;
    getRequest(url, callback);
}

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function getFundCompeDetail(competitionId, callback){
    console.log(competitionId)
    const url = fetchPrefix + '/user/competition/detail?competitionId='+competitionId;
    getRequest(url, callback);
}

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function getFundCompeSimple(competitionId, callback){
    console.log(competitionId)
    const url = fetchPrefix + '/user/competition/simple?competitionId='+competitionId;
    getRequest(url, callback);
}

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function getFundCompeList(callback){
    const url = fetchPrefix + '/user/competition/active/public/detail';
    getRequest(url, callback);
}

export function getMyFundCompeList(callback){
    const url = fetchPrefix + '/user/competition/active/public/detail/my';
    getRequest(url, callback);
}

/**
 * TODO:modify to user only
 * @param data
 * @param callback
 */
export function postJoinCompetition(data,callback){
    const url = fetchPrefix + '/user/competition/join';
    postRequest(url,data,callback);
}

export function getActiveCompetitionsByFundType(type, callback) {
    const url = `${fetchPrefix}/user/competition/getActiveCompetitionsByUserAndFundType`;
    postRequest(url, {"type": type}, callback)
}

export function buyFund(competitionId, fundCode, amount, callback) {
    const url = `${fetchPrefix}/user/competition/buyFund`;
    postRequest(url, {
        "competitionId": competitionId,
        "fundCode": fundCode,
        "amount": amount,
    }, callback)
}

export function getLogs(competitionId, fundCode, callback) {
    const url = `${fetchPrefix}/user/competition/log/fund/byCodeAndCompeIdAndUserId`;
    postRequest(url, {
        "competitionId": competitionId,
        "fundCode": fundCode,
    }, callback)
}

export function getUserPos(competitionId, fundCode, callback) {
    const url = `${fetchPrefix}/user/competition/getFundCompeUserPosByCodeAndCompeId`;
    postRequest(url, {
        "competitionId": competitionId,
        "fundCode": fundCode,
    }, callback)
}