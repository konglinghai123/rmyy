package com.ewcms.yjk.zd.commonname.service;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.util.PinYin;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRepository;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */
@Service
public class CommonNameService extends BaseService<CommonName, Long> {
	
    private CommonNameRepository getCommonNameRepository() {
        return (CommonNameRepository) baseRepository;
    }

    @Autowired
    private AdministrationService administrationService;
    
    public List<CommonName> findCommonNameBySpell(String spell){
    	return getCommonNameRepository().findCommonNameBySpell(spell);
    }
    
    public CommonName findByMatchNumberAndCommonName(String matchNumber,String commonName){
    	return getCommonNameRepository().findByMatchNumberAndCommonName(matchNumber,commonName);
    }

    public List<CommonName> findByMatchNumber(String matchNumber){
    	return getCommonNameRepository().findByMatchNumber(matchNumber);
    }
//    public List<CommonName> findByCommonName(String commonName){
//    	return getCommonNameRepository().findByCommonName(commonName);
//    }
//    
//    public List<CommonName> findByCommonNameAndAdministrationIdAndEnabledTrueAndDeletedFalse(String commonName, Long administrationId){
//    	return getCommonNameRepository().findByCommonNameAndAdministrationIdAndEnabledTrueAndDeletedFalse(commonName, administrationId);
//    }
//    
//    public List<CommonName> findByNumberAndAdministrationIdAndDrugCategory(String number, Long administrationId, DrugCategoryEnum drugCategory){
//    	return getCommonNameRepository().findByNumberAndAdministrationIdAndDrugCategory(number, administrationId, drugCategory);
//    }
//    
//    public List<CommonName> findByCommonNameAndNumberAndAdministrationIdAndDrugCategoryAndDeletedFalse(String commonName,String number, Long administrationId, DrugCategoryEnum drugCategory){
//    	return getCommonNameRepository().findByCommonNameAndNumberAndAdministrationIdAndDrugCategoryAndDeletedFalse(commonName, number, administrationId, drugCategory);
//    }

	@Override
	public CommonName save(CommonName m) {
		PinYin.initSpell(m);
		return super.save(m);
	}
	
	@Override
	public CommonName saveAndFlush(CommonName m) {
		PinYin.initSpell(m);
		return super.saveAndFlush(m);
	}

	@Override
	public CommonName update(CommonName m) {
		CommonName vo = findOne(m.getId());
		if(!m.getCommonName().equals(vo.getCommonName())){
			PinYin.initSpell(m);
		}
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
				CommonName commonName = new CommonName();
				rows = sheet.getRow(i);
				try {
					for (int j = 0; j <= cols; j++) {
							if (columnNames[j].equals("软件通用名")) {
								commonName.setCommonName(rows.getCell(j).getStringCellValue().trim());
								PinYin.initSpell(commonName);
							} else if (columnNames[j].equals("软件ID")) {
								try {
									commonName.setMatchNumber(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double number = rows.getCell(j).getNumericCellValue();
									commonName.setMatchNumber(String.valueOf(number.longValue()));
								}								
								
							} else if (columnNames[j].equals("省招标通用名")) {
								commonName.setBidCommonName(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("类别")) {
								String dc = rows.getCell(j).getStringCellValue().trim();
								if(EmptyUtil.isStringEmpty(dc)){
									commonName.setDrugCategory(DrugCategoryEnum.Q);
								}else{
									if(dc.equals("西药")){
										commonName.setDrugCategory(DrugCategoryEnum.H);
									}else if(dc.equals("中成药")){
										commonName.setDrugCategory(DrugCategoryEnum.Z);
									}else{
										commonName.setDrugCategory(DrugCategoryEnum.Q);
									}
								}
								
							}else if (columnNames[j].equals("化药大类")) {
								commonName.setChemicalBigCategory(rows.getCell(j).getStringCellValue().trim());
							}else if (columnNames[j].equals("化药小类")) {
								commonName.setChemicalSubCategory(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("抗菌药物")) {
								String anti = rows.getCell(j).getStringCellValue().trim();
								if(EmptyUtil.isStringNotEmpty(anti)&&anti.equals("是")){
									commonName.setAntibacterialsed(Boolean.TRUE);
								}
							}else if (columnNames[j].equals("抗菌药物类别")) {
								commonName.setAntibacterialseCategory(rows.getCell(j).getStringCellValue().trim());
							}else if (columnNames[j].equals("抗菌药物编号")) {
								try {
									commonName.setAntibacterialseNumber(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double number = rows.getCell(j).getNumericCellValue();
									commonName.setAntibacterialseNumber(String.valueOf(number.longValue()));
								}								
							}
					}
					
					if (EmptyUtil.isNull(findByMatchNumberAndCommonName(commonName.getMatchNumber(),commonName.getCommonName()))) {
						super.saveAndFlush(commonName);
					}else{
						noSave.add(i + 1);
					}
				}catch(Exception e) {
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
		} finally {
		}
		return noSave;
	}
}
