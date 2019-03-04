package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    int selectForCount(SystemDictionaryQueryObject qo);

    List selectForList(SystemDictionaryQueryObject qo);

    List<SystemDictionaryItem> selectItemsBySn(String sn);
}