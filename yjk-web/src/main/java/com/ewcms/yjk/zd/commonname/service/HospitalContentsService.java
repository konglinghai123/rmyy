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

	public List<HospitalContents> findByCommonIdInInAndAdministrationIdAndDeletedFalse(List<Long> commonIds, Long administrationId){
		return getHospitalContentsRepository().findByCommonIdInInAndAdministrationIdAndDeletedFalse(commonIds, administrationId);
	}
	
	public void deleteAllHospitalContents(){
		getHospitalContentsRepository().deleteAllHospitalContents();
	}
	
	public HospitalContents findByBidDrugIdAndDeletedFalse(String bidDrugId){
		return getHospitalContentsRepository().findByBidDrugIdAndDeletedFalse(bidDrugId);
	}
	
	/**
	 * 查询申报类型,0为新增通用名,其他为新增规格/剂型
	 * 
	 * @param commonIds 通用名编号集合
	 * @return
	 */
	public Long countHospitalContents(List<Long> commonIds) {
		return getHospitalContentsRepository().countHospitalContents(commonIds);
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
		List<CommonName> commonNames = commonNameService.findByMatchNumber(commonNameContentsvo.getCommon().getMatchNumber());

		List<Long> commonNameIds = Collections3.extractToList(commonNames, "id");
		//return getHospitalContentsRepository().findByCommonIdInInAndAdministrationIdAndDeletedFalse(commonNameIds,commonNameContentsvo.getAdministration().getId(), pageable);
		return getHospitalContentsRepository().findByCommonIdInInAndDeletedFalse(commonNameIds, pageable);
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
	public List<String> findDistinctProjectName(){
		return getHospitalContentsRepository().findDistinctProjectName();
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
				String matchNumber = "", extractCommonName = "", bidCommonName = "", antibacterialseCategory = "", antibacterialseNumber = "", chemicalBigCategory = "" , chemicalSubCategory = "";
				Boolean antibacterialsed = Boolean.FALSE;
				DrugCategoryEnum dcEnum = DrugCategoryEnum.Q;
				HospitalContents hospitalContents = new HospitalContents();
				rows = sheet.getRow(i);
				try {
					for (int j = 0; j <= cols; j++) {
						 if (columnNames[j].equals("项目名称")) {
							 hospitalContents.setProjectName(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("药品代码")) {
							try {
								hospitalContents.setDrugCode(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								hospitalContents.setDrugCode(String.valueOf(drugCode.longValue()));
							}
						} else if (columnNames[j].equals("药品通用名")) {
							hospitalContents.setCommonName(rows.getCell(j).getStringCellValue().trim());
							PinYin.initSpell(hospitalContents);
						}  else if (columnNames[j].equals("软件通用名")) {
							extractCommonName = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("省招标通用名")) {
							bidCommonName = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("软件ID")) {
							try {
								matchNumber = rows.getCell(j).getStringCellValue().trim();
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								matchNumber = String.valueOf(drugCode.longValue());
							}
						} else if (columnNames[j].equals("给药途径")) {
							try {
								Double administrationId = rows.getCell(j).getNumericCellValue();
								if (administrationId == 0L) {
									hospitalContents.setAdministration(null);
								} else {
									Administration administration = administrationService.findOne(administrationId.longValue());
									hospitalContents.setAdministration(administration);
								}
							}catch (Exception e) {
								hospitalContents.setAdministration(null);
							}
						}  else if (columnNames[j].equals("剂型")) {
							hospitalContents.setPill(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("商品名")) {
							hospitalContents.setProductName(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("规格*数量")) {
							String specNumber = rows.getCell(j).getStringCellValue().trim();
							if (EmptyUtil.isStringNotEmpty(specNumber)) {
								String[] tmp = specNumber.split("\\*");
								if (tmp.length >1) {
									String tmpSpecfications = "";
									for(int k=0;k<tmp.length-1;k++){
										tmpSpecfications +=tmp[k]+"*";
									}
									tmpSpecfications = tmpSpecfications.substring(0, tmpSpecfications.length()-1);
									hospitalContents.setSpecifications(tmpSpecfications);
									hospitalContents.setAmount(tmp[tmp.length-1]);
								}else{
									hospitalContents.setSpecifications(specNumber);
									hospitalContents.setAmount("");
								}
							}
						} else if (columnNames[j].equals("生产企业")) {
							hospitalContents.setManufacturer(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("进口企业")) {
							hospitalContents.setImportEnterprise(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("中标价")) {
							try {
								Double bidPrice = rows.getCell(j).getNumericCellValue();
								if (bidPrice == 0L) {
									hospitalContents.setBidPrice(null);
								} else {
									hospitalContents.setBidPrice(bidPrice);
								}
							} catch (Exception e) {
								hospitalContents.setBidPrice(null);
							}
						}else if (columnNames[j].equals("抗菌药物类别")) {
							antibacterialseCategory = rows.getCell(j).getStringCellValue().trim();
							if(EmptyUtil.isStringNotEmpty(antibacterialseCategory)){
								antibacterialsed = Boolean.TRUE;
							}
						} else if (columnNames[j].equals("抗菌药物编号")) {
							try {
								antibacterialseNumber = rows.getCell(j).getStringCellValue().trim();
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								antibacterialseNumber = String.valueOf(drugCode.longValue());
							}
						}  else if (columnNames[j].equals("医保")) {
							hospitalContents.setMedical(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("限制范围")) {
							hospitalContents.setLimitRange(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("大类")) {
							chemicalBigCategory = rows.getCell(j).getStringCellValue().trim(); 
						}   else if (columnNames[j].equals("小类")) {
							chemicalSubCategory = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("备注")) {
							hospitalContents.setRemark(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("备注1")) {
							hospitalContents.setRemark1(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注2")) {
							hospitalContents.setRemark2(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注3")) {
							hospitalContents.setRemark3(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("省招标药品ID")) {
							try {
								hospitalContents.setBidDrugId(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								hospitalContents.setBidDrugId(String.valueOf(drugCode.longValue()));
							}
						}
					}
					
					if(!isDisabledOriginalData){//增量导入，需要按照提取通用名，给药途径，编号，药品类型，院用目录通用名，生产企业，剂型,规格，包装数量,省招标通用名查重，重复记录的不保存
						List<HospitalContents> repeatList = getHospitalContentsRepository().findByCommonCommonNameAndAdministrationIdAndCommonDrugCategoryAndCommonMatchNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndCommonBidCommonNameAndDeletedFalse(extractCommonName, hospitalContents.getAdministration().getId(), dcEnum, matchNumber, hospitalContents.getPill(), hospitalContents.getManufacturer(), hospitalContents.getCommonName(),hospitalContents.getSpecifications(),hospitalContents.getAmount(),bidCommonName);
						if(EmptyUtil.isCollectionNotEmpty(repeatList)){
							noSave.add(i + 1);
							continue;
						}
					}
					
					CommonName commonName = commonNameService.findByMatchNumberAndCommonName(matchNumber,extractCommonName);
					if (EmptyUtil.isNull(commonName)) {
							commonName = new CommonName();
							commonName.setCommonName(extractCommonName);
							commonName.setBidCommonName(bidCommonName);
							commonName.setDeleted(Boolean.FALSE);
							commonName.setDrugCategory(dcEnum);
							commonName.setEnabled(Boolean.TRUE);
							commonName.setMatchNumber(matchNumber);
							commonName.setChemicalBigCategory(chemicalBigCategory);
							commonName.setChemicalSubCategory(chemicalSubCategory);
							commonName.setAntibacterialseCategory(antibacterialseCategory);
							commonName.setAntibacterialseNumber(antibacterialseNumber);
							commonName.setAntibacterialsed(antibacterialsed);
							commonNameService.saveAndFlush(commonName);
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
