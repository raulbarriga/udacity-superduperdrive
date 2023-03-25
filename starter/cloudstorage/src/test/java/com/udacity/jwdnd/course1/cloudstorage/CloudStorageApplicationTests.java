package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		// I redirect automatically so this will fail
		//Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}


	// Write a test that verifies that an unauthorized user can only access the login and
	// signup pages.
	@Test
	public void testUnauthorizedUserHomePageRestriction() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	// Write a test that signs up a new user, logs in, verifies that the home page is
	// accessible, logs out, and verifies that the home page is no longer accessible.
	@Test
	public void testAuthorizedUserHomePageAccess() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("Authorized User","Test","abc","321");
		doLogIn("abc", "321");

		// verifies that the home page is accessible
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
		// log out
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		// verifies that the home page is no longer accessible.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}
//WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

	/*
		Note Tests
	 */


	@Test
	public void testNotes() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("Create Note","Test","note","321");
		doLogIn("note", "321");

		// Write a test that creates a note, and verifies it is displayed.
		// go to note tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note")));
		WebElement addNewNote = driver.findElement(By.id("add-new-note"));
		addNewNote.click();

		// note modal
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys("New note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("This is the note description");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-btn")));
		WebElement saveNoteBtn = driver.findElement(By.id("save-note-btn"));
		saveNoteBtn.click();

		// go back to the notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		//webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-success-msg")));
		WebElement noteSuccessMsg = driver.findElement(By.id("note-success-msg"));
		try {
			//verify new note is added and displayed as expected
			Assertions.assertEquals("Successfully created note.", noteSuccessMsg.getAttribute("textContent"));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Note creation failed.");
		}

		// edit note just created

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-btn")));
		WebElement editNoteBtn = driver.findElement(By.id("edit-note-btn"));
		editNoteBtn.click();

		// modal open again
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys("Edited note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("This is the note description now edited");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-btn")));
		saveNoteBtn = driver.findElement(By.id("save-note-btn"));
		saveNoteBtn.click();

		// go back to the notes tab
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		// verifies that the changes are displayed.
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-success-msg")));
		noteSuccessMsg = driver.findElement(By.id("note-success-msg"));
		try {
			Assertions.assertEquals("Successfully edited note.", noteSuccessMsg.getAttribute("textContent"));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Note editing failed.");
		}

		// deleting note just edited
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-btn")));
		WebElement deleteNoteBtn = driver.findElement(By.id("delete-note-btn"));
		deleteNoteBtn.click();

		// go back to the notes tab
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		// Wait for the success message to be displayed
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-success-msg")));
		noteSuccessMsg = driver.findElement(By.id("note-success-msg"));

		WebElement noteTableBody = driver.findElement(By.id("userTable"));
		List<WebElement> rows = noteTableBody.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		try {
			webDriverWait.until(ExpectedConditions.textToBePresentInElement(noteSuccessMsg, "Deleting note successful."));
			// Assert that the text matches the expected value
			Assertions.assertEquals("Deleting note successful.", noteSuccessMsg.getText());

			// checks that the table is empty since we only added 1 note item
			Assertions.assertTrue(rows.isEmpty());
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Note deletion failed.");
		}
	}

	/*
		Credentials Tests
	 */

	@Test
	public void testCredentials() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("Create Credential", "Test", "credentials", "abc");
		doLogIn("credentials", "abc");

		// Write a test that creates a set of credentials

		// go to credentials tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		// click on add new credential button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential")));
		WebElement addNewCredential = driver.findElement(By.id("add-new-credential"));
		addNewCredential.click();

		// credential modal opened
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.click();
		credentialUrl.sendKeys("https://www.facebook.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.click();
		credentialUsername.sendKeys("sarah_codes123");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.click();
		String password = "credentials_123";
		credentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
		WebElement saveCredential = driver.findElement(By.id("save-credential"));
		saveCredential.click();

		// go back to the credentials tab
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		//verify new credential is added and displayed as expected
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-success-msg")));
		WebElement credentialSuccessMsg = driver.findElement(By.id("credential-success-msg"));
		try {
			Assertions.assertEquals("Successfully created credential.", credentialSuccessMsg.getAttribute("textContent"));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Credential addition failed.");
		}

		// verify the displayed password is encrypted
		WebElement encryptedPswd = driver.findElement(By.id("encrypted-pswd"));
		try {
			// `password` is the original password submitted
			Assertions.assertNotEquals(encryptedPswd.getText(), password);
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Credential password displayed is not encrypted.");
		}

		// Write a test that views an existing set of credentials,
		// verifies that the viewable password is unencrypted

		// click on add new credential button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-edit-btn")));
		WebElement editNewCredential = driver.findElement(By.id("credential-edit-btn"));
		editNewCredential.click();

		// modal opens up with previous data
		// check that the password is unencrypted & viewable again
		// verify the displayed password is encrypted
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialPassword = driver.findElement(By.id("credential-password"));
		try {
			// `credentialPassword` is the modal text that's displayed in the password input field
			// `encryptedPswd` is the encrypted text that's displayed in the Credentials tab where the credentials items are shown
			Assertions.assertNotEquals(encryptedPswd.getText(), credentialPassword.getText());
			// `password` is the originally set password
			Assertions.assertEquals(password, credentialPassword.getAttribute("value"));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Credential encrypted password is not decrypted in the modal.");
		}
		// edits the credentials, and verifies that the changes are displayed.
		credentialPassword.click();
		String editedPassword = "credentials_edit_123";
		credentialPassword.sendKeys(editedPassword);

		credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.click();
		credentialUsername.sendKeys("sarah_codes_edited_123");

		credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.click();
		credentialUrl.sendKeys("https://www.twitter.com");

		// save credentials edit
		saveCredential = driver.findElement(By.id("save-credential"));
		saveCredential.click();
		// go back to the credentials tab
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-success-msg")));
		credentialSuccessMsg = driver.findElement(By.id("credential-success-msg"));
		try {
			// credentialSuccessMsg is the general success message shown after adding,
			// editing, or deleting a credential
			Assertions.assertEquals("Successfully edited credential.", credentialSuccessMsg.getAttribute("textContent"));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Editing credential failed.");
		}

		// Write a test that deletes an existing set of credentials and
		// verifies that the credentials are no longer displayed.

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential")));
		WebElement deleteCredential = driver.findElement(By.id("delete-credential"));
		deleteCredential.click();

		// go back to the credentials tab
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-success-msg")));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credentialTable")));
		WebElement credentialTableBody = driver.findElement(By.id("credentialTable"));

		List<WebElement> rows = credentialTableBody.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		credentialSuccessMsg = driver.findElement(By.id("credential-success-msg"));
		try {
			Assertions.assertEquals("Deleting credential successful.", credentialSuccessMsg.getAttribute("textContent"));

			// checks that the table is empty since we only added 1 credential item
			Assertions.assertTrue(rows.isEmpty());
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Deleting credential failed.");
		}
	}
}