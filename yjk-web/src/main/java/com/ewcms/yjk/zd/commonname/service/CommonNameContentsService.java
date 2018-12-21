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
import com.ewcms.yjk.zd.commonname.repository.CommonNameContentsRepository;
import com.google.common.collect.Lists;

/**
 * @author zhoudongchu
 */
@Service
public class CommonNameContentsService extends BaseService<CommonNameContents, Long> {

	@Autowired
	private CommonNameService commonNameService;
	@Autowired
	private AdministrationService administrationService;

	private CommonNameContentsRepository getCommonNameContentsRepository() {
		return (CommonNameContentsRepository) baseRepository;
	}

	@Override
	public CommonNameContents save(CommonNameContents m) {
		PinYin.initSpell(m);
		return super.save(m);
	}

	@Override
	public CommonNameContents update(CommonNameContents m) {
		PinYin.initSpell(m);
		m.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		return super.update(m);
	}

//	public List<CommonNameContents> findByCommonCommonNameAndCommonAdministrationIdAndDeletedFalseAndDeclaredTrueOrderByUpdateDateDesc(
//			String commonName, Long administrationId) {
//		return getCommonNameContentsRepository()
//				.findByCommonCommonNameAndCommonAdministrationIdAndDeletedFalseAndDeclaredTrueOrderByUpdateDateDesc(commonName,
//						administrationId);
//	}

//	public List<CommonNameContents> findByCommonIdAndDeletedFalse(Long commonId) {
//		return getCommonNameContentsRepository().findByCommonIdAndDeletedFalse(commonId);
//	}
//	
//	public Page<CommonNameContents> findByCommonIdInAndDeletedFalseAndDeclaredTrue(List<Long> commonIds, Pageable pageable){
//		return getCommonNameContentsRepository().findByCommonIdInAndDeletedFalseAndDeclaredTrue(commonIds, pageable);
//	}


	public List<CommonNameContents> findCommonNameContentsBySpell(String spell){
		return getCommonNameContentsRepository().findCommonNameContentsBySpell(spell);
	}

	public List<String> findDistinctProjectName(){
		return getCommonNameContentsRepository().findDistinctProjectName();
	}
	
