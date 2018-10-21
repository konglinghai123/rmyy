package com.ewcms.yjk.zd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;

/**
 * 通用名字典库
 * 
 * <ul>
 * <li>commonName:药品通用名称</li>
 * <li>spell:通用名称拼音</li>
 * <li>spellSimplify:用名称拼音简写</li>
 * <li>deleted:是否删除</li>
 * </ul>
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "zd_common_name",
	indexes = {
		@Index(name = "idx_zd_common_name_spell", columnList = "spell"),
		@Index(name = "idx_zd_common_name_spell_simplify", columnList = "spell_simplify")
	}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_zd_common_name_id", allocationSize = 1)
public class CommonName extends BaseSequenceEntity<Long> implements LogicDeleteable {
	private static final long serialVersionUID = -2489387145827618904L;

	@NotEmpty(message = "{not.null}")
	@Column(name = "common_name", nullable = false, unique = true)
	private String commonName;
	
	@Column(name = "spell", nullable = false)
	private String spell;
	
	@Column(name = "spell_simplify", nullable = false)
	private String spellSimplify;	
	
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
    
    
    public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
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
