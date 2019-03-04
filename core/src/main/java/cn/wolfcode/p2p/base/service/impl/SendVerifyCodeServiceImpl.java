package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.DateUtil;
import cn.wolfcode.p2p.base.util.HttpUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.base.vo.SendVerifyCodeVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SendVerifyCodeServiceImpl implements ISendVerifyCodeService {

    public void send(String phone) {
        //判断手机号是否为空
        AsserUtil.isNull(phone,"手机号不能为空");
        //判断上次发送验证码和这次发送验证码时间差
        SendVerifyCodeVo lastVo = UserContext.getVerifyCodeVo();
        Date currentTime = new Date();
        if(lastVo != null){
            Date lastVoSendTime = lastVo.getSendTime();
            if(DateUtil.getSecondsBetween(lastVoSendTime,currentTime) < 10){
                throw new DisplayException("发送过于频繁,请稍后重试");
            }
        }
        //创建验证码
        String code = UUID.randomUUID().toString().substring(0,4);
        //发送
        System.out.println("您的验证码为" + code);
        sendVerifyCode(phone,code);
        //将发送记录保存到session中
        SendVerifyCodeVo vo = new SendVerifyCodeVo();
        vo.setCode(code);
        vo.setPhone(phone);
        vo.setSendTime(currentTime);
        UserContext.setVerifyCodeVo(vo);
    }

    private void sendVerifyCode(String phone,String code){

        //http://utf8.api.smschinese.cn/?Uid=本站用户名&Key=接口安全秘钥&smsMob=手机号码&smsText=验证码:8888
        Map<String,String> params = new HashMap<String,String>();
        params.put("Uid","healer");
        params.put("Key","d41d8cd98f00b204e980");
        params.put("smsMob",phone);
        params.put("smsText","您的验证码为" + code +"请在3分钟内使用");

        try {
            String result =  HttpUtil.sendHttpRequest("http://utf8.api.smschinese.cn/",params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
