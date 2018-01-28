package com.automation.libs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.automation.page.HomePage;

public class HomeLib extends CommonLib {

	public void verifyHeaderMenuList(Hashtable<String, String> data, String vstrTestVal) throws Throwable {
		try {
			test = extent.startTest("Verify the presence of header menus");
			
			List<String> strMenulist = Arrays
					.asList(new String[] { "Bracelets", "Design Your Own", "Earrings", "Gift Ideas", "Necklaces",
							"Rings", "Watches", "Other", "Collections", "Clearance", "Sale" });
			
			List<String> pageMenuList = new ArrayList<String>();
			List<WebElement> ml = Driver.findElements(HomePage.MENU_LIST);

			for (int i = 1; i <= ml.size() - 1; i++) {
				System.out.println(ml.get(i).getText());
				pageMenuList.add(ml.get(i).getText());
			}
			System.out.println(strMenulist.size());
			System.out.println(pageMenuList.size());
			
			assertStringArrayMatching(strMenulist.toArray(), pageMenuList.toArray());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
