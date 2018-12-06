package com.marketlogic.surveymanagement.dto;

import javax.validation.constraints.NotNull;

public class SurveyResponseDto {

	@NotNull(message = "Please enter valid question Id")
	private Long questionId;
	
	@NotNull(message = "Please enter valid answer Id")
	private Long answerId;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

}
