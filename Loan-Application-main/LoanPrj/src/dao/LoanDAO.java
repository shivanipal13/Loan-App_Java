package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import entity.LoanEntity;
import entity.NewLoanApplication;
import operation.LoanOperation;
import validation.LoanValidation;

public class LoanDAO {
	Scanner sc = new Scanner(System.in);
	LoanValidation loanValidation = new LoanValidation();
	LoanOperation loanOperation=new LoanOperation();
	

	public static double loanRemain(Connection con, long loanId) throws SQLException {

		try {
			String str = "select * from (select AMOUNT_REMAINING from loan_report where loan_id=? order by payment_date desc) where rownum=1";
			PreparedStatement ps = con.prepareStatement(str);
			ps.setDouble(1, loanId);
			ResultSet rs = ps.executeQuery();

			double remainAmount = 0;
			while (rs.next()) {
				remainAmount = rs.getDouble(1);
			}

			return remainAmount;
			
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}
	
	
	
	

	public void updateEmail(Connection con, String newEmail, long accNo) {
		try {


			if (loanValidation.checkAccNoAvailable(con, accNo)) {

				String str = "update customer_details set email=? where account_number=?";
				
				PreparedStatement ps = con.prepareStatement(str);
				
				ps.setString(1, newEmail);
				ps.setLong(2, accNo);
				ps.executeUpdate();

			} else {
				System.out.println("Account Number not in database");
			}
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void updateAddress(Connection con, String newStreetAdr, int newPinCode, String newCity, long accNo) {
		try {

			if (loanValidation.checkAccNoAvailable(con, accNo)) {

				String str = "update customer_details set STREET_ADDRESS=?, PINCODE=?, CITY=? where account_number=?";
				PreparedStatement ps = con.prepareStatement(str);
				ps.setString(1, newStreetAdr);
				ps.setInt(2, newPinCode);
				ps.setString(3, newCity);
				ps.setLong(4, accNo);
				ps.executeUpdate();

			} else {
				System.out.println("Account Number not in database");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void updateContact(Connection con, long contactNo, long accNo) {
		try {

			if (loanValidation.checkAccNoAvailable(con, accNo)) {

				String str2 = "update customer_details set contact_number=? where account_number=?";
				
				PreparedStatement ps2 = con.prepareStatement(str2);
				ps2.setLong(1, contactNo);
				ps2.setLong(2, accNo);
				
				ps2.executeUpdate();

			} 
			else {
				System.out.println("Account Number not in database");
			}
			
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList searchAccountNumber(Connection con, String email) throws SQLException 
	{
		ArrayList data = new ArrayList();
		
		String str1 = "select account_number from customer_details where email=?";
		
		PreparedStatement ps = con.prepareStatement(str1);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			data.add(rs.getLong(1));
		}

		return data;
	}
	
	public long searchAccountNumber(Connection con, Long loanId) throws SQLException 
	{
		long accNo=0;
		
		String str1 = "select account_number from loan_approved where loan_id=?";
		
		PreparedStatement ps = con.prepareStatement(str1);
		ps.setLong(1, loanId);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			accNo=rs.getLong(1);
		}

		return accNo;
	}
	

	public long getContactNumber(Connection con, long accountNumber) throws SQLException 
	{
		long contactNumber=0;

		String str1 = "select contact_number from customer_details where account_number=?";
		
		PreparedStatement ps = con.prepareStatement(str1);
		ps.setLong(1, accountNumber);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			contactNumber = rs.getLong(1);
		}
		
		return contactNumber;
	}

	public void updateIncome(Connection con, Double newIncome, long accNo) {
		try {

			if (loanValidation.checkAccNoAvailable(con, accNo)) {

				String str2 = "update customer_details set Income=? where account_number=?";
				
				PreparedStatement ps2 = con.prepareStatement(str2);
				ps2.setDouble(1, newIncome);
				ps2.setLong(2, accNo);
				
				ps2.executeUpdate();

			} 
			
			else {
				System.out.println("Account Number not in database");
			}
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}

//	shivani
	public ArrayList<LoanEntity> getAllRecords(Connection con, long accNo) throws SQLException {
		ArrayList data = new ArrayList();

		String str = "select * from customer_details where account_number=?";
		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, accNo);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			data.add(rs.getLong(1));
			data.add(rs.getString(2));
			data.add(rs.getString(3));
			data.add(rs.getDate(4));
			data.add(rs.getLong(5));
			data.add(rs.getString(6));
			data.add(rs.getString(7));
			data.add(rs.getInt(8));
			data.add(rs.getString(9));
			data.add(rs.getString(10));
			data.add(rs.getLong(11));
			data.add(rs.getDouble(12));
			data.add(rs.getString(13));
			data.add(rs.getString(14));

		}
		
		return data;

	}
	
	
	public boolean loanClearedOrNot(Connection con, long loanId) throws SQLException {
		String str="select count(*) from loan_cleared where loan_id=?";
		PreparedStatement ps=con.prepareStatement(str);
		
		ps.setLong(1, loanId);
		ResultSet rs= ps.executeQuery();
		
		int c=0;
		while(rs.next()) {
			c=rs.getInt(1);
		}
		
		if(c>0) return true;
		
		return false;
		
	}
	
	
	public  void getReport(Connection con,long loanId) throws SQLException {
        String str="select * from loan_report where loan_id=? order by payment_date";
        PreparedStatement ps = con.prepareStatement(str);
        ps.setLong(1,loanId);
        ResultSet rs = ps.executeQuery();
        System.out.println("loanid\t\tamountremaining\t\tpaymentdate\t\temi\t\tdurationremaining");
        while(rs.next()) {
            long loanid=rs.getLong(1);
            double amountremaining=rs.getDouble(2);
            Date paymentdate=rs.getDate(3);
            long emi=rs.getLong(4);
            int durationremaining=rs.getInt(5);



            System.out.println(loanid+"\t\t\t"+amountremaining+"\t\t"+paymentdate+"\t\t"+emi+"\t\t"+durationremaining);

        }
    }
	
	
	
	

	public double totalAmount(Connection con, long loanId) throws SQLException {

		String str = "select TOTAL_PRINCIPAL_AMOUNT_TAKEN, TOTAL_INTEREST from loan_report where loan_id=?";
		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, loanId);
		ResultSet rs = ps.executeQuery();

		double principalAmountTaken = 0;
		double interest = 0;

		while (rs.next()) {
			principalAmountTaken += rs.getDouble(1);
			interest += rs.getDouble(2);
		}

		return (interest + principalAmountTaken);
	}

	public int countTotal(Connection con, long loanId) throws SQLException {
		
		String str = "select count(*) from loan_report where loan_id=?";
		
		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, loanId);
		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}

		return count;
	}

	public String getEmail(Connection con, long accountNumber3) throws SQLException {
		
		String email = "";
		
		String str1 = "select email from customer_details where account_number=?";
		
		PreparedStatement ps = con.prepareStatement(str1);
		ps.setLong(1, accountNumber3);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			email = rs.getString(1);
		}
		
		return email;
	}
	
	
	
