package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FundCompeRank {
    @JSONField(name = "rank")
    Integer rank;
    @JSONField(name="rate")
    Double rate;
    @JSONField(name="nickname")
    String nickname;

    public FundCompeRank(){}

    public FundCompeRank(Integer rank,Double rate,String nickname){
        this.rank=rank;
        this.rate=rate;
        this.nickname=nickname;
    }
}
