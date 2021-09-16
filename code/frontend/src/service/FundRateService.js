import {postRequest,getRequest} from "../utils/ajax";
import '../../config.js';
import {fetchPrefix} from "../../config";

export function getFundRankings(type, callback) {
    const url = fetchPrefix + '/anyone/fund/recent-rate';
    const data = {
        'type': type,
    }
    postRequest(url, data, callback);
}

export function getHistoryRank(page, size, type, callback) {
    const url = fetchPrefix + `/anyone/fund/rank/history?page=${page}&size=${size}&type=${type}`
    getRequest(url, callback)
}

export function getPredictionRank(page, size, type, callback) {
    const url = fetchPrefix + `/anyone/fund/rank/prediction?page=${page}&size=${size}&type=${type}`
    getRequest(url, callback)
}
