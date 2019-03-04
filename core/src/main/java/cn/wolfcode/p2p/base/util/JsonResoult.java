package cn.wolfcode.p2p.base.util;

public class JsonResoult {

    private boolean success =  true;
    private String msg;


    public boolean isSuccess() {
        return success;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.success = false;
        this.msg = msg;
    }
}
