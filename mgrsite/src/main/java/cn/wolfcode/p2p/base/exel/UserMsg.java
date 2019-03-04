package cn.wolfcode.p2p.base.exel;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Excel("用户信息模板")
public class UserMsg implements Serializable {

    private static final long serialVersionUID = 6682377316835907294L;

    @ExcelField(value = "用户名称")
    private String userName;


    @ExcelField(value = "年龄")
    private String age;


    @ExcelField(value = "密码")
    private String password;

}
