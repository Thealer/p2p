package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.IRealauthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UploadUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RealAuthController {

    @Autowired
    private IRealauthService realauthService;

    @Autowired
    private IUserInfoService userInfoService;

    @Value("${uploadPath}")
    private String uploadPath;

    //进入实名认证申请页面
    @RequestMapping("/realAuth")
    public String realAuth(Model model){
        UserInfo userInfo = userInfoService.selectById(UserContext.getLoginInfo().getId());
        //如果已经完成实名认证,跳转到结果页面
        if(userInfo.isRealAuth()){
           RealAuth realAuth = realauthService.selectById(userInfo.getRealAuthId());
            model.addAttribute("realAuth",realAuth);
            return "realAuth_result";
        }
        //如果已经提交了申请,跳转到等待审核页面
        if(userInfo.getRealAuthId() != null){
            model.addAttribute("auditing",true);
            return "realAuth_result";
        }
        return "realAuth";
    }


    //实名认证资料保存

    @RequestMapping("/realAuth_save")
    @ResponseBody
    public JsonResoult realAuthSave(RealAuth realAuth){
        JsonResoult resoult = new JsonResoult();
        try {

            realauthService.realAuthSave(realAuth);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }
        return resoult;
    }

    @RequestMapping("/uploadPhoto")
    @ResponseBody
    public String uploadPhoto(MultipartFile file){
        return UploadUtil.upload(file,uploadPath);
    }
}
