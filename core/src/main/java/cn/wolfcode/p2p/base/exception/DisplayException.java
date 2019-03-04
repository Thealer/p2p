package cn.wolfcode.p2p.base.exception;
/**
 * 自定义异常
 *
 * */
public class DisplayException extends RuntimeException{
    public DisplayException(String errorMsg){
        super(errorMsg);
    }
}
