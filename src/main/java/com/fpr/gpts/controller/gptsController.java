package com.fpr.gpts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpr.gpts.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpClient;

@Controller
@RequestMapping("/gpts")
@RequiredArgsConstructor
public class gptsController {

    private final GptService gptService;
    @GetMapping("/financialKnowledge")
    public ResponseEntity<String> financialKnowledge() throws JsonProcessingException {
        return ResponseEntity.ok(gptService.getKnowledge());
    }
    @GetMapping("/financialGoal")
    public String financialGoal() {
        return "financialGoal";
    }
}
