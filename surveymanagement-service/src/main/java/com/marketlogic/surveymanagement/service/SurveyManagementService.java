package com.marketlogic.surveymanagement.service;

import java.util.List;

import com.marketlogic.surveymanagement.dto.QuestionDto;
import com.marketlogic.surveymanagement.dto.SurveyDto;
import com.marketlogic.surveymanagement.dto.SurveyResponseDto;
import com.marketlogic.surveymanagement.exception.ValidationException;

public interface SurveyManagementService {

	Long createSurvey(SurveyDto surveyDto);

	List<QuestionDto> getAllQuestionsOfSurvey(Long surveyId);

	QuestionDto getQuestionOfSurvey(Long surveyId, Long questionId);

	void updateSurvey(SurveyDto surveyDto) throws ValidationException;

	void voteForSurvey(Long surveyId, List<SurveyResponseDto> surveyResponse) throws ValidationException;

	SurveyDto getSurvey(Long surveyId);

}
