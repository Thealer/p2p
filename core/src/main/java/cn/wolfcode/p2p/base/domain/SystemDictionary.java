package cn.wolfcode.p2p.base.domain;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SystemDictionary {
    private Long id;
    private String sn;
    private String title;
    private String intro;

    //返回一个json供页面获取
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("title",title);

        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
