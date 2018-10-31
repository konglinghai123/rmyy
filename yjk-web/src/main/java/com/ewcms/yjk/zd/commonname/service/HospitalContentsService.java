package com.ewcms.yjk.zd.commonname.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.ewcms.yjk.zd.commonname.repository.HospitalContentsRepository;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
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
	
	public List<HospitalContents> findByExtractCommonNameAndDeletedFalse(String extractCommonName){
		return getHospitalContentsRepository().findByExtractCommonNameAndDeletedFalse(extractCommonName);
	}
	
	public List<HospitalContents> matchByCommonNameContentsId(Long commonNameContentsId){
		CommonNameContents commonNameContentsvo = commonNameContentsService.findOne(commonNameContentsId);
		String  matchingNumber = commonNameContentsvo.getCommon().getMatchingNumber();
		List<String> commonNameList = commonNameService.findByMatchingNumber(matchingNumber);
		List<HospitalContents> hospitalContentsList = new ArrayList<HospitalContents>();
		
		for(String commonName:commonNameList){
			hospitalContentsList.addAll(findByExtractCommonNameAndDeletedFalse(commonName));
		}
		
		return hospitalContentsList;
	}
	
	@Override
	public HospitalContents update(HospitalContents m) {
		m.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		return super.update(m);
	}
	
	public List<Integer> importExcel(InputStream in){
		List<Integer> noSave = Lists.newArrayList();
		
		try {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int records = sheet.getLastRowNum();
			
			//获得列名，为第0行位置
			HSSFRow rows = sheet.getRow(0);
			int cols = rows.getLastCellNum() - 1;
			String columnNames[] = new String[cols + 1];
			
			for (int i = 0; i <= cols; i++) {
				columnNames[i] = rows.getCell(i).getStringCellValue().trim();
			}
			
			//获得数据，数据从第1行开始
			for (int i = 1; i <= records; i++) {
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
								hospitalContents.setGenericDrugName(rows.getCell(j).getStringCellValue().trim());
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
							} else if (columnNames[j].equals("提取通用名")) {
								hospitalContents.setExtractCommonName(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("编号")) {
								try {
									hospitalContents.setSerialNo(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double userName = rows.getCell(j).getNumericCellValue();
									hospitalContents.setSerialNo(String.valueOf(userName.longValue()));
								}
							} else if (columnNames[j].equals("剂型")) {
								hospitalContents.setPill(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("规格*数量")) {
								hospitalContents.setSpecNumber(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("生产企业")) {
								hospitalContents.setManufacturer(rows.getCell(j).getStringCellValue().trim());
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
							}
					}
					super.saveAndFlush(hospitalContents);
				}catch(Exception e) {
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			
		}
		return noSave;
	}

}
