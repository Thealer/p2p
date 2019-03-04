package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.BitStatesUtils;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userinfoMapper;

    public void init(LoginInfo loginInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(loginInfo.getId());
        userInfo.setPhone_number(loginInfo.getUsername());

        userinfoMapper.insert(userInfo);
    }

    public UserInfo selectById(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    public void UpdateBasicInfo(UserInfo userInfo) {
        //从数据库查出原本的userInfo
        UserInfo orUserInfo = userinfoMapper.selectByPrimaryKey(UserContext.getLoginInfo().getId());

        orUserInfo.setEducationBackground(userInfo.getEducationBackground());
        orUserInfo.setHouseCondition(userInfo.getHouseCondition());
        orUserInfo.setIncomeGrade(userInfo.getIncomeGrade());
        orUserInfo.setKidCount(userInfo.getKidCount());
        orUserInfo.setMarriage(userInfo.getMarriage());

        //修改用户基本资料状态
        if(!orUserInfo.isBasicInfo()){
            Long bit_state = BitStatesUtils.addState(orUserInfo.getBit_state(),BitStatesUtils.OP_BASIC_INFO);
            orUserInfo.setBit_state(bit_state);
        }
        update(orUserInfo);
    }

    public void update(UserInfo userInfo){
        int count =  userinfoMapper.updateByPrimaryKey(userInfo);

        if(count == 0){
            throw new DisplayException("用户信息修改失败[乐观锁异常 ID:"+userInfo.getId()+"],请稍后重试");
        }
    }
}
