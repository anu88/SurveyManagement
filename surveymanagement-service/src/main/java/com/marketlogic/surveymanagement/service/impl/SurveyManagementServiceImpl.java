package com.marketlogic.surveymanagement.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketlogic.surveymanagement.dao.entity.Answer;
import com.marketlogic.surveymanagement.dao.entity.Question;
import com.marketlogic.surveymanagement.dao.entity.Survey;
import com.marketlogic.surveymanagement.dao.repository.SurveyRepository;
import com.marketlogic.surveymanagement.dto.QuestionDto;
import com.marketlogic.surveymanagement.dto.SurveyDto;
import com.marketlogic.surveymanagement.dto.SurveyResponseDto;
import com.marketlogic.surveymanagement.exception.BaseException;
import com.marketlogic.surveymanagement.exception.ValidationException;
import com.marketlogic.surveymanagement.service.SurveyManagementService;

@Service
public class SurveyManagementServiceImpl implements SurveyManagementService {

	@Autowired
	private SurveyRepository surveyRepository;

	@Override
	@Transactional
	public Long createSurvey(final SurveyDto surveyDto) {
		return saveOrUpdateEntity(surveyDto).getId();
	}

	@Override
	public SurveyDto getSurvey(final Long surveyId) {
		return new SurveyDto(surveyRepository.getOne(surveyId));
	}

	@Override
	public List<QuestionDto> getAllQuestionsOfSurvey(final Long surveyId) {
		return surveyRepository.getOne(surveyId).getQuestions().stream()
				.map(question -> new QuestionDto(question.getId(), question.getDescription()))
				.collect(Collectors.toList());
	}

	@Override
	public QuestionDto getQuestionOfSurvey(final Long surveyId, final Long questionId) {
		final Question question = surveyRepository.getOne(surveyId).getQuestions().stream()
				.filter(ques -> ques.getId().equals(questionId)).findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Question doesn't exist."));
		return new QuestionDto(question);
	}

	@Override
	@Transactional
	public void updateSurvey(final SurveyDto surveyDto) throws ValidationException {
		if (surveyRepository.getOne(surveyDto.getId()).getVoteCount() == 0) {
			saveOrUpdateEntity(surveyDto);
		} else {
			throw new ValidationException("update.not.allowed",
					MessageFormat.format("Survey with id: {0} has already been voted.", surveyDto.getId()));
		}

	}

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public void voteForSurvey(final Long surveyId, final List<SurveyResponseDto> surveyResponse)
			throws ValidationException {
		final Survey survey = surveyRepository.getOne(surveyId);
		final Map<Long, Long> questionAnswerVoteMap = getQuestionAnswerVoteMap(surveyResponse);
		if (questionAnswerVoteMap.size() == survey.getQuestions().size()) {
			survey.addVote();
			for (final Question question : survey.getQuestions()) {
				final Long answerId = questionAnswerVoteMap.get(question.getId());
				try {
					final Answer answer = question.getAnswers().stream().filter(ans -> ans.getId().equals(answerId))
							.findFirst()
							.orElseThrow(() -> new ValidationException("invalid.vote",
									MessageFormat.format("Answer with id: {0} is invalid for question with id: {1}",
											answerId, question.getId())));
					answer.addVote();
				} catch (ValidationException e) {
					throw e;
				}
			}
		} else {
			throw new ValidationException("all.question.mandatory",
					MessageFormat.format("All questions are not answered for survey with id : {0}", surveyId));
		}
	}

	private Map<Long, Long> getQuestionAnswerVoteMap(final List<SurveyResponseDto> surveyResponse)
			throws ValidationException {
		try {
			return surveyResponse.stream()
					.collect(Collectors.toMap(SurveyResponseDto::getQuestionId, SurveyResponseDto::getAnswerId));
		} catch (final IllegalStateException ex) {
			throw new ValidationException("invalid.survey.response", "Invalid survey response");
		}
	}

	private Survey saveOrUpdateEntity(final SurveyDto surveyDto) {
		final Survey survey = surveyDto.toEntity();
		survey.getQuestions().forEach(question -> updateEntityRelationMapping(survey, question));
		return surveyRepository.save(survey);
	}

	private void updateEntityRelationMapping(final Survey survey, final Question question) {
		question.setSurvey(survey);
		question.getAnswers().forEach(answer -> answer.setQuestion(question));
	}
}
