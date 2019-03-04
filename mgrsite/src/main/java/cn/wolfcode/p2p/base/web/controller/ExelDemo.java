package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exel.UserMsg;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/exel")
@Slf4j
@Controller
public class ExelDemo {

    /**
     * 下载excel模板(或导出页面数据的excel格式)
     * @param response
     */
    @RequestMapping(value = "/downXlsx", method = RequestMethod.GET)
    public void export(HttpServletResponse response){
        try {
            List<UserMsg> userMsgs = new ArrayList<UserMsg>();
            long beginTimeMillis = System.currentTimeMillis();
            ExcelKit.$Export(UserMsg.class,response).downXlsx(userMsgs,false);
            log.info("数据量 :" + userMsgs.size() + ", 耗时: " + (System.currentTimeMillis() - beginTimeMillis)/1000L);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("下载失败");
        }
        log.info("下载成功");
    }

    /**
     * 上传excel文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/imortUser", method = RequestMethod.POST)
    public ResponseEntity<?> importUserMsg(@RequestParam("file") MultipartFile multipartFile) {

        final List<UserMsg> userMsgList =new ArrayList<UserMsg>();
        try {
            final List<Map<String, Object>> errorList = Lists.newArrayList();
            final List<UserMsg> successList = Lists.newArrayList();
            ExcelKit.$Import(UserMsg.class).readXlsx(multipartFile.getInputStream(), new ExcelReadHandler<UserMsg>() {
                @Override
                public void onSuccess(int sheet, int row, UserMsg userMsg) {
                    successList.add(userMsg);
                    userMsgList.add(userMsg);
                }
                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> list) {
                    errorList.add(ImmutableMap.of("row", row, "errorFields", list));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件解析出错");
        }
        log.info("用户信息集合: " + userMsgList.toString());
        return ResponseEntity.ok("上传成功" + userMsgList);
    }
}
