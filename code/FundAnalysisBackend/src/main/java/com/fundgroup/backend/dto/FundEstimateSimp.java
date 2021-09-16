package com.fundgroup.backend.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class FundEstimateSimp {
    LocalDateTime estimateTimestamp;
    Double NAVEstimate;
    Double increaseEstimate;

    public FundEstimateSimp(){}

    public FundEstimateSimp(LocalDateTime estimateTimestamp,Double NAVEstimate,Double increaseEstimate)
    {
        this.estimateTimestamp=estimateTimestamp;
        this.NAVEstimate=NAVEstimate;
        this.increaseEstimate=increaseEstimate;
    }

}
