import {fetchPrefix} from "../../config";
import {getRequest} from "../utils/ajax";

export const getFundPortfolio=(fundCode,callback)=>{
    const url=fetchPrefix+"/anyone/fund/portfolio?fundCode="+fundCode;
    getRequest(url,callback);
}
