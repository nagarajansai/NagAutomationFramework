package com.automation.libs;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;

import com.automation.page.RegisterPage;

public class RegisterLib extends CommonLib {
	
	
	public void CreateNewUser(Hashtable<String, String> data, String vstrTestVal) throws Throwable {
		try {
			RegisterPage al=PageFactory.initElements(Driver, RegisterPage.class);

			test = extent.startTest("Verify new user creation");
			click(RegisterPage.LOGIN, "Login link");

			typeWithAssertNew(al.EMAILNEW, data.get("Email"), "Email Address"); 
			//PageFactory is used here
			
			typeWithAssert(RegisterPage.RENTER_EMAIL, data.get("RenterEmail"), "Re-enter Email address");
			typeWithAssert(RegisterPage.PASSWORD, data.get("Password"), "Password value");

			click(RegisterPage.REGISTER, "Register button");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
