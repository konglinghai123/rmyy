package com.ewcms.yjk.zd.commonname.service;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Reflections;
import com.ewcms.util.PinYin;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRepository;
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

    public List<CommonName> findCommonNameBySpell(String spell){
    	return getCommonNameRepository().findCommonNameBySpell(spell);
    }
    
    public List<CommonName> findByCommonName(String commonName){
    	return getCommonNameRepository().findByCommonName(commonName);
    }
	@Override
	public CommonName save(CommonName m) {
		PinYin.initSpell(m);
		return super.save(m);
	}

	@Override
	public CommonName update(CommonName m) {
		PinYin.initSpell(m);
		return super.update(m);
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
					Object object = Reflections.invokeGetter(commonName, key);;
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
		map.put("administration", "用药途径");
		map.put("matchingNumber", "匹配编号");
		map.put("spell", "全拼");
		map.put("spellSimplify", "简拼");
		map.put("enabled", "是否启用");
		map.put("deleted", "删除标志");
		
		return map;
	}
}
