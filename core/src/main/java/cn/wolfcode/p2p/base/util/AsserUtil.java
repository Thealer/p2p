package cn.wolfcode.p2p.base.util;

import cn.wolfcode.p2p.base.exception.DisplayException;
import org.springframework.util.StringUtils;

/**
 * 断言工具
 */
public class AsserUtil {

    /**
     * 判断是否为空
     * @param object
     * @param message
     */
    public static void isNull(Object object,String message){
        if(object == null){
            throw new DisplayException(message);
        }
        if(object instanceof String){
            if(!StringUtils.hasLength((String) object)){
                throw new DisplayException(message);
            }
        }
    }

    /**
     * 如果是true抛出异常
     * @param isTrue
     * @param message
     */
    public static void isTrue(boolean isTrue,String message){
        if(isTrue){
            throw new DisplayException(message);
        }
    }
}
