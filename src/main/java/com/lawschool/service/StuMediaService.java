package com.lawschool.service;

import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface StuMediaService  {

    List<StuMedia>  listMyCollection(Map<String,Object> param);

    int deleteStuMedia(StuMedia stuMedia);

    StuMedia getStuMedia(StuMedia stuMedia);

    int insert(StuMedia stuMedia, User user);

    int update(StuMedia stuMedia,User user);
}
