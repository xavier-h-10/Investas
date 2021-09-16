import {fetchPrefix} from "../../config";
import {postRequest} from "../utils/ajax";

export function send_verification(data, callback) {
    const url = fetchPrefix + '/anyone/register/get_verification';
    postRequest(url, data, callback);
}

export function check_verification(data, callback) {
    const url = fetchPrefix + '/anyone/register/check_verification';
    postRequest(url, data, callback);
}

export function register(data, callback) {
    const url = fetchPrefix + '/anyone/register/register';
    postRequest(url, data, callback);
}
