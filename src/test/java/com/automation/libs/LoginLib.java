package com.automation.libs;

import java.util.Hashtable;

import com.automation.page.RegisterPage;
import com.automation.page.LoginPage;
import com.automation.webaccelerators.ActionEngine;

public class LoginLib extends CommonLib {

	public void VerifyErrorMessage(Hashtable<String, String> data, String vstrTestVal) throws Throwable {
		try {
			test = extent.startTest("Verify the presence of login error message");
			System.out.println("AdminPage.LOGINHERE"+RegisterPage.LOGINHERE);
			click(RegisterPage.LOGINHERE, "Login link");
			
			click(LoginPage.LOGINBTN, "Login button");
			isElementPresent(LoginPage.EMAIL_ERR_MSG, "Email Error Message");
			isElementPresent(LoginPage.PASSWORD_ERR_MSG, "Password Error Message");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
