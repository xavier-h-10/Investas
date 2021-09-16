import {fetchPrefix} from "../../config";
import {getRequest, postRequest} from "../utils/ajax";

export function putPositionFund(data, callback) {
    const url = fetchPrefix + '/user/position/setByAmount';
    postRequest(url, data, callback);
}

export function getPositionFund(callback) {
    const url = fetchPrefix + '/user/position/getByUser';
    postRequest(url, {}, callback)
}

export function checkPosition(data, callback) {
    const url = fetchPrefix + '/user/position/check';
    postRequest(url, data, callback)
}

export function delPosition(data, callback) {
    console.log('delPosition:', data)
    const url = fetchPrefix + '/user/position/del';
    postRequest(url, data, callback)
}