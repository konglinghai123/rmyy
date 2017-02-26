package com.ewcms.trace.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.AbstractEntity;

/**
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "T_Trace")
public class Trace extends AbstractEntity<String>{

	private static final long serialVersionUID = 4228413222452893821L;

	@Column(name = "id")
	private String id;
	@Id
	@Column(name = "CPID")
	private String cpid;
	@Column(name = "产品序号")
	private String productId;
	@Column(name = "产品编号")
	private String productNumber;
	@Column(name = "产品名称")
	private String productName;
	@Column(name = "产品分类")
	private String productCategory;
	@Column(name = "产品组别")
	private String productGroup;
	@Column(name = "单位")
	private String unit;
	@Column(name = "拼音")
	private String pinYin;
	@Column(name = "规格")
	private String standard;
	@Column(name = "型号")
	private String model;
	@Column(name = "供应商")
	private String supplier;
	@Column(name = "生产厂家")
	private String manufacturer;
	@Column(name = "最小数量")
	private Integer minQuantity;
	@Column(name = "最大数量")
	private Integer maxQuantity;
	@Column(name = "出货单价")
	private BigDecimal unitPrice;
	@Column(name = "进货单价")
	private BigDecimal purchasePrice;
	@Column(name = "最低单价")
	private BigDecimal minPrice;
	@Column(name = "最高单价")
	private BigDecimal maxPrice;
	@Column(name = "备注")
	private String remark;
	@Column(name = "注册证号")
	private String regNumber;
	@Column(name = "state")
	private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dTime;
	@Column(name = "UserID")
	private String userId;
	@Column(name = "DepID")
	private String depId;
	@Column(name = "b1")
	private byte[] b1;
	@Column(name = "b2")
	private byte[] b2;
	@Column(name = "b3")
	private byte[] b3;
	@Column(name = "b4")
	private byte[] b4;
	@Column(name = "b5")
	private byte[] b5;
	@Column(name = "b6")
	private byte[] b6;
	@Column(name = "b7")
	private byte[] b7;
	@Column(name = "b8")
	private byte[] b8;
	@Column(name = "b9")
	private byte[] b9;
	@Column(name = "b10")
	private byte[] b10;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d1")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d1;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d2")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d2;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d3")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d3;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d4")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d4;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d5")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d5;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d6")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d6;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d7")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d7;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d8")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d8;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d9")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d9;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "d10")
	@Temporal(TemporalType.TIMESTAMP)
	private Date d10;
	@Column(name = "HISDM")
	private String hisdm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "InDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inDateTime;
	@Column(name = "InRemar")
	private String inRemar;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OutDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date outDateTime;
	@Column(name = "OutRemark")
	private String outRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "OrderDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDateTime;
	@Column(name = "OrderRemark")
	private String orderRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CollarDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date collarDateTime;
	@Column(name = "CollarRemark")
	private String collarRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ChangeDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changeDateTime;
	@Column(name = "ChangeRemark")
	private String changeRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "TransferDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transferDateTime;
	@Column(name = "TransferRemark")
	private String transferRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ReturnDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDateTime;
	@Column(name = "returnRemark")
	private String returnRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "TollDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tollDateTime;
	@Column(name = "TollRemark")
	private String tollRemark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getdTime() {
		return dTime;
	}

	public void setdTime(Date dTime) {
		this.dTime = dTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	@JSONField(serialize = false)
	public byte[] getB1() {
		return b1;
	}

	public void setB1(byte[] b1) {
		this.b1 = b1;
	}

	@JSONField(serialize = false)
	public byte[] getB2() {
		return b2;
	}

	public void setB2(byte[] b2) {
		this.b2 = b2;
	}

	@JSONField(serialize = false)
	public byte[] getB3() {
		return b3;
	}

	public void setB3(byte[] b3) {
		this.b3 = b3;
	}

	@JSONField(serialize = false)
	public byte[] getB4() {
		return b4;
	}

	public void setB4(byte[] b4) {
		this.b4 = b4;
	}

	@JSONField(serialize = false)
	public byte[] getB5() {
		return b5;
	}

	public void setB5(byte[] b5) {
		this.b5 = b5;
	}

	@JSONField(serialize = false)
	public byte[] getB6() {
		return b6;
	}

	public void setB6(byte[] b6) {
		this.b6 = b6;
	}

	@JSONField(serialize = false)
	public byte[] getB7() {
		return b7;
	}

	public void setB7(byte[] b7) {
		this.b7 = b7;
	}

	@JSONField(serialize = false)
	public byte[] getB8() {
		return b8;
	}

	public void setB8(byte[] b8) {
		this.b8 = b8;
	}

	@JSONField(serialize = false)
	public byte[] getB9() {
		return b9;
	}

	public void setB9(byte[] b9) {
		this.b9 = b9;
	}

	@JSONField(serialize = false)
	public byte[] getB10() {
		return b10;
	}

	public void setB10(byte[] b10) {
		this.b10 = b10;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD2() {
		return d2;
	}

	public void setD2(Date d2) {
		this.d2 = d2;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD3() {
		return d3;
	}

	public void setD3(Date d3) {
		this.d3 = d3;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD4() {
		return d4;
	}

	public void setD4(Date d4) {
		this.d4 = d4;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD5() {
		return d5;
	}

	public void setD5(Date d5) {
		this.d5 = d5;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD6() {
		return d6;
	}

	public void setD6(Date d6) {
		this.d6 = d6;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD7() {
		return d7;
	}

	public void setD7(Date d7) {
		this.d7 = d7;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD8() {
		return d8;
	}

	public void setD8(Date d8) {
		this.d8 = d8;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD9() {
		return d9;
	}

	public void setD9(Date d9) {
		this.d9 = d9;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getD10() {
		return d10;
	}

	public void setD10(Date d10) {
		this.d10 = d10;
	}

	public String getHisdm() {
		return hisdm;
	}

	public void setHisdm(String hisdm) {
		this.hisdm = hisdm;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getInDateTime() {
		return inDateTime;
	}

	public void setInDateTime(Date inDateTime) {
		this.inDateTime = inDateTime;
	}

	public String getInRemar() {
		return inRemar;
	}

	public void setInRemar(String inRemar) {
		this.inRemar = inRemar;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOutDateTime() {
		return outDateTime;
	}

	public void setOutDateTime(Date outDateTime) {
		this.outDateTime = outDateTime;
	}

	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(Date orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCollarDateTime() {
		return collarDateTime;
	}

	public void setCollarDateTime(Date collarDateTime) {
		this.collarDateTime = collarDateTime;
	}

	public String getCollarRemark() {
		return collarRemark;
	}

	public void setCollarRemark(String collarRemark) {
		this.collarRemark = collarRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getChangeDateTime() {
		return changeDateTime;
	}

	public void setChangeDateTime(Date changeDateTime) {
		this.changeDateTime = changeDateTime;
	}

	public String getChangeRemark() {
		return changeRemark;
	}

	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getTransferDateTime() {
		return transferDateTime;
	}

	public void setTransferDateTime(Date transferDateTime) {
		this.transferDateTime = transferDateTime;
	}

	public String getTransferRemark() {
		return transferRemark;
	}

	public void setTransferRemark(String transferRemark) {
		this.transferRemark = transferRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(Date returnDateTime) {
		this.returnDateTime = returnDateTime;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getTollDateTime() {
		return tollDateTime;
	}

	public void setTollDateTime(Date tollDateTime) {
		this.tollDateTime = tollDateTime;
	}

	public String getTollRemark() {
		return tollRemark;
	}

	public void setTollRemark(String tollRemark) {
		this.tollRemark = tollRemark;
	}

	
}
