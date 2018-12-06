# SurveyManagement

## Contents

- 1. About
   - 1. Create Survey
   - 2. Update Survey
   - 3. Get All Question of a Survey
   - 4. Get a Single Question
   - 5. Respond to a Survey
   - 6. Get Survey Result
- 2. How to build the application
- 3. How to run the application
- 4. Areas of improvement


## 1. About

```
Survey Management App is a web application for creating new Survey and recording the response. A detailed description of API's available is as follow:
```
### 1. Create Survey

```
- This API will allow user to create a new survey.
- REST API URL & sample request JSON:
POST http://localhost:8082/surveymanagement/survey
{
"name": "Job Survey 1",
"questions": [
{
"description": "Question 1",
"answers": [
{"description": "Answer 1"},
{"description": "Answer 2"},
{"description": "Answer 3"}
]
},
{
"description": "Question 2",
"answers": [
{"description": "Answer 1"},
{"description": "Answer 2"},
{"description": "Answer 3"}
]
}
]
}
```
```
Validations:
- Name is mandatory.
- A Survey should have at least one Question.
- A Question should have at least one Answer.
```
### 2. Update Survey

```
- This API will allow user to update the question/answers of a survey, if the survey has
not been voted so far.
- REST API URL & Sample resquest JSON:
PUT http://localhost:8082/surveymanagement/survey/{surveyId}

{

"name": "Job Survey 1",
"questions": [
{
"description": "Question 1",
"answers": [
{"description": "Answer 1"},
{"description": "Answer 2"},
{"description": "Answer 3"}
]
},
{
"description": "Question 2",
"answers": [
{"description": "Answer 1"},
{"description": "Answer 2"},
{"description": "Answer 3"}
]
}
]
}
```
### 3. Get All Question of a Survey

```
- This API will retrieve all the questions of a survey.
- API URL & sample response JSON:
GET http://localhost:8082/surveymanagement/survey/{surveyId}/question
[
{
"id": 1,
"description": "Question 1"
},
{
"id": 2,
"description": "Question 2"
}
]
```
```
Validations :
- Survey Id should be valid.
```
### 4. Get a Single Question

```
- This API will allow user to retrieve a single question and its answers.
- REST API URL & sample Response JSON:
GET http://localhost:8082/surveymanagement/survey/{surveyId}/question/{questionId}

 {

"id": 2,
"description": "Question 1",
"answers": [
{
"id": 3,
"description": "Answer 1",
"votePercentage": "100.00"
}
]
}
```
```
Validations :
- Survey Id and Question Id should exist in database.
```
### 5. Respond to a Survey

```
- This API will allow user to respond to a survey.
- API URL & sample request JSON:
POST http://localhost:8082/surveymanagement/survey/{surveyId}/vote
[
{
"questionId": 2,
"answerId": 3
},
{
"questionId": 6,
"answerId": 7
}
]
```
```
Validations :
- Survey Id, Question Id and Answer Id are mandatory.
- Question Id should be mapped with Survey Id in URL
- Answer Id should be mapped with Question Id in response.
```
### 6. Get Survey Result

```
- This API will allow user to retrieve survey by its ID and see the total votes casted and
votes distribution per Answer of each question.
- API URL & sample response JSON:
GET http://localhost:8082/surveymanagement/survey/{surveyId}

 {

"id": 1,
"name": "Job Survey 1",
"voteCount": 9,
"questions": [
{
"id": 2,
"description": "Question 1",
"answers": [
{
"id": 3,
"description": "Answer 1",
"votePercentage": "67.67"
},
{
"id": 4,
"description": "Answer 2",
"votePercentage": "33.33"
}
]
},
{
"id": 6,
"description": "Question 2",
"answers": [
{
"id": 7,
"description": "Answer 1",
"votePercentage": "50.00"
},
{
"id": 8,
"description": "Answer 2",
"votePercentage": "50.00"
}
]
}
]
}
```
```
**Validations** :
- Survey Id is mandatory.
```

## 2. How to build the application

```
JDK 8 and maven should be installed on your machine. For installing JDK 8 and maven please follow the hyperlinks. For building the application please follow the instructions as below:
```
```
- Unzip the application and Navigate to folder ‘... \surveymanagement ’ in command
prompt.
- Run ‘mvn clean install’ for building the application
- After successful installation, it will create a target folder.
Please follow the instructions in next section to run the application.
```
## 3. How to run the application

```
- Unzip the application. Navigate to the folder
‘ ...\surveymanagement\surveymanagement-main\target ’ in command prompt.

- Run the command "java -jar surveymanagement-main-0.0.1-SNAPSHOT.jar" which will start the application.

- Open the URL: http://localhost:8082/surveymanagement/swagger-ui.html#/

- Navigate to the ‘ Creates a new Survey ’ API in ‘ survey-management-controller ’. Paste the request object as mentioned in ‘Create Survey’ and click on 'Try it out!' 

Above request will create a new Survey.

- Similarly, please try out the other API’s. For accessing the DB while application is running please open the URL and check the JDBC URL as ‘jdbc:h2:mem:testdb’ and click on ‘Connect’.
Please note as this application runs on ‘in memory’ database, application restart will recreate the complete database and any persisted state will cease to exist.
```
## 4. Areas of improvement

```
Below is some of the improvement area across the application.
```
1. DB test and Integration tests should be added.
2. Exception handling can be improved further by creating more custom exception classes.
3. Configuration file - application.yml can be made environment specific.
