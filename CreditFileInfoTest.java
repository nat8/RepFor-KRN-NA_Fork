package com.epam.test.tests;

/**
 * Class for testing Credit File Info page
 *
 * @author Natalia Amelina
 */
public class CreditFileInfoTest extends BaseTest {

	private static final String PROP_DEFAULT_CUSTOMER_URL = "url.customer.default1";
	private static final String PROP_USER_CREATOR = "db.user.createdby.1";
	private static final String PROP_LOGIN_URL = "url.login";
	private static final String PROP_CREDIT_HEADERS = "credit.view.headers";
	private static final String PROP_INCORRECT_ACCOUNTS = "incorrect.credit.account.list";
	private static final String PROP_ERROR_CATEGORY = "error.message.credit.category";
	private static final String PROP_ERROR_DUEDATE = "error.message.credit.duedate";
	private static final String PROP_ERROR_REMARKS = "error.message.credit.remarks";

	private String customerDefault;

	private UserBO user;

	private LoadPropertiesUtil properties;

	private MainService mainService;
	private ViewCustomerService viewCustomerService;
	private MaintenanceService maintenanceService;
	private CreditInfoFileService creditInfoFileService;

	/**
	 * Method initializes the global variables
	 *
	 * @param fileName
	 *            - name of *.properties file with data for tests
	 */
	@Parameters("properties")
	@BeforeTest()
	public void initialization(@Optional("dev2.properties") String fileName) {
		properties = new LoadPropertiesUtil(fileName);
		mainService = new MainService(driver, properties);
		viewCustomerService = new ViewCustomerService(driver);
		maintenanceService = new MaintenanceService(driver);
		creditInfoFileService = new CreditInfoFileService(driver);
		user = new UserBO(properties.getPropertyByKey(CharacterConstants.LOGIN),
		        properties.getPropertyByKey(CharacterConstants.PASSWORD));
		customerDefault = properties.getPropertyByKey(PROP_DEFAULT_CUSTOMER_URL);
	}

	/**
	 * Method for log in to the web application
	 */
	@BeforeTest(dependsOnMethods = "initialization")
	public void loginToNymbus() {
		LoginService loginService = new LoginService(driver);

		loginService.login(properties.getPropertyByKey(PROP_LOGIN_URL), user);
		Assert.assertTrue(mainService.isDashboard(),
		        "Dashbord page should be displayed");
	}

	/**
	 * Method for removing all additional customer data
	 */
	@BeforeTest(dependsOnMethods = "loginToNymbus")
	public void deleteAdditionalFromCustomer() {
		CommandToDataBase.removeAdditionalForCustomer(properties.getPropertyByKey(PROP_USER_CREATOR),
		        ConvertUtil.getCustomerAccountIDFromUrl(customerDefault), properties);
	}

	/**
	 * Method for navigating to Maintenance page
	 */
	@BeforeMethod()
	public void openMaintenance() {
		mainService.goToPage(customerDefault);
		viewCustomerService.openMaintenance();
	}

	/**
	 * Method for checking the display of Credit File Info form
	 */
	@TestCaseLink(url = "https://mediaspectrum.testrail.net/index.php?/cases/view/241812")
	@Test(description = "Maintenance tab - Credit File Info block - General flow & UI",
	        enabled = true)
	public void checkGeneralFlowTest() {
		LOG.info("checkGeneralFlowTest() start");
		Assert.assertTrue(maintenanceService.isCreditFileInfoDisplayed(),
		        "All required fields for form Credit Info File are displayed");
		LOG.info("checkGeneralFlowTest() finish");
	}

	/**
	 * Method for checking the table with all Credit File Info records
	 */
	@TestCaseLink(url = "https://mediaspectrum.testrail.net/index.php?/cases/view/241813")
	@Test(description = "Maintenance tab - Credit File Info block - View All",
	        dependsOnMethods = "create", enabled = true, alwaysRun = true)
	public void checkViewAllTest() {
		LOG.info("checkViewAllTest() start");
		maintenanceService.viewAllCredit();
		Assert.assertTrue(maintenanceService.isAddNewCreditDisplayed()
		        && maintenanceService.isPrintCreditDisplayed(),
		        "All required Buttons for View Credit File Info are displayed");
		Assert.assertTrue(
		        maintenanceService.checkCreditViewHeaders(properties.getValuesListByKey(PROP_CREDIT_HEADERS)),
		        "All Columns are named as required");
		LOG.info("checkViewAllTest() finish");
	}

