package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FundDescription {
	@JSONField(name = "title")
	String title;
	@JSONField(name = "content")
	String content;

	public FundDescription(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
