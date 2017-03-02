package com.ewcms.empi.card.manage.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;

/**
 *@author zhoudongchu
 */
public class PracticeCardServicelIT extends BaseIT {
	@Autowired
	private PracticeCardService practiceCardService;
	
	@Test
	public void testCombinePracticeCard(){
		practiceCardService.autoCombine();
	}
}
