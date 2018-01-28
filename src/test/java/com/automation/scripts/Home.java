package com.automation.scripts;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.libs.HomeLib;
import com.automation.utilities.TestUtil;

public class Home extends HomeLib {

	@DataProvider
	public Object[][] getTestDataFor_bvtVerifyHeaderMenuList() {
		return TestUtil.getData("bvtVerifyHeaderMenuList", testData, "Home");
	}

	@Test(dataProvider = "getTestDataFor_bvtVerifyHeaderMenuList")
	public void bvtVerifyHeaderMenuList(Hashtable<String, String> data) throws Throwable {

		try {

			if (data.get("RunMode").equals("Y")) {				
				verifyHeaderMenuList(data, "testing");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
