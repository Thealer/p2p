package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryMapper;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemdictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemdictionaryitemMapper;

    public PageResult queryDic(SystemDictionaryQueryObject qo) {
        int count = systemdictionaryMapper.selectForCount(qo);
        if(count == 0){
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(systemdictionaryMapper.selectForList(qo),count,qo.getCurrentPage(),qo.getPageSize());
    }

    public void systemDictionaryUpdate(SystemDictionary systemDictionary) {
        if(systemDictionary.getId()!= null){
            systemdictionaryMapper.updateByPrimaryKey(systemDictionary);
            return;
        }
        systemdictionaryMapper.insert(systemDictionary);
    }

    public PageResult queryItem(SystemDictionaryQueryObject qo) {
        int count = systemdictionaryitemMapper.selectForCount(qo);
        if(count == 0){
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(systemdictionaryitemMapper.selectForList(qo),count,qo.getCurrentPage(),qo.getPageSize());
    }


    public List<SystemDictionary> selectAll() {
        return systemdictionaryMapper.selectAll();
    }

    public void systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem) {
        System.out.println(systemDictionaryItem);
        if(systemDictionaryItem.getId()!= null){
            systemdictionaryitemMapper.updateByPrimaryKey(systemDictionaryItem);
            return;
        }
        systemdictionaryitemMapper.insert(systemDictionaryItem);
    }

    public List<SystemDictionaryItem> selectItemsBySn(String sn) {
        return systemdictionaryitemMapper.selectItemsBySn(sn);
    }
}
