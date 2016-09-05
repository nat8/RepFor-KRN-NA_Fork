package com.epam.test.services.customer;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class works with Credit File Info page
 *
 * @author Natalia Amelina
 */
public class CreditFileInfoService extends BaseService {

	private static final Logger LOG = Logger.getLogger(CreditFileInfoService.class);

	private CreditFileInfoPage creditFileInfoPage;

	/**
	 * Class constructor. It initializes Credit File Info page
	 *
	 * @param driver
	 *            - web driver instance which is defined for all tests
	 */
	public CreditFileInfoService(WebDriver driver) {
		super(driver);
		creditFileInfoPage = ScreenFactory.inst().getCreditFileInfoPage(driver);
	}

	/**
	 * Method for checking display of Credit File Info page fields
	 *
	 * @return true if all fields of Credit File Info page is displayed; false
	 *         otherwise
	 */
	public boolean isFieldsDisplayed() {
		LOG.debug("Is fields of Credit File Info displayed?");
		boolean result = creditFileInfoPage.categoryCodeDropDown.isDisplayed()
		        && creditFileInfoPage.accountNumberDropDown.isDisplayed()
		        && creditFileInfoPage.dueDateCalendar.isDisplayed()
		        && creditFileInfoPage.remarksInput.isDisplayed()
		        && creditFileInfoPage.printLetterSwitcher.isDisplayed()
		        && isSaveDisplayed() && creditFileInfoPage.cancelButton.isDisplayed();
		LOG.debug("Is fields displayed? " + result);
		return result;
	}

	/**
	 * Method for checking display of Credit File Info page fields
	 *
	 * @return true if Save Credit File Info button is displayed; false
	 *         otherwise
	 */
	public boolean isSaveDisplayed() {
		LOG.debug("Is Save button displayed?");
		boolean result = creditFileInfoPage.saveButton.isDisplayed();
		LOG.debug("Is Save button displayed? " + result);
		return result;
	}

	/**
	 * Method for click on the Save Credit File Info button
	 */
	public void save() {
		LOG.debug("Click Save button");
		creditFileInfoPage.saveButton.click();
		LOG.debug("Save button pressed");
	}

	/**
	 * Method for click on the Cancel button. It allows to cancel creation of
	 * Credit File Info
	 */
	public void cancel() {
		LOG.debug("Click Cancel button");
		creditFileInfoPage.cancelButton.click();
		LOG.debug("Cancel button pressed");
	}

	/**
	 * Method for filling fields of Save Credit File Info page
	 *
	 * @param creditFileBO
	 *            - object contains values of Credit File Info page fields
	 */
	public void setCreditInfo(CreditFileBO creditFileBO) {
		LOG.debug("Fill fields of Credit File Info page with " + creditFileBO);
		selectCode(creditFileBO.getCode());
		setDueDate(creditFileBO.getDate());
		setRemarks(creditFileBO.getRemarks());
		setPrint(creditFileBO.isPrint());
		LOG.debug("Fields of Credit File Info page was filled");
	}

	/**
	 * Method receives values of Credit File Info page fields
	 *
	 * @return CreditFileBO is filled by values of opened Credit File Info Page
	 */
	public CreditFileBO getCreditInfo() {
		LOG.debug("Get values of Credit File Info page fields");
		CreditFileBO result = new CreditFileBO(getCode(), getAccount(),
		        getDueDate(), getRemarks(), isPrint());
		LOG.debug("Current values of Credit File Info page = " + result);
		return result;
	}

	/**
	 * Method for comparison current values of Credit File Info page fields and
	 * expected
	 *
	 * @param expected
	 *            - object is filled with values are expected in the fields of
	 *            Credit File Info page
	 * @return true if Credit File Info page is filled with expected object;
	 *         false otherwise
	 */
	public boolean checkCreditInfo(CreditFileBO expected) {
		LOG.debug("Compare current values of Credit File Info page with " + expected);
		boolean result = getCreditInfo().equals(expected);
		LOG.debug("Are forms equals? " + result);
		return result;
	}

