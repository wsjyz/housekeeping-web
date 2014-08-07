package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

import java.math.BigDecimal;
import java.util.List;

/**
 * 阿姨
 * Created by dam on 2014/7/3.
 */
@Table(name="t_aunt_info",comment = "阿姨|小时工")
public class AuntInfo extends BaseDomain {

    @Column(name="aunt_id",pk=true,length = 32)
    private String auntId;
    @Column(name="password",comment = "密码",length = 32)
    private String password;
    @Column(name="mobile",comment = "手机号",length = 11)
    private String mobile;
    @Column(name="user_name",comment = "姓名",length = 50)
    private String userName;
    @Column(name="identity_card",comment = "身份证号",length = 20)
    private String identityCard;
    @Column(name="sex",comment = "MALE|FAMALE",length = 6)
    private String sex;
    @Column(name="age",comment = "年龄",length = 3)
    private String age;
    @Column(name="address",comment = "居住地址",length = 50)
    private String address;
    @Column(name="price",comment = "单价",length = 10,decimal = 2)
    private BigDecimal price;
    @Column(name="telephone",comment = "电话",length = 10)
    private String telephone;
    @Column(name="work_year",comment = "工作经验",length = 2)
    private int workYear;
    @Column(name="ability_score",comment = "能力分值",length = 2)
    private int abilityScore;
    @Column(name="integrity_auth",comment = "诚信认证")
    private boolean integrityAuth;
    @Column(name="description",comment = "简评",length = 100)
    private String description;
    @Column(name="native_place",comment = "籍贯",length = 5)
    private String nativePlace;
    @Column(name="busi_desc",comment = "业务",length = 20)
    private String busiDesc;
    @Column(name="blood_type",comment = "血型",length = 3)
    private String bloodType;
    @Column(name="self_evaluate",comment = "自我评价",length = 100)
    private String selfEvaluate;
    @Column(name="constellation",comment = "星座",length = 5)
    private String constellation;
    @Column(name="work_type",comment = "分类:HOUR_WORK",length = 5)
    private String workType;
    @Column(name="start",comment = "星级",length = 1)
    private String start;

    //以下是统计信息
    @Column(name="year_of_income",comment = "年度金额",length = 10,decimal = 2)
    private BigDecimal yearOfIncome;//年度金额
    @Column(name="month_of_order_counts",comment = "月度订单数",length = 3)
    private int monthOfOrderCounts;//月度订单数
    @Column(name="total_order_counts",comment = "总订单数",length = 3)
    private int totalOrderCounts;//总订单数
    @Column(name="moth_of_income",comment = "本月收入",length = 3)
    private int mothOfIncome;//本月收入
    @Column(name="very_satisfy_counts",comment = "非常满意评价数量",length = 3)
    private int verySatisfyCounts;//非常满意评价数量
    @Column(name="satisfy_counts",comment = "满意评价数量",length = 3)
    private int satisfyCounts;//满意评价数量
    @Column(name="not_satisfy_counts",comment = "不满意评价数量",length = 3)
    private int notSatisfyCounts;//不满意评价数量
    @Column(name="month_of_sign_counts",comment = "本月签到天数",length = 3)
    private int monthOfSignCounts;//本月签到天数

    private String loginResult;//登录结果，传值用 UCCESS成功|ACOUNT_NOT_CORRECT用户名或密码错

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    //以下是关系
    private List<AuntWorkCase> caseList;//案例
    private OpenPage<Review> reviews;//评论

    private boolean userCollected;//当前用户是否收藏了此阿姨

    public boolean getUserCollected() {
        return userCollected;
    }

    public void setUserCollected(boolean userCollected) {
        this.userCollected = userCollected;
    }

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

    public BigDecimal getYearOfIncome() {
        return yearOfIncome;
    }

    public void setYearOfIncome(BigDecimal yearOfIncome) {
        this.yearOfIncome = yearOfIncome;
    }

    public int getMonthOfOrderCounts() {
        return monthOfOrderCounts;
    }

    public void setMonthOfOrderCounts(int monthOfOrderCounts) {
        this.monthOfOrderCounts = monthOfOrderCounts;
    }

    public int getTotalOrderCounts() {
        return totalOrderCounts;
    }

    public void setTotalOrderCounts(int totalOrderCounts) {
        this.totalOrderCounts = totalOrderCounts;
    }

    public int getMothOfIncome() {
        return mothOfIncome;
    }

    public void setMothOfIncome(int mothOfIncome) {
        this.mothOfIncome = mothOfIncome;
    }

    public int getVerySatisfyCounts() {
        return verySatisfyCounts;
    }

    public void setVerySatisfyCounts(int verySatisfyCounts) {
        this.verySatisfyCounts = verySatisfyCounts;
    }

    public int getSatisfyCounts() {
        return satisfyCounts;
    }

    public void setSatisfyCounts(int satisfyCounts) {
        this.satisfyCounts = satisfyCounts;
    }

    public int getNotSatisfyCounts() {
        return notSatisfyCounts;
    }

    public void setNotSatisfyCounts(int notSatisfyCounts) {
        this.notSatisfyCounts = notSatisfyCounts;
    }

    public int getMonthOfSignCounts() {
        return monthOfSignCounts;
    }

    public void setMonthOfSignCounts(int monthOfSignCounts) {
        this.monthOfSignCounts = monthOfSignCounts;
    }
}
