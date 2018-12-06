package com.marketlogic.surveymanagement.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketlogic.surveymanagement.dao.entity.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
