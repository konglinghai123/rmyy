package com.ewcms.yjk.zd.commonname.service;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.util.PinYin;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.repository.HospitalContentsRepository;
import com.google.common.collect.Lists;

/**
 * @author zhoudongchu
 */
@Service
public class HospitalContentsService extends BaseService<HospitalContents, Long> {
	@Autowired
	private CommonNameContentsService commonNameContentsService;
	@Autowired
	private CommonNameService commonNameService;
	@Autowired
	private AdministrationService administrationService;

	private HospitalContentsRepository getHospitalContentsRepository() {
		return (HospitalContentsRepository) baseRepository;
	}

	public List<HospitalContents> findByCommonIdAndDeletedFalse(Long commonId) {
		return getHospitalContentsRepository().findByCommonIdAndDeletedFalse(commonId);
	}

	public Page<HospitalContents> findByCommonIdInAndDeletedFalse(List<Long> commonIds, Pageable pageable){
		return getHospitalContentsRepository().findByCommonIdInAndDeletedFalse(commonIds, pageable);
	}
	
	public void deleteAllHospitalContents(){
		getHospitalContentsRepository().deleteAllHospitalContents();
	}
	
	public List<HospitalContents> findByCommonCommonNameAndCommonAdministrationIdAndCommonDrugCategoryAndCommonNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndDeletedFalse(String extractCommonName, Long administrationId,DrugCategoryEnum drugCategory,String number,String pill,String manufacturer,String commonName,String specifications,String amount){
		return getHospitalContentsRepository().findByCommonCommonNameAndCommonAdministrationIdAndCommonDrugCategoryAndCommonNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndDeletedFalse(extractCommonName, administrationId, drugCategory, number, pill, manufacturer, commonName, specifications, amount);
	}
	/**
	 * 根据申报药品查找当前院药品目录在用医院药品集合
	 * 
	 * @param commonNameContentsId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<HospitalContents> matchByCommonNameContentsId(Long commonNameContentsId, Pageable pageable) {
		CommonNameContents commonNameContentsvo = commonNameContentsService.findOne(commonNameContentsId);
		List<CommonName> commonNames = commonNameService.findByNumberAndAdministrationIdAndDrugCategory(
				commonNameContentsvo.getCommon().getNumber(),
				commonNameContentsvo.getCommon().getAdministration().getId(),
				commonNameContentsvo.getCommon().getDrugCategory());
		
		List<Long> commonNameIds = Collections3.extractToList(commonNames, "id");
		
		return findByCommonIdInAndDeletedFalse(commonNameIds, pageable);
	}

	@Override
	public HospitalContents save(HospitalContents m) {
		PinYin.initSpell(m);
		return super.save(m);
	}

	@Override
	public HospitalContents update(HospitalContents m) {
		PinYin.initSpell(m);
		m.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		return super.update(m);
	}

	public List<Integer> importExcel(InputStream in,Boolean isDisabledOriginalData) {
		List<Integer> noSave = Lists.newArrayList();

		try {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int records = sheet.getLastRowNum();
			if(isDisabledOriginalData){//如果作废数据库以前数据，将数据库所有数据删除标志设为true
				deleteAllHospitalContents();
			}
			// 获得列名，为第0行位置
			HSSFRow rows = sheet.getRow(0);
			int cols = rows.getLastCellNum() - 1;
			String columnNames[] = new String[cols + 1];

			for (int i = 0; i <= cols; i++) {
				columnNames[i] = rows.getCell(i).getStringCellValue().trim();
			}

			// 获得数据，数据从第1行开始
			for (int i = 1; i <= records; i++) {
				String extactCommonName = "", number = "",pill = "",manufacturer = "",hospitalCommonName="",specifications="",amount="";
				DrugCategoryEnum drugCategory = DrugCategoryEnum.H;
				Long administrationId = 1L;
				HospitalContents hospitalContents = new HospitalContents();
				rows = sheet.getRow(i);
				try {
					for (int j = 0; j <= cols; j++) {
						if (columnNames[j].equals("药品代码")) {
							try {
								hospitalContents.setDrugCode(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								hospitalContents.setDrugCode(String.valueOf(drugCode.longValue()));
							}
						} else if (columnNames[j].equals("药品通用名")) {
							hospitalContents.setCommonName(rows.getCell(j).getStringCellValue().trim());
							hospitalCommonName = rows.getCell(j).getStringCellValue().trim();
							PinYin.initSpell(hospitalContents);
						} else if (columnNames[j].equals("给药途径")) {
							try {
								Double administrationIdTemp = rows.getCell(j).getNumericCellValue();
								administrationId = administrationIdTemp.longValue();
							} catch (Exception e) {
								// administrationId = Long.valueOf(rows.getCell(j).getStringCellValue().trim());
							}
						} else if (columnNames[j].equals("提取通用名")) {
							extactCommonName = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("编号")) {
							try {
								Double numberTemp = rows.getCell(j).getNumericCellValue();
								number = String.valueOf(numberTemp.longValue());
							} catch (Exception e) {
								number = rows.getCell(j).getStringCellValue().trim();
							}
						} else if (columnNames[j].equals("剂型")) {
							hospitalContents.setPill(rows.getCell(j).getStringCellValue().trim());
							pill = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("规格*数量")) {
							String specNumber = rows.getCell(j).getStringCellValue().trim();
							if (EmptyUtil.isStringNotEmpty(specNumber)) {
								String[] tmp = specNumber.split("\\*");
								if (tmp.length == 2) {
									hospitalContents.setSpecifications(tmp[0]);
									hospitalContents.setAmount(tmp[1]);
									specifications = tmp[0];
									amount = tmp[1];
								}
							}
						} else if (columnNames[j].equals("生产企业")) {
							hospitalContents.setManufacturer(rows.getCell(j).getStringCellValue().trim());
							manufacturer = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("目录分类")) {
							hospitalContents.setContentCategory(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("备注")) {
							hospitalContents.setRemark(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("药品分类大类")) {
							hospitalContents.setDrugMajor(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("药品分类")) {
							hospitalContents.setDrugCategory(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("原备注")) {
							hospitalContents.setOldRemark(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("配送公司")) {
							hospitalContents.setDiscom(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("原类别")) {
							hospitalContents.setOriginalCategory(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("医保等信息")) {
							hospitalContents.setMedicalInfo(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("原质量层次")) {
							hospitalContents.setQualityLevel(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("类别")) {
							String drugCategoryValue = rows.getCell(j).getStringCellValue().trim();
							drugCategory = DrugCategoryEnum.valueOf(drugCategoryValue);
						}else if (columnNames[j].equals("备注1")) {
							hospitalContents.setRemark1(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注2")) {
							hospitalContents.setRemark2(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注3")) {
							hospitalContents.setRemark3(rows.getCell(j).getStringCellValue().trim());
						}
					}
					if(!isDisabledOriginalData){//增量导入，需要按照提取通用名，给药途径，编号，药品类型，院用目录通用名，生产企业，剂型,规格，包装数量查重，重复记录的不保存
						List<HospitalContents> repeatList = findByCommonCommonNameAndCommonAdministrationIdAndCommonDrugCategoryAndCommonNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndDeletedFalse(extactCommonName, administrationId, drugCategory, number, pill, manufacturer, hospitalCommonName,specifications,amount);
						if(EmptyUtil.isCollectionNotEmpty(repeatList)){
							noSave.add(i + 1);
							continue;
						}
					}
					List<CommonName> commonNameList = commonNameService
							.findByCommonNameAndNumberAndAdministrationIdAndDrugCategory(extactCommonName, number,
									administrationId, drugCategory);
					CommonName commonName = null;
					if (EmptyUtil.isCollectionEmpty(commonNameList)) {
						Administration administration = administrationService.findOne(administrationId);
						if (!EmptyUtil.isNull(administration)) {
							commonName = new CommonName();
							commonName.setCommonName(extactCommonName);
							commonName.setAdministration(administration);
							commonName.setDeleted(Boolean.FALSE);
							commonName.setDrugCategory(drugCategory);
							commonName.setEnabled(Boolean.TRUE);
							commonName.setNumber(number);
							commonNameService.saveAndFlush(commonName);
						} else {
							noSave.add(i + 1);
						}
					} else {
						commonName = commonNameList.get(0);
					}

					if (commonName != null) {
						hospitalContents.setCommon(commonName);
						super.saveAndFlush(hospitalContents);
					}
				} catch (Exception e) {
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
		} finally {

		}
		return noSave;
	}

}
