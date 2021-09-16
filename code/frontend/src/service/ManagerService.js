import {getRequest} from "../utils/ajax";
import '../../config.js';
import {fetchPrefix} from "../../config";

export function getManager(id, callback) {
    const url = fetchPrefix + '/anyone/manager?id='+id;
    getRequest(url, callback)
}
