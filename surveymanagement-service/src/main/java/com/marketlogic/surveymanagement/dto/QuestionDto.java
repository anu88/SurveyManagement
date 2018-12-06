package com.marketlogic.surveymanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketlogic.surveymanagement.dao.entity.Answer;
import com.marketlogic.surveymanagement.dao.entity.Question;

public class QuestionDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotBlank(message = "Please enter valid description of question")
	private String description;

	@NotEmpty(message = "Please add at least one valid answer for a question")
	@JsonInclude(value = Include.NON_NULL)
	@Valid
	private List<AnswerDto> answers;
	
	public QuestionDto() {
	}

	public QuestionDto(final Long id, final String description) {
		this.id = id;
		this.description = description;
	}

	public QuestionDto(final Question question) {
		this(question.getId(), question.getDescription());
		this.answers = question.getAnswers().stream()
				.map(answer -> getAnswerDtoWithVotePercentage(question.getSurvey().getVoteCount(), answer))
				.collect(Collectors.toList());
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

	public List<AnswerDto> getAnswers() {
		return answers;
	}

	public void setAnswers(final List<AnswerDto> answers) {
		this.answers = answers;
	}

	public Question toEntity() {
		final Question question = new Question();
		question.setId(this.id);
		question.setDescription(this.description);
		question.setAnswers(this.answers.stream().map(answerDto -> answerDto.toEntity()).collect(Collectors.toList()));
		return question;
	}

	private AnswerDto getAnswerDtoWithVotePercentage(final long surveyVotes, final Answer answer) {
		float votePercentage = surveyVotes != 0 ? (answer.getVoteCount() * 100.0f) / surveyVotes : 0.0f;
		final AnswerDto answerDto = new AnswerDto(answer);
		answerDto.setVotePercentage(String.format("%.2f", votePercentage));
		return answerDto;
	}

}
