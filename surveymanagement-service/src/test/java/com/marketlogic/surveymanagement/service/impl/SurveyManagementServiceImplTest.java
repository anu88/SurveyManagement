package com.marketlogic.surveymanagement.service.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.marketlogic.surveymanagement.dao.entity.Answer;
import com.marketlogic.surveymanagement.dao.entity.Question;
import com.marketlogic.surveymanagement.dao.entity.Survey;
import com.marketlogic.surveymanagement.dao.repository.SurveyRepository;
import com.marketlogic.surveymanagement.dto.AnswerDto;
import com.marketlogic.surveymanagement.dto.QuestionDto;
import com.marketlogic.surveymanagement.dto.SurveyDto;

public class SurveyManagementServiceImplTest {

	@InjectMocks
	private SurveyManagementServiceImpl surveyManagementService;

	@Mock
	private SurveyRepository surveyRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createSurvey_ValidRequest_SurveyCreated() {
		final SurveyDto surveyDto = getSurveyDto();
		final Survey survey = getSurvey();
		Mockito.when(surveyRepository.save(Mockito.any(Survey.class))).thenReturn(survey);
		final ArgumentCaptor<Survey> captor = ArgumentCaptor.forClass(Survey.class);

		Long surveyId = surveyManagementService.createSurvey(surveyDto);

		Assert.assertEquals(survey.getId(), surveyId);
		Mockito.verify(surveyRepository).save(captor.capture());
		final Survey expectedSurvey = captor.getValue();
		assertSurveyEquals(expectedSurvey, surveyDto);
	}

	@Test
	public void getSurvey_ValidId_SurveyFound() {
		final Survey survey = getSurvey();
		Mockito.when(surveyRepository.getOne(1L)).thenReturn(survey);
		SurveyDto surveyDto = surveyManagementService.getSurvey(1L);
		assertSurveyEquals(survey, surveyDto);
	}

	@Test
	public void getAllQuestionsOfSurvey_ValidRequest_QuestionsFound() {
		final Survey survey = getSurvey();
		Mockito.when(surveyRepository.getOne(1L)).thenReturn(survey);
		List<QuestionDto> questionDtos = surveyManagementService.getAllQuestionsOfSurvey(1L);
		Assert.assertEquals(questionDtos.size(), 1);
	}
	
	@Test
	public void getQuestionOfSurvey_ValidRequest_QuestionFound() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void getQuestionOfSurvey_QuestionNotExists_ExceptionThrown() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void updateSurvey_ValidRequest_SurveyUpdated() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void updateSurvey_SurveyAlreadyVoted_ExceptionThrown() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void voteForSurvey_ValidSurveyVote_votesUpdated() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void voteForSurvey_InvalidAnswerMapping_ExceptionThrown() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void voteForSurvey_AllQuestionNotVoted_ExceptionThrown() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}
	
	@Test
	public void voteForSurvey_SameQuestionVotedTwice_ExceptionThrown() {
		Assert.assertTrue(true);
		//TODO: Pending implementation
	}

	private void assertSurveyEquals(final Survey survey, final SurveyDto surveyDto) {
		Assert.assertEquals(survey.getName(), surveyDto.getName());
		Assert.assertEquals(survey.getQuestions().size(), surveyDto.getQuestions().size());
		assertQuestionEquals(survey.getQuestions().get(0), surveyDto.getQuestions().get(0));
	}

	private void assertQuestionEquals(final Question question, final QuestionDto questionDto) {
		Assert.assertEquals(question.getDescription(), questionDto.getDescription());
		Assert.assertEquals(question.getAnswers().size(), questionDto.getAnswers().size());
		assertAnswerEquals(question.getAnswers().get(0), questionDto.getAnswers().get(0));
	}

	private void assertAnswerEquals(final Answer answer, final AnswerDto answerDto) {
		Assert.assertEquals(answer.getDescription(), answerDto.getDescription());
	}

	private SurveyDto getSurveyDto() {
		AnswerDto answerDto = new AnswerDto();
		answerDto.setDescription("answerDescription");

		QuestionDto questionDto = new QuestionDto();
		questionDto.setDescription("questionDescription");
		questionDto.setAnswers(Lists.newArrayList(answerDto));

		SurveyDto surveyDto = new SurveyDto();
		surveyDto.setName("surveyName");
		surveyDto.setQuestions(Lists.newArrayList(questionDto));
		return surveyDto;
	}

	private Survey getSurvey() {
		Answer answer = new Answer();
		answer.setDescription("answerDescription");
		answer.setId(3L);
		answer.setVoteCount(1L);

		Question question = new Question();
		question.setDescription("questionDescription");
		question.setAnswers(Lists.newArrayList(answer));
		question.setId(2L);
		answer.setQuestion(question);

		Survey survey = new Survey();
		survey.setName("surveyName");
		survey.setQuestions(Lists.newArrayList(question));
		survey.setId(1L);
		survey.setVoteCount(1L);
		question.setSurvey(survey);

		return survey;
	}
}
