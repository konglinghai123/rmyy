package com.ewcms.yjk.zd.commonname.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 通用名字典库
 * 
 * <ul>
 * <li>commonName:药品通用名</li>
 * <li>matchNumber:匹配编号（软件ID）</li>
 * <li>drugCategory:类别</li>
 * <li>spell:通用名拼音</li>
 * <li>spellSimplify:通用名简拼</li>
 * <li>enabled:是否启用</li>
 * <li>deleted:是否删除</li>
 * <li>bidCommonName:省招标通用名</li>
 * <li>chemicalBigCategory:化药大类</li>
 * <li>chemicalSubCategory:化药小类</li>
 * <li>antibacterialsed:是否抗菌药物</li>
 * <li>antibacterialseCategory:抗菌药物类别</li>
 * <li>antibacterialseNumber:抗菌药物编号</li>
 * </ul>
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "zd_common_name",
	indexes = {
		@Index(name = "idx_zd_common_name_spell", columnList = "spell"),
		@Index(name = "idx_zd_common_name_spell_simplify", columnList = "spell_simplify")
	}, 
	uniqueConstraints = {
		@UniqueConstraint(name = "unique_zd_common_name", columnNames = {"common_name", "match_number"}) 
	}
)
@SequenceGenerator(name="seq", sequenceName="seq_zd_common_name_id", allocationSize = 1)
public class CommonName extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -2489387145827618904L;

	@NotEmpty(message = "{not.null}")
	@Column(name = "common_name", nullable = false)
	private String commonName;
	
	@NotEmpty(message = "{not.null}")
	@Column(name = "match_number", nullable = false)
	private String matchNumber;
	
	@Enumerated(EnumType.STRING)
	private DrugCategoryEnum drugCategory = DrugCategoryEnum.H;
	
	@Column(name = "spell", nullable = false)
	private String spell;
	
	@Column(name = "spell_simplify", nullable = false)
	private String spellSimplify;	
	
	@Column(name = "is_enabled")
	private Boolean enabled = Boolean.TRUE;
	
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    
	@Column(name = "bid_common_name")
	private String bidCommonName;
	
	@Column(name = "chemical_big_category")
	private String chemicalBigCategory;	
	
	@Column(name = "chemical_sub_category")
	private String chemicalSubCategory;	
	
	@Column(name = "is_antibacterialsed")
	private Boolean antibacterialsed = Boolean.FALSE;

	@Column(name = "antibacterialse_category")
	private String antibacterialseCategory;	
	
	@Column(name = "antibacterialse_number")
	private String antibacterialseNumber;	
	
    public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getBidCommonName() {
		return bidCommonName;
	}

	public void setBidCommonName(String bidCommonName) {
		this.bidCommonName = bidCommonName;
	}

	public String getAntibacterialseCategory() {
		return antibacterialseCategory;
	}

	public void setAntibacterialseCategory(String antibacterialseCategory) {
		this.antibacterialseCategory = antibacterialseCategory;
	}

	public String getAntibacterialseNumber() {
		return antibacterialseNumber;
	}

	public void setAntibacterialseNumber(String antibacterialseNumber) {
		this.antibacterialseNumber = antibacterialseNumber;
	}

	public String getDrugCategoryInfo() {
		return drugCategory == null ? "" : drugCategory.getInfo();
	}

	public DrugCategoryEnum getDrugCategory() {
		return drugCategory;
	}

	public void setDrugCategory(DrugCategoryEnum drugCategory) {
		this.drugCategory = drugCategory;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSpellSimplify() {
		return spellSimplify;
	}

	public void setSpellSimplify(String spellSimplify) {
		this.spellSimplify = spellSimplify;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(String matchNumber) {
		this.matchNumber = matchNumber;
	}

	public String getChemicalBigCategory() {
		return chemicalBigCategory;
	}

	public void setChemicalBigCategory(String chemicalBigCategory) {
		this.chemicalBigCategory = chemicalBigCategory;
	}

	public String getChemicalSubCategory() {
		return chemicalSubCategory;
	}

	public void setChemicalSubCategory(String chemicalSubCategory) {
		this.chemicalSubCategory = chemicalSubCategory;
	}

	public Boolean getAntibacterialsed() {
		return antibacterialsed;
	}

	public void setAntibacterialsed(Boolean antibacterialsed) {
		this.antibacterialsed = antibacterialsed;
	}

	@Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
    
    
}
