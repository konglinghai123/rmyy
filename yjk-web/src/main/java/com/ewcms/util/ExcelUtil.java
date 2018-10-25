package com.ewcms.util;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.google.common.collect.Lists;

public class ExcelUtil {

	/**
	 * 导入通用名字典库
	 * 
	 * @param in excel文件
	 * @return List 通用名字典库集合
	 */
	public static List<CommonName> importCommonName(InputStream in){
		List<CommonName> commonNames = Lists.newArrayList();
		
		try {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int records = sheet.getLastRowNum();
			
			//获得列名，为第0行位置
			HSSFRow rows = sheet.getRow(0);
			int cols = rows.getLastCellNum() - 1;
			String columnNames[] = new String[cols + 2];
			
			for (int i = 0; i <= cols; i++) {
				columnNames[i] = rows.getCell(i).getStringCellValue().trim();
			}
			
			//获得数据，数据从第1行开始
			for (int i = 1; i <= records; i++) {
				CommonName commonName = new CommonName();
				rows = sheet.getRow(i);
				for (int j = 0; j <= cols; j++) {
					if (columnNames[j].equals("通用名")) {
						commonName.setCommonName(rows.getCell(j).getStringCellValue().trim());
						PinYin.initSpell(commonName);
					} else if (columnNames[j].equals("全拼")) {
						commonName.setSpell(rows.getCell(j).getStringCellValue().trim());
					} else if (columnNames[j].equals("简拼")) {
						commonName.setSpellSimplify(rows.getCell(j).getStringCellValue().trim());
					}
					commonNames.add(commonName);
				}
			}
		} catch (Exception e) {
		}
		return commonNames;
	}
	
}
