package com.fundgroup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundgroup.backend.dto.FundCompeUserSimp;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "fund_competition_user_position_log")
public class FundCompeUserPosLog {
	@Id
	@NotNull
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	Long id;

	@NotNull
	@Column(name="fund_code",length=6)
	private String fundCode;

	@NotNull
	@Column(name="date")
	LocalDate date;

	@NotNull
	@ColumnDefault("0")
	@Column(name="amount")
	BigDecimal amount;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fund_competition_user_id")
	private FundCompeUser fundCompeUser;

	public FundCompeUserSimp getFundCompeUser() {
		return new FundCompeUserSimp(fundCompeUser);
	}
}
