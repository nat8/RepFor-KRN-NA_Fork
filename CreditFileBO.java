package com.epam.test.bo;

import java.util.logging.Logger;

/**
 * Class contains values of Credit File page fields
 *
 * @author Natalia Amelina
 */
public class CreditFileBO {

	private static final Logger LOG = Logger.getLogger(CreditFileBO.class);

	private String code;
	private String accountNumber;
	private String date;
	private String remarks;
	private boolean print;

	/**
	 * Class constructor
	 *
	 * @param code
	 *            - value of Category Code field
	 * @param date
	 *            - value of Due Date field
	 * @param remarks
	 *            - value of Remarks field
	 * @param print
	 *            - value of Print field
	 */
	public CreditFileBO(String code, String date, String remarks, boolean print) {
		super();
		this.code = code;
		this.date = date;
		this.remarks = remarks;
		this.print = print;
	}

	/**
	 * Class constructor
	 *
	 * @param code
	 *            - value of Category Code field
	 * @param accountNumber
	 *            - value of Account Number field
	 * @param date
	 *            - value of Due Date field
	 * @param remarks
	 *            - value of Remarks field
	 * @param print
	 *            - value of Print Letter field
	 */
	public CreditFileBO(String code, String accountNumber, String date, String remarks, boolean print) {
		super();
		this.code = code;
		this.accountNumber = accountNumber;
		this.date = date;
		this.remarks = remarks;
		this.print = print;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	/**
	 * Method for carrying this object to a string representing
	 *
	 * @return a string representation of this object
	 */
	@Override
	public String toString() {
		LOG.debug("Converting object to String representation");
		StringBuilder result = new StringBuilder();

		result.append(code).append(CharacterConstants.VERTICAL_LINE)
		        .append(accountNumber).append(CharacterConstants.VERTICAL_LINE)
		        .append(date).append(CharacterConstants.VERTICAL_LINE)
		        .append(print);

		LOG.debug("Credit File = " + result);
		return result.toString();
	}

	/**
	 * Method for comparison String of current object with String of other
	 * object
	 *
	 * @param expected
	 *            - object to compare
	 * @return true if the expected object represents a String equivalent to
	 *         String of this object, false otherwise
	 */
	public boolean equals(CreditFileBO expected) {
		LOG.debug(String.format("Compare: Actual = %s; Expected = %s ", this.toString(), expected.toString()));
		boolean result = this.toString().equals(expected.toString());
		LOG.debug("Result of comparison = " + result);
		return result;
	}

}