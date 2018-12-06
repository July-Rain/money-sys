package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.PracticePaper;
import com.lawschool.service.PracticePaperService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("/PracticePaper")
public class PracticePaperController extends AbstractController {
    @Autowired
    PracticePaperService practicePaperService;

    @RequestMapping("/listAll")
    public Result listAll(String paperId){
        Result result = Result.ok();
        List<PracticePaper> paperList = practicePaperService.listAllPracPaper();
        result.put("list",paperList);
        return result;
    }
}
