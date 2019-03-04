package cn.wolfcode.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class SendVerifyCodeVo {
    private String phone;
    private Date sendTime;
    private String code;
}
