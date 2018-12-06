package com.marketlogic.surveymanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketlogic.surveymanagement.dao.entity.Survey;

public class SurveyDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotBlank(message = "Please enter valid survey name")
	private String name;

	@NotEmpty(message = "Please add at least one valid survey question")
	@Valid
	private List<QuestionDto> questions;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long voteCount;

	public SurveyDto() {
	}

	public SurveyDto(final Survey survey) {
		this.id = survey.getId();
		this.name = survey.getName();
		this.questions = survey.getQuestions().stream().map(QuestionDto::new).collect(Collectors.toList());
		this.voteCount = survey.getVoteCount();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setQuestions(final List<QuestionDto> questions) {
		this.questions = questions;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(final Long voteCount) {
		this.voteCount = voteCount;
	}

	public Survey toEntity() {
		final Survey survey = new Survey();
		survey.setId(this.id);
		survey.setName(this.name);
		survey.setQuestions(
				this.questions.stream().map(questionDto -> questionDto.toEntity()).collect(Collectors.toList()));
		return survey;
	}

}