	public List<CommonNameContents> findCommonNameContentsByManufacturer(String manufacturer){
		return getCommonNameContentsRepository().findCommonNameContentsByManufacturer(manufacturer);
	}
	/**
	 * 根据申报药品查找当前大目录匹配数据集合
	 * 
	 * @param commonNameContentsId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<CommonNameContents> matchByCommonNameContentsId(Long commonNameContentsId, Pageable pageable) {
		CommonNameContents commonNameContentsvo = findOne(commonNameContentsId);
		List<CommonName> commonNames = commonNameService.findByMatchNumber(commonNameContentsvo.getCommon().getMatchNumber());

		List<Long> commonNameIds = Collections3.extractToList(commonNames, "id");
		return getCommonNameContentsRepository().findByCommonIdInAndAdministrationIdAndDeletedFalseAndDeclaredTrue(commonNameIds,commonNameContentsvo.getAdministration().getId(), pageable);
	}

	public void filterDeclaredByProjectName(List<String> projectDeclareds){
		if(EmptyUtil.isCollectionNotEmpty(projectDeclareds)){
			getCommonNameContentsRepository().setAllValidRecordDeclaredFalse();
			getCommonNameContentsRepository().setDeclaredTrueByProjectName(projectDeclareds);
		}
	}
	
	public List<Integer> importExcel(InputStream in,Boolean isDisabledOriginalData) {
		List<Integer> noSave = Lists.newArrayList();
		try {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int records = sheet.getLastRowNum();
			if(isDisabledOriginalData){//如果作废数据库以前数据，将数据库所有数据删除标志设为true
				getCommonNameContentsRepository().deleteAllCommonNameContents();
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
				CommonNameContents commonNameContents = new CommonNameContents();
				rows = sheet.getRow(i);
				try {
					for (int j = 0; j <= cols; j++) {
						 if (columnNames[j].equals("类别")) {
								String dc = rows.getCell(j).getStringCellValue().trim();
								if(EmptyUtil.isStringNotEmpty(dc)){
									if(dc.equals("西药")){
										dcEnum = DrugCategoryEnum.H;
									}else if(dc.equals("中成药")){
										dcEnum = DrugCategoryEnum.Z;
									}else{
										dcEnum = DrugCategoryEnum.Q;
									}
								}
						} else if (columnNames[j].equals("项目名称")) {
							commonNameContents.setProjectName(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("批次")) {
							commonNameContents.setBatch(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("通用名")) {
							commonNameContents.setCommonName(rows.getCell(j).getStringCellValue().trim());
							PinYin.initSpell(commonNameContents);
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
						}else if (columnNames[j].equals("省招标药品ID")) {
							try {
								commonNameContents.setBidDrugId(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								commonNameContents.setBidDrugId(String.valueOf(drugCode.longValue()));
							}
						}else if (columnNames[j].equals("国家ID")) {
							try {
								commonNameContents.setCountryId(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								commonNameContents.setCountryId(String.valueOf(drugCode.longValue()));
							}
						}else if (columnNames[j].equals("给药途径")) {
							try {
								Double administrationId = rows.getCell(j).getNumericCellValue();
								if (administrationId == 0L) {
									commonNameContents.setAdministration(null);
								} else {
									Administration administration = administrationService.findOne(administrationId.longValue());
									commonNameContents.setAdministration(administration);
								}
							}catch (Exception e) {
								commonNameContents.setAdministration(null);
							}
						}else if (columnNames[j].equals("剂型")) {
							commonNameContents.setPill(rows.getCell(j).getStringCellValue().trim());
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
									commonNameContents.setSpecifications(tmpSpecfications);
									commonNameContents.setAmount(tmp[tmp.length-1]);
								}else{
									commonNameContents.setSpecifications(specNumber);
									commonNameContents.setAmount("");
								}
							}
						} else if (columnNames[j].equals("商品名")) {
							commonNameContents.setProductName(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("包装单位")) {
							commonNameContents.setPackageUnit(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("生产企业")) {
							commonNameContents.setManufacturer(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("进口企业")) {
							commonNameContents.setImportEnterprise(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("采购价（元）")) {
							try {
								Double purchasePrice = rows.getCell(j).getNumericCellValue();
								if (purchasePrice == 0L) {
									commonNameContents.setPurchasePrice(null);
								} else {
									commonNameContents.setPurchasePrice(purchasePrice);
								}
							} catch (Exception e) {
								commonNameContents.setPurchasePrice(null);
							}
						} else if (columnNames[j].equals("抗菌药物")) {
							String antied = rows.getCell(j).getStringCellValue().trim();
							if(EmptyUtil.isStringNotEmpty(antied)&&antied.equals("是")){
								antibacterialsed = Boolean.TRUE;
							}
						} else if (columnNames[j].equals("抗菌药物类别")) {
							antibacterialseCategory = rows.getCell(j).getStringCellValue().trim();
						} else if (columnNames[j].equals("抗菌药物编号")) {
							
							try {
								antibacterialseNumber = rows.getCell(j).getStringCellValue().trim();
							} catch (Exception e) {
								Double drugCode = rows.getCell(j).getNumericCellValue();
								antibacterialseNumber = String.valueOf(drugCode.longValue());
							}
						} else if (columnNames[j].equals("医保目录编号")) {
							try {
								commonNameContents.setMedicalDirNo(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double medicalDirNoTemp = rows.getCell(j).getNumericCellValue();
								commonNameContents.setMedicalDirNo(String.valueOf(medicalDirNoTemp.longValue()));
							}
						}  else if (columnNames[j].equals("医保类别")) {
							commonNameContents.setMedicalCategory(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("医保备注")) {
							commonNameContents.setMedicalRemark(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("一致性评价")) {
							commonNameContents.setConsistencyEvaluation(rows.getCell(j).getStringCellValue().trim());
						}  else if (columnNames[j].equals("基药")) {
							commonNameContents.setHeds(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("妇科")) {
							commonNameContents.setGynaecology(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("儿科")) {
							commonNameContents.setPediatric(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("急救")) {
							try {
								commonNameContents.setFirstAid(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double firstAid = rows.getCell(j).getNumericCellValue();
								commonNameContents.setFirstAid(String.valueOf(firstAid.longValue()));
							}							
						}   else if (columnNames[j].equals("基础输液")) {
							try {
								commonNameContents.setBasicInfusion(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double basicInfusion = rows.getCell(j).getNumericCellValue();
								commonNameContents.setBasicInfusion(String.valueOf(basicInfusion.longValue()));
							}							
						}   else if (columnNames[j].equals("廉价短缺")) {
							try {
								commonNameContents.setCheapShortage(rows.getCell(j).getStringCellValue().trim());
							} catch (Exception e) {
								Double cheapShortage = rows.getCell(j).getNumericCellValue();
								commonNameContents.setCheapShortage(String.valueOf(cheapShortage.longValue()));
							}
						}   else if (columnNames[j].equals("国家谈判品种")) {
							commonNameContents.setNegotiationVariety(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("大类")) {
							chemicalBigCategory = rows.getCell(j).getStringCellValue().trim(); 
						}   else if (columnNames[j].equals("小类/功效")) {
							chemicalSubCategory = rows.getCell(j).getStringCellValue().trim();
						}   else if (columnNames[j].equals("批准文号")) {
							commonNameContents.setLicenseNumber(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("招标剂型")) {
							commonNameContents.setBidPill(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("招标规格")) {
							commonNameContents.setBidSpecifications(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("招标单位")) {
							commonNameContents.setBidUnit(rows.getCell(j).getStringCellValue().trim());
						}   else if (columnNames[j].equals("包材")) {
							commonNameContents.setPackageMaterials(rows.getCell(j).getStringCellValue().trim());
						} else if (columnNames[j].equals("最小制剂单位")) {
							commonNameContents.setMinimalUnit(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注1")) {
							commonNameContents.setRemark1(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注2")) {
							commonNameContents.setRemark2(rows.getCell(j).getStringCellValue().trim());
						}else if (columnNames[j].equals("备注3")) {
							commonNameContents.setRemark3(rows.getCell(j).getStringCellValue().trim());
						}
					}
					if(!isDisabledOriginalData){//增量导入，需要按照提取通用名，给药途径，编号，药品类型，大目录通用名，生产企业，剂型,规格，包装数量,省招标通用名,省招标药品ID，国家ID，项目名称查重，重复记录的不保存
						List<CommonNameContents> repeatList = getCommonNameContentsRepository().findByCommonCommonNameAndAdministrationIdAndCommonDrugCategoryAndCommonMatchNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndCommonBidCommonNameAndBidDrugIdAndCountryIdAndProjectNameAndDeletedFalse(extractCommonName, commonNameContents.getAdministration().getId(), dcEnum, matchNumber, commonNameContents.getPill(), commonNameContents.getManufacturer(), commonNameContents.getCommonName(),commonNameContents.getSpecifications(),commonNameContents.getAmount(),bidCommonName, commonNameContents.getBidDrugId(), commonNameContents.getCountryId(), commonNameContents.getProjectName());
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
						commonNameContents.setCommon(commonName);
						super.saveAndFlush(commonNameContents);
					}
				} catch (Exception e) {
					System.out.print(e.toString());
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
		}
		return noSave;
	}
}
