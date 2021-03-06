package com.test.cnqaos.testcases.users;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cnqaos.pages.users.StudentPage;
import com.cnqaos.pages.users.UserPage;
import com.cnqaos.pages.LoginPage;
import com.cnqaos.testbase.TestBase;
import com.cnqaos.utils.TestUtils;

public class AddStudentTest extends TestBase
{

	String sheetname = "Sheet1";
	StudentPage studentPage ;
	
	String filepath = "/src/main/java/com/cnqaos/testdata/cnqaosstudentaccountdata.xlsx";
	
	LoginPage loginPage;
	UserPage userPage;
	boolean b;
	
	public AddStudentTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@BeforeMethod
	public void studentSetUp() throws IOException
	{
		initializebrowser();
		
		LoginPage loginpages = new LoginPage();
		
		loginpages.clickOnLoginButton(prob.getProperty("username"), prob.getProperty("password"));
		
		studentPage =  new StudentPage();
		
		userPage = new UserPage();
		
		studentPage.addStudentLink();
		
	}
	
	@Test(dataProvider="studentdata", enabled = true)
	public void addStudentTest(String centername,String civilstatus,String sex,String name,String address,String country,
			                   String city,String postalcode,String phonenumber,String emailaddress,String dateofbirth,
			                   String birthplace,String departmentname,String emergencycontact,String securitynumber,
			                   String professionalbackground,String educationbackground,String educationfield,String trainingtype,
			                   String trainingname,String additionaltraining,String validated,String active) throws IOException, InterruptedException
	{
		
		studentPage.addStudentButton();
		
		userPage.selectCenter(centername);
		
		studentPage.selectCivilStatus(civilstatus);
		
		userPage.selectSex(sex);
		
		userPage.enterName(name);
		
		userPage.enterAddress(address);
		
		userPage.selectCountry(country);
		
		userPage.selectCity(city);
		
		userPage.enterPostalCode(postalcode);
		
		userPage.enterPhoneNumber1(phonenumber);
		
		userPage.enterEmailAddress(emailaddress);
		
		studentPage.enterDateofBirth(dateofbirth);
		
		studentPage.enterPlaceofBirth(birthplace);
		
		studentPage.enterDepartmentName(departmentname);
		
		studentPage.enterEmerngcyContactNumber(emergencycontact);
		
		studentPage.enterSocialSecurityNumber(securitynumber);
		
		studentPage.enterProfessionalBackground(professionalbackground);
		
		studentPage.selectEqucationBackGrund(educationbackground);
		
		studentPage.selectEducationField(educationfield);
		
		studentPage.selectTrainingType(trainingtype);
		
		studentPage.selectTrainingName(trainingname);
		
		studentPage.selectAdditionaltraining(additionaltraining);
		
		Boolean validatedbolean = Boolean.parseBoolean(validated);
		
		userPage.checkValidated(validatedbolean);
		
		Boolean activebollean = Boolean.parseBoolean(active);
		
		studentPage.checkActive(activebollean);
		
		userPage.createRecord();
		
		Thread.sleep(6000);
		
		Alert alert = driver.switchTo().alert();
		String alertmessage = alert.getText();
		
		if(alertmessage.equals("Student edited successfully."))
        {
			System.out.println("Student record saved");
        }else if (alertmessage.equals("undefined")) 
        {
			System.out.println("System get undefined error");
		}else if(alertmessage.equals("Student created successfully."))
		{
			System.out.println("Student record created successfully");
		}else {
			System.out.println("not able to save record");
		}
       
		
		alert.accept();		
		
	}
	
	@DataProvider
	public Object[][] studentdata() throws IOException
	{
		Object object[][] = TestUtils.getTestData(sheetname, filepath);
		return object;
	}
	
	@AfterMethod
	public void teardown()
	{
		driver.close();
	}

}