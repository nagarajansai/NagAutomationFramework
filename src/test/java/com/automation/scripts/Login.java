package com.automation.scripts;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.libs.LoginLib;
import com.automation.utilities.TestUtil;

public class Login extends LoginLib {

	@DataProvider
	public Object[][] getTestDataFor_bvtVerifyLogin() {
		return TestUtil.getData("bvtVerifyLogin", testData, "Login");
	}

	@Test(dataProvider = "getTestDataFor_bvtVerifyLogin")
	public void bvtVerifyLoginErrorMessage(Hashtable<String, String> data) throws Throwable {

		try {

			if (data.get("RunMode").equals("Y")) {

				int intRandNum = generateRandomNumber();
				VerifyErrorMessage(data, "testing");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
