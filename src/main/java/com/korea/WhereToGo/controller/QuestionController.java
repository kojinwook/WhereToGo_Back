package com.korea.WhereToGo.controller;


import com.korea.WhereToGo.dto.request.Question.PatchQuestionRequestDto;
import com.korea.WhereToGo.dto.request.Question.PostQuestionRequestDto;
import com.korea.WhereToGo.dto.response.Question.*;
import com.korea.WhereToGo.service.QuestionService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<? super PostQuestionResponseDto> postQuestion(
            @RequestBody @Valid PostQuestionRequestDto requestBody
            ){ResponseEntity<? super PostQuestionResponseDto> response = questionService.postQuestion(requestBody);
            return response;
    }
    @GetMapping("/list")
    public ResponseEntity<? super GetAllQuestionResponseDto> getAllQuestion(){
        ResponseEntity<? super GetAllQuestionResponseDto> response = questionService.getAllQuestions();
        return response;
    }
    @GetMapping("/detail/{questionId}")
    public ResponseEntity<? super GetQuestionResponseDto> getQuestion(
            @PathVariable("questionId") Long questionId
    ){
        ResponseEntity<? super GetQuestionResponseDto> response = questionService.getQuestion(questionId);
        return response;
    }
    @PatchMapping("/update/{questionId}")
    public ResponseEntity<? super PatchQuestionResponseDto> patchQuestion(
            @RequestBody @Valid PatchQuestionRequestDto requestBody,
            @PathVariable("questionId") Long questionId){
        ResponseEntity<? super PatchQuestionResponseDto> response = questionService.patchQuestion(requestBody, questionId);
    return response;
    }
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<? super DeleteQuestionResponseDto> deleteQuestion(
            @PathVariable("questionId") Long questionId,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super DeleteQuestionResponseDto> response = questionService.deleteQuestion(questionId);
        return response;
    }

}