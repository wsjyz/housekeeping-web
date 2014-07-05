package com.eighth.housekeeping.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * 阿姨
 * Created by dam on 2014/7/3.
 */
public class AuntInfo extends BaseDomain {

    private String auntId;
    private String password;//密码
    private String mobile;//手机号
    private String userName;//姓名
    private String identityCard;//身份证号
    private String sex;//MALE|FAMALE
    private String age;//年龄
    private String address;//居住地址
    private BigDecimal price;//单价
    private String telephone;//电话
    private int workYear;//工作经验
    private int abilityScore;//能力分值
    private boolean integrityAuth;//诚信认证
    private String description;//简评
    private String nativePlace;//籍贯
    private String busiDesc;//业务
    private String bloodType;//血型
    private String selfEvaluate;//自我评价
    private String constellation;//星座
    private String workType;//分类
    private String start;//星级
    private List<AuntWorkCase> caseList;//案例
    private OpenPage<Review> reviews;//评论


    public OpenPage<Review> getReviews() {
        return reviews;
    }

    public void setReviews(OpenPage<Review> reviews) {
        this.reviews = reviews;
    }

    public String getAuntId() {
        return auntId;
    }

    public void setAuntId(String auntId) {
        this.auntId = auntId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getWorkYear() {
        return workYear;
    }

    public void setWorkYear(int workYear) {
        this.workYear = workYear;
    }

    public int getAbilityScore() {
        return abilityScore;
    }

    public void setAbilityScore(int abilityScore) {
        this.abilityScore = abilityScore;
    }

    public boolean isIntegrityAuth() {
        return integrityAuth;
    }

    public void setIntegrityAuth(boolean integrityAuth) {
        this.integrityAuth = integrityAuth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getBusiDesc() {
        return busiDesc;
    }

    public void setBusiDesc(String busiDesc) {
        this.busiDesc = busiDesc;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getSelfEvaluate() {
        return selfEvaluate;
    }

    public void setSelfEvaluate(String selfEvaluate) {
        this.selfEvaluate = selfEvaluate;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<AuntWorkCase> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<AuntWorkCase> caseList) {
        this.caseList = caseList;
    }
}
