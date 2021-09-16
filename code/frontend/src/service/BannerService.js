import {getRequest} from "../utils/ajax";

export function getBannerAddress(callback) {
  const url = 'https://m.fullgoal.com.cn/fgEmmIn/store/getBannerList/fqb?ver=2.0';
  getRequest(url,callback);
}
