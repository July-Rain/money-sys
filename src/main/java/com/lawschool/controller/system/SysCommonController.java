package com.lawschool.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.accessory.AccessoryEntity;
import com.lawschool.service.accessory.AccessoryService;
import com.lawschool.util.FileUtil;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ClassName: SysCommonController
 * Description: 系统公共的controller
 * date: 2018-12-6 13:48
 *
 * @author MengyuWu
 * @since JDK 1.8
 */


@Controller
@RequestMapping("/sys")
public class SysCommonController extends AbstractController {

    @Autowired
    private AccessoryService accessoryService;
    /**
     * @Author MengyuWu
     * @Description 系统文件上传
     * @Date 14:13 2018-12-6
     * @Param [request]
     * @return com.lawschool.util.Result
     **/
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(MultipartHttpServletRequest request) throws IOException {
        Result result = new Result();
        List<MultipartFile> importfile = request.getFiles("importfile");
        if (UtilValidate.isEmpty(importfile)) {
            throw new RuntimeException("上传失败：文件为空");
        }
        for (MultipartFile multipartFile : importfile) {
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename.contains(",")) {
                originalFilename = originalFilename.replaceAll(",", "");
            }
            InputStream inputStream = multipartFile.getInputStream();//输入流
            Result uploadResult = FileUtil.uploadToFTPServer(originalFilename,inputStream);
            if("success".equals(uploadResult.get("msg"))){
                return uploadResult;
            }else{
                return result.error("上传失败");
            }
        }
        return result.error("未知异常");
    }

    /**
     * @Author MengyuWu
     * @Description 附件下载
     * @Date 14:13 2018-12-6
     * @Param [filePath, filename, response]
     * @return void
     **/
    
    /*@RequestMapping("/download")
    public void downLoad(@RequestParam(value = "accessoryId") String accessoryId, HttpServletResponse response) throws IOException {
        if (UtilValidate.isEmpty(accessoryId)) {
            throw new RuntimeException("文件路径错误");
        }
        //根据附件id下载相关的附件
        AccessoryEntity accessoryEntity = accessoryService.selectById(accessoryId);
        if(UtilValidate.isEmpty(accessoryEntity)){
            logger.info("文件不存在");
        }
        ServletOutputStream outputStream = response.getOutputStream();
        response=FileUtil.downloadFromFileServer(accessoryId,outputStream,response);
        outputStream.close();
    }*/
    @RequestMapping("/download")
    public void downLoad(@RequestParam String accessoryId, HttpServletResponse response) throws IOException {

        if (UtilValidate.isEmpty(accessoryId)) {
            throw new RuntimeException("附件id为空");
        }
        ServletOutputStream outputStream = response.getOutputStream();

            AccessoryEntity accessoryEntity = accessoryService.selectOne(new EntityWrapper<AccessoryEntity>().eq("id", accessoryId));
            if (UtilValidate.isNotEmpty(accessoryEntity)) {
                String fileName = null;
                if (UtilValidate.isNotEmpty(accessoryEntity.getAccessoryName())) {
                    fileName = java.net.URLEncoder.encode(accessoryEntity.getAccessoryName(), "UTF-8");
                } else {
                    fileName = "未知名称的附件";
                }
                if (fileName.contains(",")) {
                    fileName = fileName.replaceAll(",", "");
                }
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + fileName);
                outputStream=(ServletOutputStream)FileUtil.downloadFromFileServer(accessoryId,outputStream);
            }

        outputStream.close();
    }

}
