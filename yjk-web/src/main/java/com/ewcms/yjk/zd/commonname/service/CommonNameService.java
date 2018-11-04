package com.ewcms.yjk.zd.commonname.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Reflections;
import com.ewcms.util.PinYin;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
    

    public List<CommonName> findByCommonName(String commonName){
    	return getCommonNameRepository().findByCommonName(commonName);
    }
    
    public List<CommonName> findByCommonNameAndAdministrationIdAndEnabledTrueAndDeletedFalse(String commonName, Long administrationId){
    	return getCommonNameRepository().findByCommonNameAndAdministrationIdAndEnabledTrueAndDeletedFalse(commonName, administrationId);
    }
    
    public List<CommonName> findByNumberAndAdministrationIdAndDrugCategory(String number, Long administrationId, DrugCategoryEnum drugCategory){
    	return getCommonNameRepository().findByNumberAndAdministrationIdAndDrugCategory(number, administrationId, drugCategory);
    }
    
    public List<CommonName> findByCommonNameAndNumberAndAdministrationIdAndDrugCategory(String commonName,String number, Long administrationId, DrugCategoryEnum drugCategory){
    	return getCommonNameRepository().findByCommonNameAndNumberAndAdministrationIdAndDrugCategory(commonName, number, administrationId, drugCategory);
    }

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
		PinYin.initSpell(m);
		return super.update(m);
	}
	
//	public CommonName restore(Long commonNameId){
//		CommonName m = baseRepository.findOne(commonNameId);
//		m.setDeleted(Boolean.FALSE);
//		return super.update(m);
//	}
	
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
							if (columnNames[j].equals("提取通用名")) {
								commonName.setCommonName(rows.getCell(j).getStringCellValue().trim());
								PinYin.initSpell(commonName);
							} else if (columnNames[j].equals("给药途径")) {
								try {
									Double administrationId = rows.getCell(j).getNumericCellValue();
									if (administrationId == 0L) {
										commonName.setAdministration(null);
									} else {
										Administration administration = administrationService.findOne(administrationId.longValue());
										commonName.setAdministration(administration);
									}
								}catch (Exception e) {
									commonName.setAdministration(null);
								}
							} else if (columnNames[j].equals("编号")) {
								try {
									commonName.setNumber(rows.getCell(j).getStringCellValue().trim());
								} catch (Exception e) {
									Double number = rows.getCell(j).getNumericCellValue();
									commonName.setNumber(String.valueOf(number.longValue()));
								}								
								
							} else if (columnNames[j].equals("匹配编号")) {
								commonName.setDrugCategory(DrugCategoryEnum.valueOf(rows.getCell(j).getStringCellValue().trim().substring(0, 1)));
							}else if (columnNames[j].equals("全拼")) {
								commonName.setSpell(rows.getCell(j).getStringCellValue().trim());
							} else if (columnNames[j].equals("简拼")) {
								commonName.setSpellSimplify(rows.getCell(j).getStringCellValue().trim());
							}
					}
					if (findByCommonNameAndNumberAndAdministrationIdAndDrugCategory(commonName.getCommonName(),commonName.getNumber(), commonName.getAdministration().getId(),commonName.getDrugCategory()).size() == 0) {
						super.saveAndFlush(commonName);
					}else{
						noSave.add(i + 1);
					}
				}catch(Exception e) {
					System.out.print(e.toString());
					noSave.add(i + 1);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			
		}
		return noSave;
	}
    	
	public void writeExcel(OutputStream out) {
		try {
			WritableWorkbook wbook = Workbook.createWorkbook(out);
			String tmptitle = "通用名信息";
			WritableSheet wsheet = wbook.createSheet(tmptitle, 0);
			
			List<CommonName> commonNames = findAll();
			
			Map<String, String> map = getCorrespond();
			
			Collection<String> values = map.values();
			int row = 0;
			int i = 0;
			for (String value : values) {
				wsheet.addCell(new Label(i, row, value));
				i++;
			}
			
			Set<String> keys = map.keySet();
			for (CommonName commonName : commonNames) {
				row = row + 1;
				int j = 0;
				for (String key : keys) {
					String value = "";
					Object object = Reflections.invokeGetter(commonName, key);
					if (object instanceof Long) {
						if (object != null) {
							value = Long.toString((Long) object);
						}
					} else if (object instanceof Double){
						if (object != null){
							value = Double.toString((Double) object);
						}
					} else if (object instanceof Boolean) {
						if (object != null) {
							if ((Boolean) object) {
								value = (String)"是";
							}
						}
					} else if (object instanceof Administration) {
						if (object != null) {
							value = ((Administration)object).getName();
						}
					}else if (object instanceof DrugCategoryEnum) {
						if (object != null) {
							value = ((DrugCategoryEnum)object).getInfo();
						}
					} else {
						value = (String) object;
					}
					wsheet.addCell(new Label(j, row, value));
					j++;
				}
			}
			// 主体内容生成结束
			wbook.write(); // 写入文件
			wbook.close();
		} catch (Exception e) {
		}
	}
	
	private Map<String, String> getCorrespond(){
		Map<String, String> map = Maps.newLinkedHashMap();
		
		map.put("commonName", "通用名");
		map.put("administration", "给药途径");
		map.put("number", "编号");
		map.put("spell", "全拼");
		map.put("spellSimplify", "简拼");
		map.put("enabled", "是否启用");
		map.put("deleted", "删除标志");
		map.put("drugCategory", "药品种类");
		return map;
	}
}
