package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface ISystemDictionaryService {
    //数据字典列表查询
    PageResult queryDic(SystemDictionaryQueryObject qo);

    void systemDictionaryUpdate(SystemDictionary systemDictionary);

    //数据字典明细列表查询
    PageResult queryItem(SystemDictionaryQueryObject qo);

    //数据字典分组
    List<SystemDictionary> selectAll();

    //操作数据字典明细(修改或保存)
    void systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem);

    //根据数据字典编号查询明细列表
    List<SystemDictionaryItem> selectItemsBySn(String sn);
}
