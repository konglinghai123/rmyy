package com.ewcms.yjk.zd.commonname.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ewcms.common.utils.Reflections;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

public class DuplicateRemovalUtil {
	
    /**
     * 去重
     * 
     * @param orderList
     * @return
     * @author jqlin
     */
    public static List<CommonNameContents> removeDuplicateOrder(List<CommonNameContents> orderList, String objName) {
    	 List<CommonNameContents> list1= new ArrayList<CommonNameContents>();
    	 Set<String> set=new HashSet<String>();
    	 for(CommonNameContents vo : orderList) {
    		 if (vo == null) {
    			 continue;
    	     }
    		 String aValue = String.valueOf(Reflections.getFieldValue(vo, objName));
    	     if (aValue != null) {
    	    	 if (!set.contains(aValue)) { //set中不包含重复的
    	    		 set.add(aValue);
    	    		 list1.add(vo);
    	         } else {
    	             continue;
    	         }
    	      }   
    	}
    	 set.clear(); 
        return list1;    	
    }

}
