package com.automation.scripts;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.libs.RegisterLib;
import com.automation.utilities.TestUtil;

public class Register extends RegisterLib {

	@DataProvider
	public Object[][] getTestDataFor_bvtVerifyNewUser() {
		return TestUtil.getData("bvtVerifyNewUser", testData, "Admin");
	}

	@Test(dataProvider = "getTestDataFor_bvtVerifyNewUser")
	public void bvtVerifyNewUser(Hashtable<String, String> data) throws Throwable {

		try {

			if (data.get("RunMode").equals("Y")) {

				int intRandNum = generateRandomNumber();
				data.put("Email", data.get("Email") + "_" + Integer.toString(intRandNum));
				CreateNewUser(data, "testing");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
