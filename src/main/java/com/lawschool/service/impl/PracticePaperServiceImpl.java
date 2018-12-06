package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.PracticePaper;
import com.lawschool.dao.PracticePaperDao;
import com.lawschool.service.PracticePaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PracticePaperServiceImpl implements PracticePaperService {

    @Autowired
    PracticePaperDao practicePaperDao;

    @Override
    public List<PracticePaper> listAllPracPaper() {
        List<PracticePaper> practicePaperList = practicePaperDao.selectList(new EntityWrapper<PracticePaper>());
        return practicePaperList;
    }

    @Override
    public int addPracPaperById(PracticePaper practicePaper) {
        practicePaperDao.insert(practicePaper);
        return 1;
    }

    @Override
    public int deleteByPaperId(String paperId) {
        practicePaperDao.delete(new EntityWrapper<PracticePaper>().eq("ID",paperId));
        return 0;
    }

    @Override
    public PracticePaper selectByPaperId(String paperId) {
        PracticePaper practicePaper = practicePaperDao.selectById(paperId);
        return practicePaper;
    }


}
