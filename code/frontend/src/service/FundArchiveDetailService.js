import {getRequest} from "../utils/ajax";
import '../../config.js';
import {fetchPrefix} from "../../config";

export function getFundArchiveDetail(code, callback) {
    const url = fetchPrefix + '/anyone/archive/detail?code=' + code;
    getRequest(url, callback)
}
