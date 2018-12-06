package com.marketlogic.surveymanagement.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketlogic.surveymanagement.dao.entity.Answer;

public class AnswerDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotBlank(message = "Please enter valid description of answer")
	private String description;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String votePercentage;

	public AnswerDto() {
	}

	public AnswerDto(final Answer answer) {
		this.id = answer.getId();
		this.description = answer.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getVotePercentage() {
		return votePercentage;
	}

	public void setVotePercentage(final String votePercentage) {
		this.votePercentage = votePercentage;
	}

	public Answer toEntity() {
		final Answer answer = new Answer();
		answer.setId(this.id);
		answer.setDescription(this.description);
		return answer;
	}

}
