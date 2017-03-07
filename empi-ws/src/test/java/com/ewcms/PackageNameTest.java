package com.ewcms;

import org.junit.Test;

import com.ewcms.webservice.service.IPatientWebService;

/**
 *
 * @author wu_zhijun
 */
public class PackageNameTest {
	@Test
	public void testPackageName() {
		System.out.println("canonicalName : " + IPatientWebService.class.getCanonicalName());
		System.out.println("name : " + IPatientWebService.class.getName());
		System.out.println("SimpleName : " + IPatientWebService.class.getSimpleName());
		
	}
}
