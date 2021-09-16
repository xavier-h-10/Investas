import {fetchPrefix} from "../../config";
import {getRequest, postRequest} from "../utils/ajax";

export const getFundPredictions=(callback)=>{
    const url=fetchPrefix + "/anyone/prediction/PredictionRanking";
    postRequest(url, {}, callback);
}
