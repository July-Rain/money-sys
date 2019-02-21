package com.lawschool.controller.system;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Auther: Moon
 * @Date: 2019/2/20 14:34
 * @Description:
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request){
        String path = request.getServletContext().getRealPath("/statics/excel");
        System.out.println("path===" + path);

        String fileName = path + File.separator + "question_template.xlsx";
        System.out.println("fileName===" + fileName);

        File file = new File(fileName);
        System.out.println("file===" + file.getName());
        return null;
    }

}
