import {fetchPrefix} from "../../config";
import {postRequest, postFile, getRequest} from "../utils/ajax";

export function getUserInfo(callback) {
    const url = fetchPrefix + '/user/getUserInfo';
    postRequest(url, {}, callback);
}

export function upload(fileUrl, callback) {
    const url = fetchPrefix + '/user/upload';
    postFile(url, fileUrl, callback);
}

//map转object  再object转json
export function updateUserInfo(map, callback) {
    const url = fetchPrefix + '/user/updateUserInfo';
    let obj = Object.create(null);
    for (let [k, v] of map) {
        obj[k] = v;
    }
    postRequest(url, obj, callback);
}

//获取评测问卷
export function getRiskAssessment(callback) {
    const url = fetchPrefix + '/anyone/riskAssessment';
    getRequest(url, callback);
}

//忘记密码 获取验证码
export function getVerification(map, callback) {
    const url = fetchPrefix + '/anyone/forget/getVerification';
    let obj = Object.create(null);
    for (let [k, v] of map) {
        obj[k] = v;
    }
    postRequest(url, obj, callback);
}

//验证码比对
export function checkAuth(map, callback) {
    const url = fetchPrefix + '/anyone/forget/checkAuth';
    let obj = Object.create(null);
    for (let [k, v] of map) {
        obj[k] = v;
    }
    postRequest(url, obj, callback);
}

//获取验证码后修改密码
export function changePassword(map, callback) {
    const url = fetchPrefix + '/anyone/forget/changePassword';
    let obj = Object.create(null);
    for (let [k, v] of map) {
        obj[k] = v;
    }
    postRequest(url, obj, callback);
}
