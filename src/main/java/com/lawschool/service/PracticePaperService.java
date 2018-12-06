package com.lawschool.service;

import com.lawschool.beans.PracticePaper;
import com.lawschool.dao.PracticePaperDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Autor liuhuan
 * @Description TODO
 * @DATE 2018-11-30
 * @Param
 * @return
 */
public interface PracticePaperService {

    /**
     * 查询所有练习卷
     * @return
     */
    public List<PracticePaper> listAllPracPaper();

    /**
     * 新增练习卷
     * @param practicePaper
     * @return
     */
    public int addPracPaperById(PracticePaper practicePaper);

    /**
     * 根据ID删除单条练习卷数据
     * @param paperId
     * @return
     */
    public int deleteByPaperId(String paperId);

    /**
     * 查看选定数据根据ID
     * @param paperId
     * @return
     */
    public PracticePaper selectByPaperId(String paperId);
}