	/**
	 * Method for choose value from Category Code field
	 *
	 * @param value
	 *            - value which need to choose in the Category Code field
	 * @return true if value is set according with the parameter; false
	 *         otherwise
	 */
	public boolean selectCode(String value) {
		LOG.debug(String.format("Select value \"%s\" of Category Code field", value));
		boolean result = creditFileInfoPage.categoryCodeDropDown.selectValue(value);
		LOG.debug("Is value selected? " + result);
		return result;
	}

	/**
	 * Method for checking of possibility to set incorrect values to Account
	 * Number field
	 *
	 * @param incorrectValues
	 *            - list contains values which invalid for Account Number field
	 * @return true if Account Number field isn't filled; false otherwise
	 */
	public boolean checkPutIncorrectInAccount(List<String> incorrectValues) {
		LOG.debug("Set incorrect values to Account Number field " + incorrectValues);
		for (String incorrect : incorrectValues) {
			creditFileInfoPage.accountNumberDropDown.setValue(incorrect);

			if (!creditFileInfoPage.accountNumberDropDown.isEmpty()) {
				LOG.debug("Incorrect value is present in Amount field " + incorrect);
				return false;
			}

		}
		LOG.debug("Incorrect values don't set to Account Number field");
		return true;
	}

	/**
	 * Method for set the first available value in Account Number field
	 *
	 * @return current value of Account Number field
	 */
	public String selectAccountFirstAvailable() {
		LOG.debug("Select the first available value of Account Number field");
		String value = creditFileInfoPage.accountNumberDropDown.selectValueFirstAvailable();
		LOG.debug("Current value of Account Number field = " + value);
		return value;
	}

	/**
	 * Method for choose value from Due Date field
	 *
	 * @param value
	 *            - value which need to choose in the Due Date field
	 * @return true if value is set according with the parameter; false
	 *         otherwise
	 */
	public boolean setDueDate(String value) {
		LOG.debug(String.format("Select value \"%s\" of Due Date field", value));
		boolean result = creditFileInfoPage.dueDateCalendar.setDate(value);
		LOG.debug(String.format("Is Due Date field with %s? %b", value, result));
		return result;
	}

	/**
	 * Method for set value in Remarks field
	 *
	 * @param value
	 *            - value which need to set in the Remarks field
	 * @return true if value is set according with the parameter; false
	 *         otherwise
	 */
	public boolean setRemarks(String value) {
		LOG.debug(String.format("Set value \"%s\" to Remarks field", value));
		boolean result = creditFileInfoPage.remarksInput.setValue(value);
		LOG.debug(String.format("Is Remarks field filled with %s? %b", value, result));
		return result;
	}

	/**
	 * Method for set value of Print Letter switch field
	 *
	 * @param value
	 *            - value which need to set in the Print Letter field
	 * @return true if value is set according with the parameter; false
	 *         otherwise
	 */
	public boolean setPrint(boolean value) {
		LOG.debug("Switch Print Letter in", value);
		boolean result = creditFileInfoPage.printLetterSwitcher.switchTo(value);
		LOG.debug(String.format("Is Print Letter switched in %b? %b", value, result));
		return result;
	}

	/**
	 * Method for get value of Category Code field
	 *
	 * @return current value of Category Code field
	 */
	public String getCode() {
		LOG.debug("Get value of Category Code field");
		String result = creditFileInfoPage.categoryCodeDropDown.getValue();
		LOG.debug("Current value of Category Code field = " + result);
		return result;
	}

	/**
	 * Method for get value of Account Number field
	 *
	 * @return current value of Account Number field
	 */
	public String getAccount() {
		LOG.debug("Get value of Account Number field");
		String result = creditFileInfoPage.accountNumberDropDown.getValue();
		LOG.debug("Current value of Account Number field = " + result);
		return result;
	}

