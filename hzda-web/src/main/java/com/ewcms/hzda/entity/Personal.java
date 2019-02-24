package com.ewcms.hzda.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 个人史
 * 
 * <ul>
 * <li>userId:医生编号</li>
 * <li>realName:医生姓名</li>
 * <li>organizationId:医院编号</li>
 * <li>organizationNames:医院名称</li>
 * <li>generalInformationId:一般信息编号</li>
 * <li>noSmok:不吸烟</li>
 * <li>quitSmok:已戒烟</li>
 * <li>quitSmokAge:戒烟年龄</li>
 * <li>smok:目前吸烟</li>
 * <li>smokDay:每日几支</li>
 * <li>smokYear:吸烟几年</li>
 * <li>smokRefuseAnswer:吸烟拒绝回答</li>
 * <li>noDrink:不饮或很少饮酒</li>
 * <li>quitDrink:已戒酒</li>
 * <li>quitDrinkAge:已戒酒年龄</li>
 * <li>oftenDrink:经常饮酒</li>
 * <li>oftenDrinkBeginAge:开始饮酒年龄</li>
 * <li>beer:啤酒</li>
 * <li>beerMl:啤酒每天几毫升</li>
 * <li>spirit:白酒</li>
 * <li>spiritMl:白酒每天几毫升</li>
 * <li>redWine:红酒</li>
 * <li>redWineMl:红酒每天几毫升</li>
 * <li>drinkRefuseAnswer:饮酒拒绝回答</li>
 * <li>fullDiet:普通膳食</li>
 * <li>vegetarianDiet:素食或基本素食</li>
 * <li>lowfatDiet:低脂肪膳食</li>
 * <li>diabeticDiet:糖尿病膳食</li>
 * <li>dietRefuseAnswer:膳食拒绝回答</li>
 * <li>coffee:咖啡</li>
 * <li>coffeeWeeklyDrinks:咖啡每周几次</li>
 * <li>coffeeEveryTimeDrinks:咖啡每次几ML</li>
 * <li>strongTea:浓茶</li>
 * <li>:浓茶每周几次</li>
 * <li>strongTeaEveryTimeDrinks:浓茶每次几ML</li>
 * <li>sodas:碳酸饮料</li>
 * <li>sodasWeeklyDrinks:碳酸饮料每周几次</li>
 * <li>sodasEveryTimeDrinks:碳酸饮料每次几ML</li>
 * <li>milk:牛奶</li>
 * <li>milkWeeklyDrinks:牛奶每周几次</li>
 * <li>milkEveryTimeDrinks:牛奶每次几ML</li>
 * <li>otherDrink:其他饮料</li>
 * <li>otherDrinkDesc:其他饮料说明</li>
 * <li>otherDrinkWeeklyDrinks:其他饮料每周几次</li>
 * <li>otherDrinkEveryTimeDrinks:其他饮料每次几ML</li>
 * <li>dadra:饮料饮用拒绝回答</li>
 * <li>sleepLessThanFourHours:小于4小时</li>
 * <li>sleepFourToSixHours:4-6小时</li>
 * <li>sleepSevenToEightHours:7-8小时</li>
 * <li>sleepNineToTenHours:9-10小时</li>
 * <li>sleepMoreThanTenHours:大于10小时</li>
 * <li>sleepRefuseAnswer:睡眼时间拒绝回答</li>
 * <li>insolationLittle:日晒很少</li>
 * <li>insolationMoreThanTenMin:平均每天10分钟以上</li>
 * <li>insolationMoreThanThirtyMin:平均每天30分钟以上</li>
 * <li>temperLessThanThree:平均每周锻炼小于3次</li>
 * <li>temperThreeToFive:平均每周锻炼3-5次</li>
 * <li>temperMoreThanFive:平均每周锻炼大于5次</li>
 * <li>temperNever:平均每周锻炼从不</li>
 * <li>temperRefuseAnswer:平均每周锻炼拒绝回答</li>
 * <li>temperLessThanThirtyMin:平均每次锻炼小于30分钟</li>
 * <li>temperThirtyToSixtyMin:平均每次锻炼30-60分钟</li>
 * <li>temperMoreThanSixtyMin:平均每次锻炼大于60分钟</li>
 * <li>walk:散步</li>
 * <li>skelp:快步走</li>
 * <li>jog:慢跑</li>
 * <li>dance:跳舞</li>
 * <li>shadowboxing:太极拳</li>
 * <li>stairs:爬山/楼梯</li>
 * <li>fitnessEquipment:器材健身</li>
 * <li>playBall:打球</li>
 * <li>swim:游泳</li>
 * <li>cycling:骑车</li>
 * <li>temperOther:其他锻炼方式</li>
 * <li>temperOtherDesc:其他锻炼方式说明</li>
 * </ul>
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "hzda_personal")
@SequenceGenerator(name = "seq", sequenceName = "hzda_personal_id", allocationSize = 1)
public class Personal extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 4497015972208293083L;

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

	@Column(name = "is_no_smok")
	private Boolean noSmok;
	@Column(name = "is_quit_smok")
	private Boolean quitSmok;
	@Column(name = "quit_smok_age")
	private Long quitSmokAge;
	@Column(name = "is_smok")
	private Boolean smok;
	@Column(name = "smok_day")
	private Double smokDay;
	@Column(name = "smok_year")
	private Double smokYear;
	@Column(name = "is_smok_refuse_answer")
	private Boolean smokRefuseAnswer;
	@Column(name = "is_no_drink")
	private Boolean noDrink;
	@Column(name = "is_quit_drink")
	private Boolean quitDrink;
	@Column(name = "quit_drink_age")
	private Double quitDrinkAge;
	@Column(name = "is_often_drink")
	private Boolean oftenDrink;
	@Column(name = "often_drink_age")
	private Double oftenDrinkBeginAge;
	@Column(name = "is_beer")
	private Boolean beer;
	@Column(name = "beer_ml")
	private Double beerMl;
	@Column(name = "is_spirit")
	private Boolean spirit;
	@Column(name = "spirit_ml")
	private Double spiritMl;
	@Column(name = "is_red_wine")
	private Boolean redWine;
	@Column(name = "red_wine_ml")
	private Double redWineMl;
	@Column(name = "is_drink_refuse_answer")
	private Boolean drinkRefuseAnswer;
	@Column(name = "is_full_diet")
	private Boolean fullDiet;
	@Column(name = "is_vegetarian_diet")
	private Boolean vegetarianDiet;
	@Column(name = "is_lowfat_diet")
	private Boolean lowfatDiet;
	@Column(name = "is_diabetic_diet")
	private Boolean diabeticDiet;
	@Column(name = "is_diet_refuse_answer")
	private Boolean dietRefuseAnswer;
	@Column(name = "is_coffeer")
	private Boolean coffee;
	@Column(name = "cofee_weekly_drinks")
	private Double coffeeWeeklyDrinks;
	@Column(name = "coffee_every_time_drinks")
	private Double coffeeEveryTimeDrinks;
	@Column(name = "is_strong_tea")
	private Boolean strongTea;
	@Column(name = "strong_tea_weekly_drinks")
	private Double strongTeaWeeklyDrinks;
	@Column(name = "strong_tea_every_time_drinks")
	private Double strongTeaEveryTimeDrinks;
	@Column(name = "is_sodas")
	private Boolean sodas;
	@Column(name = "sodas_weekly_drinks")
	private Double sodasWeeklyDrinks;
	@Column(name = "sodas_every_time_drinks")
	private Double sodasEveryTimeDrinks;
	@Column(name = "is_milk")
	private Boolean milk;
	@Column(name = "milk_weekly_drinks")
	private Double milkWeeklyDrinks;
	@Column(name = "milk_every_time_drinks")
	private Double milkEveryTimeDrinks;
	@Column(name = "is_other_drink")
	private Boolean otherDrink;
	@Column(name = "other_drink_desc", columnDefinition = "text")
	private String otherDrinkDesc;
	@Column(name = "other_drink_weekly_drinks")
	private Double otherDrinkWeeklyDrinks;
	@Column(name = "other_drink_every_time_drinks")
	private Double otherDrinkEveryTimeDrinks;
	@Column(name = "is_dadra")
	private Boolean dadra;
	@Column(name = "is_sleep_less_than_four_hours")
	private Boolean sleepLessThanFourHours;
	@Column(name = "is_sleep_four_to_six_hours")
	private Boolean sleepFourToSixHours;
	@Column(name = "is_sleep_seven_to_eight_hours")
	private Boolean sleepSevenToEightHours;
	@Column(name = "is_sleep_nine_to_ten_hours")
	private Boolean sleepNineToTenHours;
	@Column(name = "is_sleep_more_than_ten_hours")
	private Boolean sleepMoreThanTenHours;
	@Column(name = "is_sleep_refuse_answer")
	private Boolean sleepRefuseAnswer;
	@Column(name = "is_insolation_little")
	private Boolean insolationLittle;
	@Column(name = "is_insolation_more_than_ten_min")
	private Boolean insolationMoreThanTenMin;
	@Column(name = "is_insolation_more_than_thirty_min")
	private Boolean insolationMoreThanThirtyMin;
	@Column(name = "is_temper_less_than_three")
	private Boolean temperLessThanThree;
	@Column(name = "is_temper_three_to_five")
	private Boolean temperThreeToFive;
	@Column(name = "is_temper_more_than_five")
	private Boolean temperMoreThanFive;
	@Column(name = "is_temper_never")
	private Boolean temperNever;
	@Column(name = "is_temper_refuse_answer")
	private Boolean temperRefuseAnswer;
	@Column(name = "is_temper_less_than_thirty_min")
	private Boolean temperLessThanThirtyMin;
	@Column(name = "is_temper_thirty_to_sixty_min")
	private Boolean temperThirtyToSixtyMin;
	@Column(name = "is_temper_more_than_sixty_min")
	private Boolean temperMoreThanSixtyMin;
	@Column(name = "is_walk")
	private Boolean walk;
	@Column(name = "is_skelp")
	private Boolean skelp;
	@Column(name = "is_jog")
	private Boolean jog;
	@Column(name = "is_dance")
	private Boolean dance;
	@Column(name = "is_shadowboxing")
	private Boolean shadowboxing;
	@Column(name = "is_stairs")
	private Boolean stairs;
	@Column(name = "is_fitness_equipment")
	private Boolean fitnessEquipment;
	@Column(name = "is_play_ball")
	private Boolean playBall;
	@Column(name = "is_swim")
	private Boolean swim;
	@Column(name = "is_cycling")
	private Boolean cycling;
	@Column(name = "is_temper_other")
	private Boolean temperOther;
	@Column(name = "temper_other_desc", columnDefinition = "text")
	private String temperOtherDesc;

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

	public Boolean getNoSmok() {
		return noSmok;
	}

	public void setNoSmok(Boolean noSmok) {
		this.noSmok = noSmok;
	}

	public Boolean getQuitSmok() {
		return quitSmok;
	}

	public void setQuitSmok(Boolean quitSmok) {
		this.quitSmok = quitSmok;
	}

	public Long getQuitSmokAge() {
		return quitSmokAge;
	}

	public void setQuitSmokAge(Long quitSmokAge) {
		this.quitSmokAge = quitSmokAge;
	}

	public Boolean getSmok() {
		return smok;
	}

	public void setSmok(Boolean smok) {
		this.smok = smok;
	}

	public Double getSmokDay() {
		return smokDay;
	}

	public void setSmokDay(Double smokDay) {
		this.smokDay = smokDay;
	}

	public Double getSmokYear() {
		return smokYear;
	}

	public void setSmokYear(Double smokYear) {
		this.smokYear = smokYear;
	}

	public Boolean getSmokRefuseAnswer() {
		return smokRefuseAnswer;
	}

	public void setSmokRefuseAnswer(Boolean smokRefuseAnswer) {
		this.smokRefuseAnswer = smokRefuseAnswer;
	}

	public Boolean getNoDrink() {
		return noDrink;
	}

	public void setNoDrink(Boolean noDrink) {
		this.noDrink = noDrink;
	}

	public Boolean getQuitDrink() {
		return quitDrink;
	}

	public void setQuitDrink(Boolean quitDrink) {
		this.quitDrink = quitDrink;
	}

	public Double getQuitDrinkAge() {
		return quitDrinkAge;
	}

	public void setQuitDrinkAge(Double quitDrinkAge) {
		this.quitDrinkAge = quitDrinkAge;
	}

	public Boolean getOftenDrink() {
		return oftenDrink;
	}

	public void setOftenDrink(Boolean oftenDrink) {
		this.oftenDrink = oftenDrink;
	}

	public Double getOftenDrinkBeginAge() {
		return oftenDrinkBeginAge;
	}

	public void setOftenDrinkBeginAge(Double oftenDrinkBeginAge) {
		this.oftenDrinkBeginAge = oftenDrinkBeginAge;
	}

	public Boolean getBeer() {
		return beer;
	}

	public void setBeer(Boolean beer) {
		this.beer = beer;
	}

	public Double getBeerMl() {
		return beerMl;
	}

	public void setBeerMl(Double beerMl) {
		this.beerMl = beerMl;
	}

	public Boolean getSpirit() {
		return spirit;
	}

	public void setSpirit(Boolean spirit) {
		this.spirit = spirit;
	}

	public Double getSpiritMl() {
		return spiritMl;
	}

	public void setSpiritMl(Double spiritMl) {
		this.spiritMl = spiritMl;
	}

	public Boolean getRedWine() {
		return redWine;
	}

	public void setRedWine(Boolean redWine) {
		this.redWine = redWine;
	}

	public Double getRedWineMl() {
		return redWineMl;
	}

	public void setRedWineMl(Double redWineMl) {
		this.redWineMl = redWineMl;
	}

	public Boolean getDrinkRefuseAnswer() {
		return drinkRefuseAnswer;
	}

	public void setDrinkRefuseAnswer(Boolean drinkRefuseAnswer) {
		this.drinkRefuseAnswer = drinkRefuseAnswer;
	}

	public Boolean getFullDiet() {
		return fullDiet;
	}

	public void setFullDiet(Boolean fullDiet) {
		this.fullDiet = fullDiet;
	}

	public Boolean getVegetarianDiet() {
		return vegetarianDiet;
	}

	public void setVegetarianDiet(Boolean vegetarianDiet) {
		this.vegetarianDiet = vegetarianDiet;
	}

	public Boolean getLowfatDiet() {
		return lowfatDiet;
	}

	public void setLowfatDiet(Boolean lowfatDiet) {
		this.lowfatDiet = lowfatDiet;
	}

	public Boolean getDiabeticDiet() {
		return diabeticDiet;
	}

	public void setDiabeticDiet(Boolean diabeticDiet) {
		this.diabeticDiet = diabeticDiet;
	}

	public Boolean getDietRefuseAnswer() {
		return dietRefuseAnswer;
	}

	public void setDietRefuseAnswer(Boolean dietRefuseAnswer) {
		this.dietRefuseAnswer = dietRefuseAnswer;
	}

	public Boolean getCoffee() {
		return coffee;
	}

	public void setCoffee(Boolean coffee) {
		this.coffee = coffee;
	}

	public Double getCoffeeWeeklyDrinks() {
		return coffeeWeeklyDrinks;
	}

	public void setCoffeeWeeklyDrinks(Double coffeeWeeklyDrinks) {
		this.coffeeWeeklyDrinks = coffeeWeeklyDrinks;
	}

	public Double getCoffeeEveryTimeDrinks() {
		return coffeeEveryTimeDrinks;
	}

	public void setCoffeeEveryTimeDrinks(Double coffeeEveryTimeDrinks) {
		this.coffeeEveryTimeDrinks = coffeeEveryTimeDrinks;
	}

	public Boolean getStrongTea() {
		return strongTea;
	}

	public void setStrongTea(Boolean strongTea) {
		this.strongTea = strongTea;
	}

	public Double getStrongTeaWeeklyDrinks() {
		return strongTeaWeeklyDrinks;
	}

	public void setStrongTeaWeeklyDrinks(Double strongTeaWeeklyDrinks) {
		this.strongTeaWeeklyDrinks = strongTeaWeeklyDrinks;
	}

	public Double getStrongTeaEveryTimeDrinks() {
		return strongTeaEveryTimeDrinks;
	}

	public void setStrongTeaEveryTimeDrinks(Double strongTeaEveryTimeDrinks) {
		this.strongTeaEveryTimeDrinks = strongTeaEveryTimeDrinks;
	}

	public Boolean getSodas() {
		return sodas;
	}

	public void setSodas(Boolean sodas) {
		this.sodas = sodas;
	}

	public Double getSodasWeeklyDrinks() {
		return sodasWeeklyDrinks;
	}

	public void setSodasWeeklyDrinks(Double sodasWeeklyDrinks) {
		this.sodasWeeklyDrinks = sodasWeeklyDrinks;
	}

	public Double getSodasEveryTimeDrinks() {
		return sodasEveryTimeDrinks;
	}

	public void setSodasEveryTimeDrinks(Double sodasEveryTimeDrinks) {
		this.sodasEveryTimeDrinks = sodasEveryTimeDrinks;
	}

	public Boolean getMilk() {
		return milk;
	}

	public void setMilk(Boolean milk) {
		this.milk = milk;
	}

	public Double getMilkWeeklyDrinks() {
		return milkWeeklyDrinks;
	}

	public void setMilkWeeklyDrinks(Double milkWeeklyDrinks) {
		this.milkWeeklyDrinks = milkWeeklyDrinks;
	}

	public Double getMilkEveryTimeDrinks() {
		return milkEveryTimeDrinks;
	}

	public void setMilkEveryTimeDrinks(Double milkEveryTimeDrinks) {
		this.milkEveryTimeDrinks = milkEveryTimeDrinks;
	}

	public Boolean getOtherDrink() {
		return otherDrink;
	}

	public void setOtherDrink(Boolean otherDrink) {
		this.otherDrink = otherDrink;
	}

	public String getOtherDrinkDesc() {
		return otherDrinkDesc;
	}

	public void setOtherDrinkDesc(String otherDrinkDesc) {
		this.otherDrinkDesc = otherDrinkDesc;
	}

	public Double getOtherDrinkWeeklyDrinks() {
		return otherDrinkWeeklyDrinks;
	}

	public void setOtherDrinkWeeklyDrinks(Double otherDrinkWeeklyDrinks) {
		this.otherDrinkWeeklyDrinks = otherDrinkWeeklyDrinks;
	}

	public Double getOtherDrinkEveryTimeDrinks() {
		return otherDrinkEveryTimeDrinks;
	}

	public void setOtherDrinkEveryTimeDrinks(Double otherDrinkEveryTimeDrinks) {
		this.otherDrinkEveryTimeDrinks = otherDrinkEveryTimeDrinks;
	}

	public Boolean getDadra() {
		return dadra;
	}

	public void setDadra(Boolean dadra) {
		this.dadra = dadra;
	}

	public Boolean getSleepLessThanFourHours() {
		return sleepLessThanFourHours;
	}

	public void setSleepLessThanFourHours(Boolean sleepLessThanFourHours) {
		this.sleepLessThanFourHours = sleepLessThanFourHours;
	}

	public Boolean getSleepFourToSixHours() {
		return sleepFourToSixHours;
	}

	public void setSleepFourToSixHours(Boolean sleepFourToSixHours) {
		this.sleepFourToSixHours = sleepFourToSixHours;
	}

	public Boolean getSleepSevenToEightHours() {
		return sleepSevenToEightHours;
	}

	public void setSleepSevenToEightHours(Boolean sleepSevenToEightHours) {
		this.sleepSevenToEightHours = sleepSevenToEightHours;
	}

	public Boolean getSleepNineToTenHours() {
		return sleepNineToTenHours;
	}

	public void setSleepNineToTenHours(Boolean sleepNineToTenHours) {
		this.sleepNineToTenHours = sleepNineToTenHours;
	}

	public Boolean getSleepMoreThanTenHours() {
		return sleepMoreThanTenHours;
	}

	public void setSleepMoreThanTenHours(Boolean sleepMoreThanTenHours) {
		this.sleepMoreThanTenHours = sleepMoreThanTenHours;
	}

	public Boolean getSleepRefuseAnswer() {
		return sleepRefuseAnswer;
	}

	public void setSleepRefuseAnswer(Boolean sleepRefuseAnswer) {
		this.sleepRefuseAnswer = sleepRefuseAnswer;
	}

	public Boolean getInsolationLittle() {
		return insolationLittle;
	}

	public void setInsolationLittle(Boolean insolationLittle) {
		this.insolationLittle = insolationLittle;
	}

	public Boolean getInsolationMoreThanTenMin() {
		return insolationMoreThanTenMin;
	}

	public void setInsolationMoreThanTenMin(Boolean insolationMoreThanTenMin) {
		this.insolationMoreThanTenMin = insolationMoreThanTenMin;
	}

	public Boolean getInsolationMoreThanThirtyMin() {
		return insolationMoreThanThirtyMin;
	}

	public void setInsolationMoreThanThirtyMin(Boolean insolationMoreThanThirtyMin) {
		this.insolationMoreThanThirtyMin = insolationMoreThanThirtyMin;
	}

	public Boolean getTemperLessThanThree() {
		return temperLessThanThree;
	}

	public void setTemperLessThanThree(Boolean temperLessThanThree) {
		this.temperLessThanThree = temperLessThanThree;
	}

	public Boolean getTemperThreeToFive() {
		return temperThreeToFive;
	}

	public void setTemperThreeToFive(Boolean temperThreeToFive) {
		this.temperThreeToFive = temperThreeToFive;
	}

	public Boolean getTemperMoreThanFive() {
		return temperMoreThanFive;
	}

	public void setTemperMoreThanFive(Boolean temperMoreThanFive) {
		this.temperMoreThanFive = temperMoreThanFive;
	}

	public Boolean getTemperNever() {
		return temperNever;
	}

	public void setTemperNever(Boolean temperNever) {
		this.temperNever = temperNever;
	}

	public Boolean getTemperRefuseAnswer() {
		return temperRefuseAnswer;
	}

	public void setTemperRefuseAnswer(Boolean temperRefuseAnswer) {
		this.temperRefuseAnswer = temperRefuseAnswer;
	}

	public Boolean getTemperLessThanThirtyMin() {
		return temperLessThanThirtyMin;
	}

	public void setTemperLessThanThirtyMin(Boolean temperLessThanThirtyMin) {
		this.temperLessThanThirtyMin = temperLessThanThirtyMin;
	}

	public Boolean getTemperThirtyToSixtyMin() {
		return temperThirtyToSixtyMin;
	}

	public void setTemperThirtyToSixtyMin(Boolean temperThirtyToSixtyMin) {
		this.temperThirtyToSixtyMin = temperThirtyToSixtyMin;
	}

	public Boolean getTemperMoreThanSixtyMin() {
		return temperMoreThanSixtyMin;
	}

	public void setTemperMoreThanSixtyMin(Boolean temperMoreThanSixtyMin) {
		this.temperMoreThanSixtyMin = temperMoreThanSixtyMin;
	}

	public Boolean getWalk() {
		return walk;
	}

	public void setWalk(Boolean walk) {
		this.walk = walk;
	}

	public Boolean getSkelp() {
		return skelp;
	}

	public void setSkelp(Boolean skelp) {
		this.skelp = skelp;
	}

	public Boolean getJog() {
		return jog;
	}

	public void setJog(Boolean jog) {
		this.jog = jog;
	}

	public Boolean getDance() {
		return dance;
	}

	public void setDance(Boolean dance) {
		this.dance = dance;
	}

	public Boolean getShadowboxing() {
		return shadowboxing;
	}

	public void setShadowboxing(Boolean shadowboxing) {
		this.shadowboxing = shadowboxing;
	}

	public Boolean getStairs() {
		return stairs;
	}

	public void setStairs(Boolean stairs) {
		this.stairs = stairs;
	}

	public Boolean getFitnessEquipment() {
		return fitnessEquipment;
	}

	public void setFitnessEquipment(Boolean fitnessEquipment) {
		this.fitnessEquipment = fitnessEquipment;
	}

	public Boolean getPlayBall() {
		return playBall;
	}

	public void setPlayBall(Boolean playBall) {
		this.playBall = playBall;
	}

	public Boolean getSwim() {
		return swim;
	}

	public void setSwim(Boolean swim) {
		this.swim = swim;
	}

	public Boolean getCycling() {
		return cycling;
	}

	public void setCycling(Boolean cycling) {
		this.cycling = cycling;
	}

	public Boolean getTemperOther() {
		return temperOther;
	}

	public void setTemperOther(Boolean temperOther) {
		this.temperOther = temperOther;
	}

	public String getTemperOtherDesc() {
		return temperOtherDesc;
	}

	public void setTemperOtherDesc(String temperOtherDesc) {
		this.temperOtherDesc = temperOtherDesc;
	}

	public String getRealName() {
		return realName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
}