	public void insertLoanReport(Connection con, long loanId, NewLoanApplication newLoanApplication) throws SQLException {
		String str="insert into loan_report values(?,?,sysdate,?,?)";
		PreparedStatement ps = con.prepareStatement(str);
		
		
		
		double totAmount=loanOperation.overAllAmount2( newLoanApplication.getAmount(), 
				newLoanApplication.getLoanType());
		
		double emi = (totAmount / newLoanApplication.getLoanDuration());
		
		
		ps.setLong(1, loanId);
		ps.setDouble(2, totAmount);
		ps.setDouble(3, emi);
		ps.setInt(4, newLoanApplication.getLoanDuration());
		
		ps.executeQuery();
		
	}
	
	
	
	public void insertLoanApproved(Connection con, NewLoanApplication newLoanApplication) throws SQLException {
		
		String str="insert into loan_approved values(loan_idseq.NEXTVAL,?)";
		
		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, newLoanApplication.getAccNo());
		ps.executeUpdate();
		
		
		String str2="select loan_idseq.currval from dual";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(str2);
		
		long loanId=0;
		while(rs.next()) {
			loanId=rs.getLong(1);
		}
		
		LoanDAO loanDao = new LoanDAO();
		
		loanDao.insertLoanReport(con, loanId, newLoanApplication);
		
	}
	
	

	public void insertApplication(Connection con, NewLoanApplication newLoanApplication, String status) {
		
		LoanDAO loanDao = new LoanDAO();
		
		try {

			if (loanValidation.checkAccNoAvailable(con, newLoanApplication.getAccNo())) {

				String str2 = "insert into loan_application values(?,?,?,sysdate,?,?)";
				
				PreparedStatement ps2 = con.prepareStatement(str2);
				
				ps2.setLong(1, newLoanApplication.getAccNo());
				ps2.setDouble(2, newLoanApplication.getAmount());
				ps2.setString(3, newLoanApplication.getLoanType());
				ps2.setInt(4, newLoanApplication.getLoanDuration());
				ps2.setString(5, status);
				ps2.executeUpdate();
				
				
				loanDao.insertLoanApproved(con, newLoanApplication);

			} 
			else {
				System.out.println("Account Number not in database");
			}
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	

	public ArrayList getLoanReport(Connection con, long loanId) throws SQLException {
        ArrayList loanEntity5 = new ArrayList();
        String str1 = "select * from(select * from loan_report where loan_id=? order by payment_date desc) where rownum<=12";
        PreparedStatement ps = con.prepareStatement(str1);
        ps.setLong(1, loanId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {

            loanEntity5.add(rs.getLong(1));
            loanEntity5.add(rs.getDate(2));
            loanEntity5.add(rs.getLong(3));
            loanEntity5.add(rs.getLong(4));
            loanEntity5.add(rs.getLong(5));
            loanEntity5.add(rs.getLong(6));
            loanEntity5.add(rs.getLong(7));
            loanEntity5.add(rs.getInt(8));

        }
        return loanEntity5;
    }


}
