package operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.NewLoanApplication;

public class LoanOperation {

	public double getIncome(Connection con, long accno) throws SQLException {
		String str = "select income from customer_details where account_number=?";

		PreparedStatement ps = con.prepareStatement(str);
		ResultSet rs = ps.executeQuery();

		double result = 0;
		while (rs.next()) {
			result = rs.getDouble(1);
		}
		return result;
	}
	
	public double getEmiAmount(Connection con, long loanId) throws SQLException {
		String str="select * from (select MONTHLY_EMI from loan_report where loan_id=? order by PAYMENT_DATE desc) where rownum=1";
		
		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, loanId);
		ResultSet rs = ps.executeQuery();
		
		double emiAmount=0;
		while(rs.next()) {
			emiAmount=rs.getDouble(1);
		}
		
		return emiAmount;
	}


//	public double getLoanAmount(Connection con, long accno) throws SQLException {
//		String str = "select amount from loan_application where account_number=?";
//
//		PreparedStatement ps = con.prepareStatement(str);
//		ResultSet rs = ps.executeQuery();
//
//		double result = 0;
//		while (rs.next()) {
//			result = rs.getDouble(1);
//		}
//		return result;
//	}
//
//	public int getLoanDuration(Connection con, long accno) throws SQLException {
//		String str = "select loan_duration from loan_application where account_number=?";
//
//		PreparedStatement ps = con.prepareStatement(str);
//		ResultSet rs = ps.executeQuery();
//
//		int result = 0;
//		while (rs.next()) {
//			result = rs.getInt(1);
//		}
//		return result;
//	}

	public long getLoanId(Connection con, long accno) throws SQLException {//need to modify
		String str = "select loan_id from loan_report where account_number=?";

		PreparedStatement ps = con.prepareStatement(str);
		ResultSet rs = ps.executeQuery();

		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		return result;
	}



	public int getLoanRemainingDuration(Connection con, long loanId) throws SQLException {
		
		String str1 = "select * from(select DURATION_REMAINING from loan_report where loan_id=? order by payment_date desc) where rownum=1";
		
		PreparedStatement ps2 = con.prepareStatement(str1);
		ps2.setLong(1, loanId);
		ResultSet rs = ps2.executeQuery();
		
		
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		return result;
	}
	
	
	public double getLoanRemainingAmount(Connection con, long loan_id) throws SQLException {
		String str = "select * from(select amount_remaining from loan_report where loan_id=? order by payment_date desc)where rownum=1";

		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1,loan_id);
		ResultSet rs = ps.executeQuery();
		

		double result = 0;
		while (rs.next()) {
			result = rs.getDouble(1);
		}
//		System.out.println(result);
		
		return result;
	}
	
	
	public void updateLoanCleared(Connection con, long accNo, long loanId) {
		try {
			
//			String str2="select LOAN_TYPE from loan_application where account_number=?";
//			PreparedStatement ps2 = con.prepareStatement(str2);
//			ps2.setLong(1,accNo);
//			ResultSet rs = ps2.executeQuery();
//			
//			String loanType="";
//			while(rs.next()) {
//				loanType=rs.getString(1);
//			}

			String str="insert into loan_cleared values(?,?)";
			PreparedStatement ps = con.prepareStatement(str);
			ps.setLong(1, loanId);
			ps.setLong(2, accNo);
			ps.executeUpdate();
			
			
			
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		
	}
	
	
	public void updateLoanReport(Connection con, long loanId, double payableAmount, int duration) {
		try {
			
			LoanOperation loanOperation= new LoanOperation();

			double remainingAmount = loanOperation.getLoanRemainingAmount(con, loanId);
			
//			double remainingAmount=1000;
			String str = "insert into loan_report values (?, ?, sysdate, ?, ?)";
			PreparedStatement ps = con.prepareStatement(str);

			double newAmount=(remainingAmount - payableAmount);
			int newDuration = (duration - 1);
			
			ps.setLong(1, loanId);
			ps.setDouble(2, newAmount);
			ps.setDouble(3, payableAmount);
			ps.setInt(4, newDuration);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e);
		}

	}
	

	public double getAmountTaken(Connection con, long loan_id) throws SQLException {//need to modify
		String str = "select TOTAL_PRINCIPAL_AMOUNT_TAKEN from loan_report where loan_id=?";

		PreparedStatement ps = con.prepareStatement(str);
		ResultSet rs = ps.executeQuery();

		double result = 0;
		while (rs.next()) {
			result = rs.getDouble(1);
		}
		return result;
	}


	public double getInterestAmount(Connection con, long loan_id) throws SQLException {//modify
		String str = "select total_interest from loan_report where loan_id=?";

		PreparedStatement ps = con.prepareStatement(str);
		ResultSet rs = ps.executeQuery();

		double result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		return result;
	}

	public double overAllAmount2( double amount, String type) {
		double intrest=0;
		
		
		if (type == "Home loan")
		{
			intrest = (amount * 0.075);
			return (amount+intrest);
			
		}
		else if (type == "Personal loan") 
		{
			intrest = (amount * 0.12);
			return (amount+intrest);
		}
		else 
		{
			intrest = (amount * 0.0862);
			return (amount+intrest);
		}
		
		
	}

	
	
	
	public boolean loanApproval(Connection con, NewLoanApplication newLoanApplication) throws SQLException {
		String str = "select income from customer_details where account_Number=?";

		PreparedStatement ps = con.prepareStatement(str);
		ps.setLong(1, newLoanApplication.getAccNo());
		ResultSet rs = ps.executeQuery();

		double income = 0;
		while (rs.next()) {
			income = rs.getDouble(1);

		}

		if (newLoanApplication.getLoanType() == "Home loan") 
		{

			if (newLoanApplication.getAmount() <= ((income / 12) * 60)) return true;
			
		} 
		
		else if (newLoanApplication.getLoanType() == "Personal loan") 
		{

			if (newLoanApplication.getAmount() <= ((income / 12) * 40)) return true;
		} 
		else 
		{
			if (newLoanApplication.getAmount() <= ((income / 12) * 20)) return true;
		}

		return false;

	}


}
