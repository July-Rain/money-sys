package com.lawschool.enums;

import com.lawschool.base.IEnum;

/**
 * Name: Source
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
public enum Source implements IEnum {

    EXAM("考试"),VIDEOSTUDY("视频学习"),AUDIOSTUDY("音频学习"),
    PICSTUDY("图文学习"),STUTASK("学习任务"),GROUPPRAC("组卷练习"),
    OTHERPRAC("非组卷练习"),DAILYQUE("每日一题"),RECRUIT("闯关"),
    COMPEITIONONLINE("在线比武"),MATCH("擂台赛");

    private final String value;

    Source(String value) {
        this.value = value;
    }
    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