	/**
	 * Method for get value of Due Date field
	 *
	 * @return current value of Due Date field
	 */
	public String getDueDate() {
		LOG.debug("Get value of Due Date field");
		String result = creditFileInfoPage.dueDateCalendar.getDate();
		LOG.debug("Current value of Due Date field = " + result);
		return result;
	}

	/**
	 * Method for get value of Remarks field
	 *
	 * @return current value of Remarks field
	 */
	public String getRemarks() {
		LOG.debug("Get value of Remarks field");
		String result = creditFileInfoPage.remarksInput.getValue();
		LOG.debug("Current value of Remarks field = " + result);
		return result;
	}

	/**
	 * Method for get value of Print Letter field
	 *
	 * @return current value of Print Letter field
	 */
	public boolean isPrint() {
		LOG.debug("Get value of Print Letter field");
		boolean result = creditFileInfoPage.printLetterSwitcher.isSwitch();
		LOG.debug("Current value of Print Letter field = " + result);
		return result;
	}

	/**
	 * Method for clean Due Date field
	 */
	public void clearDueDate() {
		LOG.debug("Clear Due Date");
		creditFileInfoPage.dueDateCalendar.clear();
		LOG.debug("Due Date was cleaned");
	}

	/**
	 * Method for clean Remarks field
	 */
	public void clearRemarks() {
		LOG.debug("Clear Remarks");
		creditFileInfoPage.remarksInput.clear();
		LOG.debug("Remarks was cleaned");
	}

	/**
	 * Method for checking the filling of Due Date field
	 *
	 * @return true if field is empty; false otherwise
	 */
	public boolean isDueDateEmpty() {
		LOG.debug("Check Due Date is empty");
		boolean result = creditFileInfoPage.dueDateCalendar.isEmpty();
		LOG.debug("Is Due Date empty? " + result);
		return result;
	}

	/**
	 * Method for checking the filling of Remarks field
	 *
	 * @return true if field is empty; false otherwise
	 */
	public boolean isRemarksEmpty() {
		LOG.debug("Check Remarks is empty");
		boolean result = creditFileInfoPage.remarksInput.isEmpty();
		LOG.debug("Is Remarks empty? " + result);
		return result;
	}

	/**
	 * for checking the validation error of Category Code field
	 *
	 * @return true if red border and error text are displayed; false otherwise
	 */
	public boolean isCategoryErrorDisplayed(String expectedError) {
		LOG.debug("Check red border and error message are displayed for Category Code");
		boolean result = creditFileInfoPage.categoryErrorDropDown.isErrorBoxDisplayed()
		        && creditFileInfoPage.categoryErrorDropDown.checkErrorText(expectedError);
		LOG.debug("Are red border and error message displayed for Category Code? " + result);
		return result;
	}

	/**
	 * Method for checking the validation error of Due Date field
	 *
	 * @return true if red border and error text are displayed; false otherwise
	 */
	public boolean isDueDateErrorDisplayed(String expectedError) {
		LOG.debug("Check red border and error message are displayed for Due Date");
		boolean result = creditFileInfoPage.dueDateErrorCalendar.isErrorBoxDisplayed()
		        && creditFileInfoPage.dueDateErrorCalendar.checkErrorText(expectedError);
		LOG.debug("Are red border and error message displayed for Due Dates? " + result);
		return result;
	}

	/**
	 * Method for checking the validation error of Remarks field
	 *
	 * @return true if red border and error text are displayed; false otherwise
	 */
	public boolean isRemarksErrorDisplayed(String expectedError) {
		LOG.debug("Check red border and error message are displayed for Remarks");
		boolean result = creditFileInfoPage.remarksErrorInput.isErrorBoxDisplayed()
		        && creditFileInfoPage.remarksErrorInput.checkErrorText(expectedError);
		LOG.debug("Are red border and error message displayed for Remarks? " + result);
		return result;
	}

}