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
import org.springframework.stereotype.Service;
import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */
@Service
public class CommonNameContentsService extends BaseService<CommonNameContents, Long> {
	
    @Autowired
    private AdministrationService administrationService;
    
	@Override
	public CommonNameContents update(CommonNameContents m) {
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
				CommonNameContents commonNameContents = new CommonNameContents();
				rows = sheet.getRow(i);
				try {
					for (int j = 0; j <= cols; j++) {
							if (columnNames[j].equals("药品ID")) {
								try {
									commonNameContents.setDrugId(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double drugCode = rows.getCell(j).getNumericCellValue();
									commonNameContents.setDrugId(String.valueOf(drugCode.longValue()));
								}
							} else if (columnNames[j].equals("项目名称")) {
								commonNameContents.setProjectName(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("批次")) {
								commonNameContents.setBatch(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("通用名")) {
//								commonNameContents.setCommonName(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("给药途径")) {
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
							} else if (columnNames[j].equals("提取通用名")) {
								commonNameContents.setExtractCommonName(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("编号")) {
								try {
									commonNameContents.setSerialNo(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double userName = rows.getCell(j).getNumericCellValue();
									commonNameContents.setSerialNo(String.valueOf(userName.longValue()));
								}
							} else if (columnNames[j].equals("抗菌药物")) {
								try {
									Double antibacterialsed = rows.getCell(j).getNumericCellValue();
									if (antibacterialsed != null && antibacterialsed.longValue() == 1L) {
										commonNameContents.setAntibacterialsed(true);
									}
								} catch (Exception e) {
								}
							} else if (columnNames[j].equals("序号")) {
								commonNameContents.setPill(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("规格*数量")) {
								commonNameContents.setSpecNumber(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("生产企业")) {
								commonNameContents.setManufacturer(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("目录分类")) {
//								commonNameContents.setContentCategory(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("备注")) {
//								commonNameContents.setRemark(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("药品分类大类")) {
//								commonNameContents.setDrugMajor(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("药品分类")) {
//								commonNameContents.setDrugCategory(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("原备注")) {
//								commonNameContents.setOldRemark(rows.getCell(j).getStringCellValue().trim());
//							} else if (columnNames[j].equals("配送公司")) {
//								commonNameContents.setDiscom(rows.getCell(j).getStringCellValue().trim());
							}
					}
					super.saveAndFlush(commonNameContents);
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
