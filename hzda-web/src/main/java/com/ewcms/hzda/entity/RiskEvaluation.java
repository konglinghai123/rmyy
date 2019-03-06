package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 风险评估
 * 
 * <ul>
 * <li>omt1:父母是否患有骨质疏松症或曾经在轻微跌倒后骨折</li>
 * <li>omt2:父母一人有驼背</li>
 * <li>omt3:是否40岁或以上</li>
 * <li>omt4:成年后是否曾经在轻微跌倒后骨折</li>
 * <li>omt5:是否经常跌倒(在过去的一年里曾跌倒多过一次)或因身体虚弱而担心会跌倒？</li>
 * <li>omt6:40岁后，身高是否降低了3厘米以上</li>
 * <li>omt7:是否体重过轻（BMI值少于19kg/m2）</li>
 * <li>omt8:是否曾经连续服用类固醇类药物(如可的松、强的松等) 超过3个月</li>
 * <li>omt9:是否患有类风湿性关节炎</li>
 * <li>omt10:是否患有甲状腺功能亢进、甲状旁腺功能亢进、1型糖尿病、克罗恩病或乳糜泻等胃肠疾病或营养不良</li>
 * <li>omt11:是否在45岁或以前已停经</li>
 * <li>omt12:怀孕、停经或切除子宫后，您是否曾经停经超过12个月</li>
 * <li>omt13:是否在50岁前切除卵巢且没有服用激素补充剂</li>
 * <li>omt14:是否曾经因雄激素过低而导致阳痿或性欲减低</li>
 * <li>omt15:是否每天饮酒（每天超过两单位乙醇，相当于啤酒1斤、葡萄糖酒3两或烈性酒1两）</li>
 * <li>omt16:是否抽烟</li>
 * <li>omt17每天的体力活动(如做家务、园艺、散步等)是否少于30分</li>
 * <li>omt18:是否不能进食奶类制品又没有服用钙片</li>
 * <li>omt19:每天的户外活动(部分身体暴露于阳光下)是否少于10分钟又没有服用维生素D补充剂</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_risk_evaluation")
@SequenceGenerator(name = "seq", sequenceName = "hzda_risk_evaluation_id", allocationSize = 1)
public class RiskEvaluation extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -1183015670942513773L;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Formula(value = "(select s_o.realname  from sec_user s_o where s_o.id=user_id)")
	private String realName;
	@Column(name = "organization_id")
	private Long organizationId;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id=organization_id)")
	private String organizationName;
	@Column(name = "general_information_id", unique = true, nullable = false)
	private Long generalInformationId;

	@Column(name = "is_omt_1")
	private Boolean omt1;
	@Column(name = "is_omt_2")
	private Boolean omt2;
	@Column(name = "is_omt_3")
	private Boolean omt3;
	@Column(name = "is_omt_4")
	private Boolean omt4;
	@Column(name = "is_omt_5")
	private Boolean omt5;
	@Column(name = "is_omt_6")
	private Boolean omt6;
	@Column(name = "is_omt_7")
	private Boolean omt7;
	@Column(name = "is_omt_8")
	private Boolean omt8;
	@Column(name = "is_omt_9")
	private Boolean omt9;
	@Column(name = "is_omt_10")
	private Boolean omt10;
	@Column(name = "is_omt_11")
	private Boolean omt11;
	@Column(name = "is_omt_12")
	private Boolean omt12;
	@Column(name = "is_omt_13")
	private Boolean omt13;
	@Column(name = "is_omt_14")
	private Boolean omt14;
	@Column(name = "is_omt_15")
	private Boolean omt15;
	@Column(name = "is_omt_16")
	private Boolean omt16;
	@Column(name = "is_omt_17")
	private Boolean omt17;
	@Column(name = "is_omt_18")
	private Boolean omt18;
	@Column(name = "is_omt_19")
	private Boolean omt19;
	public Boolean getOmt1() {
		return omt1;
	}
	public void setOmt1(Boolean omt1) {
		this.omt1 = omt1;
	}
	public Boolean getOmt2() {
		return omt2;
	}
	public void setOmt2(Boolean omt2) {
		this.omt2 = omt2;
	}
	public Boolean getOmt3() {
		return omt3;
	}
	public void setOmt3(Boolean omt3) {
		this.omt3 = omt3;
	}
	public Boolean getOmt4() {
		return omt4;
	}
	public void setOmt4(Boolean omt4) {
		this.omt4 = omt4;
	}
	public Boolean getOmt5() {
		return omt5;
	}
	public void setOmt5(Boolean omt5) {
		this.omt5 = omt5;
	}
	public Boolean getOmt6() {
		return omt6;
	}
	public void setOmt6(Boolean omt6) {
		this.omt6 = omt6;
	}
	public Boolean getOmt7() {
		return omt7;
	}
	public void setOmt7(Boolean omt7) {
		this.omt7 = omt7;
	}
	public Boolean getOmt8() {
		return omt8;
	}
	public void setOmt8(Boolean omt8) {
		this.omt8 = omt8;
	}
	public Boolean getOmt9() {
		return omt9;
	}
	public void setOmt9(Boolean omt9) {
		this.omt9 = omt9;
	}
	public Boolean getOmt10() {
		return omt10;
	}
	public void setOmt10(Boolean omt10) {
		this.omt10 = omt10;
	}
	public Boolean getOmt11() {
		return omt11;
	}
	public void setOmt11(Boolean omt11) {
		this.omt11 = omt11;
	}
	public Boolean getOmt12() {
		return omt12;
	}
	public void setOmt12(Boolean omt12) {
		this.omt12 = omt12;
	}
	public Boolean getOmt13() {
		return omt13;
	}
	public void setOmt13(Boolean omt13) {
		this.omt13 = omt13;
	}
	public Boolean getOmt14() {
		return omt14;
	}
	public void setOmt14(Boolean omt14) {
		this.omt14 = omt14;
	}
	public Boolean getOmt15() {
		return omt15;
	}
	public void setOmt15(Boolean omt15) {
		this.omt15 = omt15;
	}
	public Boolean getOmt16() {
		return omt16;
	}
	public void setOmt16(Boolean omt16) {
		this.omt16 = omt16;
	}
	public Boolean getOmt17() {
		return omt17;
	}
	public void setOmt17(Boolean omt17) {
		this.omt17 = omt17;
	}
	public Boolean getOmt18() {
		return omt18;
	}
	public void setOmt18(Boolean omt18) {
		this.omt18 = omt18;
	}
	public Boolean getOmt19() {
		return omt19;
	}
	public void setOmt19(Boolean omt19) {
		this.omt19 = omt19;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getGeneralInformationId() {
		return generalInformationId;
	}
	public void setGeneralInformationId(Long generalInformationId) {
		this.generalInformationId = generalInformationId;
	}
	public String getRealName() {
		return realName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
}