	/**
	 * Method for checking the fields validation
	 */
	@TestCaseLink(url = "https://mediaspectrum.testrail.net/index.php?/cases/view/241814")
	@Test(description = "Maintenance tab - Credit File Info block - Create New (Invalid)",
	        enabled = true)
	public void checkInvalidTest() {
		LOG.info("checkInvalidTest() start");
		maintenanceService.addNewCreditInfoFile();
		Assert.assertTrue(creditInfoFileService.isFieldsDisplayed(),
		        "All required fields are displayed on Credit File Info window");

		creditInfoFileService.cancel();
		Assert.assertTrue(maintenanceService.isAddNewCreditDisplayed()
		        && (!creditInfoFileService.isSaveDisplayed()),
		        "Credit File Info form is closed");

		maintenanceService.addNewCreditInfoFile();
		creditInfoFileService.save();
		Assert.assertTrue(
		        creditInfoFileService.isCategoryErrorDisplayed(properties.getPropertyByKey(PROP_ERROR_CATEGORY))
		                && creditInfoFileService.isDueDateErrorDisplayed(properties
		                        .getPropertyByKey(PROP_ERROR_DUEDATE))
		                && creditInfoFileService.isRemarksErrorDisplayed(properties
		                        .getPropertyByKey(PROP_ERROR_REMARKS)),
		        "Red borders and feedbacks are displayed for all required fields");
		Assert.assertTrue(creditInfoFileService.checkPutIncorrectInAccount(properties
		        .getValuesListByKey(PROP_INCORRECT_ACCOUNTS)),
		        "Incorrect Values aren't entered in Account field");
		LOG.info("checkInvalidTest() finish");
	}

	/**
	 * Method for checking create of Credit File Info
	 */
	@TestCaseLink(url = "https://mediaspectrum.testrail.net/index.php?/cases/view/241814")
	@Test(description = "Maintenance tab - Credit File Info block - Create New/View Credit File",
	        enabled = true)
	public void checkCreateTest() {
		LOG.info("checkCreateTest() start");
		CreditFileBO creditFile = new CreditFileBO(properties.getPropertyByKey("def.creditfile.categorycode1"),
		        DateConverterUtil.getDateToSeveralDaysInDefaultFormat(2),
		        properties.getPropertyByKey("def.creditfile.remarks1"), true);

		maintenanceService.addNewCreditInfoFile();
		creditFile.setAccountNumber(creditInfoFileService.selectAccountFirstAvailable());
		creditInfoFileService.setCreditInfo(creditFile);
		creditInfoFileService.save();
		Assert.assertTrue(maintenanceService.isAddNewCreditDisplayed()
		        && (!creditInfoFileService.isSaveDisplayed()),
		        "Credit File Info form is closed after saving");

		maintenanceService.viewAllCredit();
		maintenanceService.editLastCreditFile();
		Assert.assertTrue(creditInfoFileService.checkCreditInfo(creditFile),
		        "Credit Info File filled as required");
		LOG.info("checkCreateTest() finish");
	}

	/**
	 * Method for checking edit of Credit File Info
	 */
	@TestCaseLink(url = "https://mediaspectrum.testrail.net/index.php?/cases/view/241825")
	@Test(description = "Maintenance tab - Credit File Info block - Edit Credit File",
	        dependsOnMethods = "create", enabled = true, alwaysRun = true)
	public void checkEditTest() {
		LOG.info("checkEditTest() start");
		CreditFileBO creditFile = new CreditFileBO(properties.getPropertyByKey("def.creditfile.categorycode2"),
		        DateConverterUtil.getDateToSeveralDaysInDefaultFormat(5),
		        properties.getPropertyByKey("def.creditfile.remarks2"), false);

		maintenanceService.viewAllCredit();
		maintenanceService.editLastCreditFile();
		Assert.assertTrue(creditInfoFileService.isFieldsDisplayed(),
		        "All required fields are displayed on Credit File Info window");

		creditInfoFileService.clearDueDate();
		creditInfoFileService.clearRemarks();
		creditInfoFileService.save();
		Assert.assertTrue(
		        creditInfoFileService.isDueDateErrorDisplayed(properties.getPropertyByKey(PROP_ERROR_DUEDATE))
		                && creditInfoFileService.isRemarksErrorDisplayed(properties
		                        .getPropertyByKey(PROP_ERROR_REMARKS)),
		        "Red borders and feedbacks are displayed for Due Date and Remarks fields");

		creditInfoFileService.cancel();
		Assert.assertTrue(maintenanceService.isAddNewCreditDisplayed()
		        && (!creditInfoFileService.isSaveDisplayed()),
		        "Credit File Info form is closed");

		maintenanceService.editLastCreditFile();
		Assert.assertTrue(!creditInfoFileService.isDueDateEmpty()
		        && !creditInfoFileService.isRemarksEmpty(),
		        "Due Date and Remarks fields aren't empty");

		creditFile.setAccountNumber(creditInfoFileService.selectAccountFirstAvailable());
		creditInfoFileService.setCreditInfo(creditFile);
		creditInfoFileService.save();
		maintenanceService.editLastCreditFile();
		Assert.assertTrue(creditInfoFileService.checkCreditInfo(creditFile),
		        "Credit Info File filled as required after edit");
		LOG.info("checkEditTest() finish");
	}

}