package validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.*;

public class LoanValidation {

//    validation for Account NUmber    

	public boolean checkAccNo(long accNo) {
		if (accNo <= 0)
			return false;
		int acoountNumberLength = String.valueOf(accNo).length();
		if (acoountNumberLength < 12)
			return false;
		else
			return true;
	}

//    validation for first name
	public boolean checkFirstName(String firstName) {
		char ch;
		boolean valid = false;

		for (int i = 0; i < firstName.length(); i++) {
			ch = firstName.charAt(i);
			if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
				valid = true;
			else
				valid = false;
		}
		if (valid == true)
			return true;
		else
			return false;
	}

//    validation for last name
	public boolean checkLastName(String lastName) {
		char ch;
		boolean valid = false;

		for (int i = 0; i < lastName.length(); i++) {
			ch = lastName.charAt(i);
			if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
				valid = true;
			else
				valid = false;
		}
		if (valid == true)
			return true;
		else
			return false;
	}

//    validation for contact Number
	public boolean checkContactNumber(long contactNumber) {
		if (contactNumber <= 0)
			return false;
		int contactNumberLength = String.valueOf(contactNumber).length();
		if (contactNumberLength < 10)
			return false;
		else
			return true;
	}

//    validation for Aadhaar number

	public boolean checkAadharCard(long aadharCard) {
		if (aadharCard <= 0)
			return false;
		int aadharCardLength = String.valueOf(aadharCard).length();
		if (aadharCardLength < 12)
			return false;
		else
			return true;
	}

//    validation for Income  number

	public boolean checkIncome(double income) {
		if (income <= 0)
			return false;
		else if (income > 300000) {
			System.out.println("show the income proof !!");
			return false;
		} else
			return true;
	}

//    validation for income Proof

	public boolean checkIncomeProof(long incomeProof) {
		if (incomeProof <= 0)
			return false;
		int incomeProofLength = String.valueOf(incomeProof).length();
		if (incomeProofLength < 6)
			return false;
		else
			return true;
	}

//    validation for  email
	public boolean checkEmail(String email) {
		int dot = email.lastIndexOf(".");// lastIndexOf : will return last the position
		int at = email.indexOf("@"); // will return the position

		if (dot > -1 && at > -1 && (at < dot))
			// 1 2 3
			return true;
		else
			return false;
	}
//    validation for  pincode

	public boolean checkPinCode(int pincode) {
		if (pincode <= 0)
			return false;
		int pincodeLength = String.valueOf(pincode).length();
		if (pincodeLength < 6)
			return false;
		else
			return true;
	}

//    validation for nominee city
	public boolean checkCity(String city) {
		char ch;
		boolean valid = false;

		for (int i = 0; i < city.length(); i++) {
			ch = city.charAt(i);
			if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
				valid = true;
			else
				valid = false;
		}
		if (valid == true)
			return true;
		else
			return false;
	}

//    validation for nominee city
	public boolean checkPanCard(String panCard) {
		String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
		Pattern p = Pattern.compile(regex);
		if (panCard == null) {
			return false;
		}
		Matcher m = p.matcher(panCard);

		return m.matches();
	}

//    validation for  address
	public boolean checkAddress(String address) {
		int AddressLength = address.length();

		if (AddressLength <= 0)
			return false;
		if (AddressLength >= 40)
			return false;
		else
			return true;
	}

//	Validation to check LoanId

	public boolean checkLoanId(Connection con, long loanId) throws SQLException {

		try {
			
			String str = "select count(*) from loan_report where loan_id=?";
			
			PreparedStatement ps = con.prepareStatement(str);
			ps.setLong(1, loanId);
			ResultSet rs = ps.executeQuery();

			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}

			if (count > 0)
				return true;

		} 
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return false;

	}
	
	
	

//	Validation to check Account Number 
	public boolean checkAccNoAvailable(Connection con, long accNo) throws SQLException {
		try {
			String str = "select count(*) from customer_details where account_number=?";
			PreparedStatement ps = con.prepareStatement(str);
			ps.setLong(1, accNo);
			ResultSet rs = ps.executeQuery();

			long c = 0;
			while (rs.next()) {
				c = rs.getLong(1);
			}

			if (c>0)
				return true;
			
			return false;

		}
		catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

}