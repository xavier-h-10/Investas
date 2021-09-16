import {postRequest} from "../utils/ajax";
import {fetchPrefix} from "../../config";

export function login(data, callback) {
  const url = fetchPrefix + '/doLogin';
  postRequest(url, data, callback);
}

export function logout(callback) {
  const url = fetchPrefix + '/logout';
  postRequest(url,{},callback);
}
