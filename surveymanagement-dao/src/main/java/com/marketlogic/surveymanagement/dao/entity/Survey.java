package com.marketlogic.surveymanagement.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "survey")
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private long voteCount;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

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

	public long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(final long voteCount) {
		this.voteCount = voteCount;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(final List<Question> questions) {
		this.questions.addAll(questions);
	}

	@Override
	public boolean equals(final Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public void addVote() {
		this.voteCount++;
	}
}
