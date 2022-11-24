package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name="fund_message")
public class FundMessage {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="message_id")
    private BigInteger messageId;

    @NotNull
    @Column(name="message_timestamp")
    @CreationTimestamp
    private Timestamp messageTimestamp;

    @Column(name="message_type")
    @ColumnDefault("0")
    private Integer messageType;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="message_title",length=100)
    private String messageTitle;

    @NotNull
    @Column(name="message_content",length=20000)
    private String messageContent;

}
