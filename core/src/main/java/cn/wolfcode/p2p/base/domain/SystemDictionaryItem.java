package cn.wolfcode.p2p.base.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SystemDictionaryItem {
    private Long id;

    private String title;

    private String intro;

    private String tvalue;

    private Integer sequence;

    private Long parentId;

    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("title",title);
        map.put("sequence",sequence);
        map.put("parentId",parentId);

        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "SystemDictionaryItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", tvalue='" + tvalue + '\'' +
                ", sequence=" + sequence +
                ", parentId=" + parentId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTvalue() {
        return tvalue;
    }

    public void setTvalue(String tvalue) {
        this.tvalue = tvalue;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}