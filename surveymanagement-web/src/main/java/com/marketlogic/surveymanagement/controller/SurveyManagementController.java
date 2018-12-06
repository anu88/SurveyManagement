package com.marketlogic.surveymanagement.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marketlogic.surveymanagement.dto.QuestionDto;
import com.marketlogic.surveymanagement.dto.SurveyDto;
import com.marketlogic.surveymanagement.dto.SurveyResponseDto;
import com.marketlogic.surveymanagement.exception.ValidationException;
import com.marketlogic.surveymanagement.service.SurveyManagementService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/survey")
public class SurveyManagementController {

	@Autowired
	private SurveyManagementService surveyMangementService;

	@PostMapping
	@ApiOperation(value = "Creates a new Survey")
	public ResponseEntity<Void> createSurvey(@RequestBody @Valid final SurveyDto surveyDto) {
		final Long surveyId = surveyMangementService.createSurvey(surveyDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(surveyId).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{surveyId}/question")
	@ApiOperation(value = "Get all the questions of a Survey")
	public ResponseEntity<List<QuestionDto>> getAllQuestionOfSurvey(@PathVariable @NotNull @Valid final Long surveyId) {
		return new ResponseEntity<List<QuestionDto>>(surveyMangementService.getAllQuestionsOfSurvey(surveyId),
				HttpStatus.OK);
	}

	@GetMapping("/{surveyId}/question/{questionId}")
	@ApiOperation(value = "Gets a single question and it's answers of a Survey")
	public ResponseEntity<QuestionDto> getQuestionOfSurvey(@PathVariable @NotNull @Valid final Long surveyId,
			@PathVariable @NotNull @Valid final Long questionId) {
		return new ResponseEntity<QuestionDto>(surveyMangementService.getQuestionOfSurvey(surveyId, questionId),
				HttpStatus.OK);
	}

	@PutMapping("/{surveyId}")
	@ApiOperation(value = "Updates question/answers of a Survey, if not already VOTED")
	public ResponseEntity<Void> updateSurvey(@PathVariable @NotNull @Valid final Long surveyId,
			@RequestBody @Valid final SurveyDto surveyDto) throws ValidationException {
		surveyDto.setId(surveyId);
		surveyMangementService.updateSurvey(surveyDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{surveyId}/vote")
	@ApiOperation(value = "Records the response to a Survey")
	public ResponseEntity<Void> voteForSurvey(@PathVariable @NotNull @Valid final Long surveyId,
			@RequestBody @Valid @NotEmpty final List<SurveyResponseDto> surveyResponse) throws ValidationException {
		surveyMangementService.voteForSurvey(surveyId, surveyResponse);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{surveyId}")
	@ApiOperation(value = "Gets a Survey along with it's result")
	public ResponseEntity<SurveyDto> getSurvey(@PathVariable @NotNull @Valid final Long surveyId) {
		return new ResponseEntity<SurveyDto>(surveyMangementService.getSurvey(surveyId), HttpStatus.OK);
	}

}
