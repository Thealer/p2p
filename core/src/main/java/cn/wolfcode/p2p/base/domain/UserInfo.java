package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.base.util.BitStatesUtils;

public class UserInfo {

    private Long id;

    private int version;
    private Long bit_state = 0L;

    private String real_name;

    private String id_number;

    private String phone_number;

    private SystemDictionaryItem incomeGrade;

    private SystemDictionaryItem marriage;

    private SystemDictionaryItem kidCount;

    private SystemDictionaryItem educationBackground;

    private SystemDictionaryItem houseCondition;

    private String email;

    //实名认证对象
    private Long realAuthId;

    //视频认证对象
    private Long videoAuthId;

    public Long getVideoAuthId() {
        return videoAuthId;
    }

    public void setVideoAuthId(Long videoAuthId) {
        this.videoAuthId = videoAuthId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //判断是否已经完成基本资料认证
    public boolean isBasicInfo(){
        return BitStatesUtils.hasState(bit_state,BitStatesUtils.OP_BASIC_INFO);
    }
    //判断是否完成实名认证
    public boolean isRealAuth(){
        return BitStatesUtils.hasState(bit_state,BitStatesUtils.OP_REAL_AUTH);
    }

    //判断是否完成视频认证
    public boolean isVedioAuth(){
        return BitStatesUtils.hasState(bit_state,BitStatesUtils.OP_VEDIO_AUTH);
    }

    //判断是否满足借款条件
    public boolean isCanBorrow(){
        return isBasicInfo()&&isRealAuth()&&isVedioAuth();
    }

    //判断是否在借款中
    public boolean isBidRequestInProcess(){
        return BitStatesUtils.hasState(bit_state,BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
    }


    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", version=" + version +
                ", bit_state=" + bit_state +
                ", real_name='" + real_name + '\'' +
                ", id_number='" + id_number + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", incomeGrade=" + incomeGrade +
                ", marriage=" + marriage +
                ", kidCount=" + kidCount +
                ", educationBackground=" + educationBackground +
                ", houseCondition=" + houseCondition +
                '}';
    }


    public Long getRealAuthId() {
        return realAuthId;
    }

    public void setRealAuthId(Long realAuthId) {
        this.realAuthId = realAuthId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getBit_state() {
        return bit_state;
    }

    public void setBit_state(Long bit_state) {
        this.bit_state = bit_state;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public SystemDictionaryItem getIncomeGrade() {
        return incomeGrade;
    }

    public void setIncomeGrade(SystemDictionaryItem incomeGrade) {
        this.incomeGrade = incomeGrade;
    }

    public SystemDictionaryItem getMarriage() {
        return marriage;
    }

    public void setMarriage(SystemDictionaryItem marriage) {
        this.marriage = marriage;
    }

    public SystemDictionaryItem getKidCount() {
        return kidCount;
    }

    public void setKidCount(SystemDictionaryItem kidCount) {
        this.kidCount = kidCount;
    }

    public SystemDictionaryItem getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(SystemDictionaryItem educationBackground) {
        this.educationBackground = educationBackground;
    }

    public SystemDictionaryItem getHouseCondition() {
        return houseCondition;
    }

    public void setHouseCondition(SystemDictionaryItem houseCondition) {
        this.houseCondition = houseCondition;
    }
}