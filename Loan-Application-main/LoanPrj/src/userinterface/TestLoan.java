package userinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import dao.LoanDAO;
import entity.*;
import operation.LoanOperation;
import validation.LoanValidation;


public class TestLoan {

	public static boolean bk(String s) {
		if (s == "v" || s == "V")
			return true;
		return false;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		LoanDAO loanDao = new LoanDAO();
		LoanOperation loanOperation = new LoanOperation();
		LoanValidation loanValidation = new LoanValidation();
		NewLoanApplication newLoanApplication = new NewLoanApplication();

		String choice;
		int ch;
		do {
			System.out.println("HDFC Bank");
			System.out.println("1. Display the Details");
			System.out.println("2. Search for Details");
			System.out.println("3. Update the Details");
			System.out.println("4. Apply for New Loan");// 332
			System.out.println("5. Emi-Payment");// 516

			System.out.println("6. Loan Payment History");
			System.out.println("7. Loan Cleared");
			System.out.println("8. Exit");
			System.out.println("Enter your choice...");
			ch = sc.nextInt();

			switch (ch) {

//			Displaying details

			case 1:

				long accNo;
				int ch1;
				System.out.println("Select details to display");

				do {
					System.out.println("1. Display all details ");
					System.out.println("2. Dispaly email");
					System.out.println("3. display contact number");
					System.out.println("4. Exit");
					ch1 = sc.nextInt();

					switch (ch1) {

					case 1:
						System.out.println("Displaying the Details");
						ArrayList loanEntity1 = new ArrayList();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connection is successful !!!");

							System.out.println("Enter Account number");
							accNo = sc.nextLong();

							loanEntity1 = loanDao.getAllRecords(con, accNo);
//							System.out.println("Account number:- " + loanEntity1.get(0));

							Iterator itr = loanEntity1.iterator();
							while (itr.hasNext()) {
								System.out.println(" - " + itr.next());
								System.out.println();
							}

						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());
						}
						break;

					case 2:
						System.out.println("displaying Email");
						String email = "";
						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17.QNX.local:1521:xe",
									"loan", "loan");
							System.out.println("Connection is successful !!!");

							System.out.println("enter the account number");
							accNo = sc.nextLong();

							email = loanDao.getEmail(con, accNo);// 192809487392
							System.out.println("Email :- " + email);

						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());

						}
						break;

					case 3:
						System.out.println("displaying Contact Number");
						long contactNumber;

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17.QNX.local:1521:xe",
									"loan", "loan");
							System.out.println("Connection is successful !!!");

							System.out.println("enter the account number");
							accNo = sc.nextLong();

							contactNumber = loanDao.getContactNumber(con, accNo);
							System.out.println("Contact No. :- " + contactNumber);

						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());

						}
						break;

					case 4:
						break;

					default:
						System.out.println("Invalid option");

					}

				} while (ch1 != 4);

				break;

//				Search for Account Number using Email

			case 2:

				System.out.println("displaying account number");
				ArrayList loanEntity6 = new ArrayList();

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17.QNX.local:1521:xe", "loan",
							"loan");
					System.out.println("Connection is successful !!!");

					System.out.println("enter the email id ");
					String email = sc.next();
					loanEntity6 = loanDao.searchAccountNumber(con, email);

					Iterator itr = loanEntity6.iterator();
					while (itr.hasNext()) {
						System.out.println(itr.next());
						System.out.println("-------------------");
					}
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());

				}

				break;

//				Updating details

			case 3:

				long accNo2;
				int ch2;
				System.out.println("Select details to be Updated ");

				do {
					System.out.println("1. Email ");
					System.out.println("2. Contact Number");
					System.out.println("3. Address");
					System.out.println("4. Income");
					System.out.println("5. Go back to previous menu");
					ch2 = sc.nextInt();

					switch (ch2) {

					case 1:
						System.out.println("Enter Account Number");
						accNo2 = sc.nextLong();
						System.out.println("Enter new Email-ID");
						String newEmail = sc.next();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connected");

							if (loanValidation.checkEmail(newEmail))
								loanDao.updateEmail(con, newEmail, accNo2);
							else
								System.out.println("Please enter a valid Email");

						}

						catch (SQLException e) {
							System.out.println(e);
						}

						break;

					case 2:

						System.out.println("Enter Account Number");
						accNo2 = sc.nextLong();
						System.out.println("Enter new Contact Number");
						long newContact = sc.nextLong();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connected");

							if (loanValidation.checkContactNumber(newContact))
								loanDao.updateContact(con, newContact, accNo2);
							else
								System.out.println("Please enter a valid Contact");

						}

						catch (SQLException e) {
							System.out.println(e);
						}

						break;

					case 3:

						System.out.println("Enter Account Number");
						accNo2 = sc.nextLong();

						System.out.println("Enter new Street Address");
						String newStreetAdr = sc.next();

						System.out.println("Enter new Pincode");
						int newPinCode = sc.nextInt();

						System.out.println("Enter new City");
						String newCity = sc.next();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connected");

							if (loanValidation.checkCity(newCity) && loanValidation.checkPinCode(newPinCode))
								loanDao.updateAddress(con, newStreetAdr, newPinCode, newCity, accNo2);
							else
								System.out.println("Please enter a valid Address");

						}

						catch (SQLException e) {
							System.out.println(e);
						}

						break;

					case 4:

						System.out.println("Enter Account Number");
						long accNo4 = sc.nextLong();

						System.out.println("Enter Income");
						Double newIncome = sc.nextDouble();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connected");

							if (loanValidation.checkIncome(newIncome))
								loanDao.updateIncome(con, newIncome, accNo4);
							else
								System.out.println("Please enter valid Income Value");

						}

						catch (SQLException e) {
							System.out.println(e);
						}

						break;

					case 5:
						break;

					default:
						System.out.println("Invalid option");

					}

				} while (ch2 != 5);

				break;

//				chirag

			case 4:

				int ch4, duration;
				long accNo1;
				double amount;
				String loanType;

				do {
					System.out.println("1. Home loan ");
					System.out.println("2. Personal loan");
					System.out.println("3. Vehicle loan");
					System.out.println("4. Back to previous menu");

					ch4 = sc.nextInt();
					ArrayList loanEntity2 = new ArrayList();

					switch (ch4) {

					case 1:

						loanType = "Home loan";
						System.out.println("Enter Account Number");
						accNo1 = sc.nextLong();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connection is successful !!!");

							loanEntity2 = loanDao.getAllRecords(con, accNo1);

							Iterator itr = loanEntity2.iterator();

							while (itr.hasNext()) {
								System.out.println(" - " + itr.next());
								System.out.println();
							}

							System.out.println("Press V if data is Valid else N");
							String lt = sc.next();

							switch (lt) {

							case "V":
								System.out.println("enter Loan amount");
								amount = sc.nextLong();

								System.out.println("enter Duration");
								duration = sc.nextInt();

								if (loanValidation.checkAccNoAvailable(con, accNo1)) {
									newLoanApplication.setAccNo(accNo1);
									newLoanApplication.setAmount(amount);
									newLoanApplication.setLoanDuration(duration);
									newLoanApplication.setLoanType(loanType);

									if (loanOperation.loanApproval(con, newLoanApplication)) {
										loanDao.insertApplication(con, newLoanApplication, "A");

									} else {
										loanDao.insertApplication(con, newLoanApplication, "N");
									}

								}

								else {
									System.out.println("Please enter a valid details");
								}

								break;

							case "N":
								break;

							default:
								System.out.println("Invalid option");
								break;
							}

						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());
						}

						break;

					case 2:
						loanType = "Personal loan";
						System.out.println("Enter Account Number");
						accNo1 = sc.nextLong();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connection is successful !!!");

							loanEntity2 = loanDao.getAllRecords(con, accNo1);

							Iterator itr = loanEntity2.iterator();

							while (itr.hasNext()) {
								System.out.println(" - " + itr.next());
								System.out.println();
							}

							System.out.println("Press V if data is Valid else N");
							String lt = sc.next();

							switch (lt) {
							case "V":

								System.out.println("enter Loan amount");
								amount = sc.nextLong();

								System.out.println("enter Duration");
								duration = sc.nextInt();

								if (loanValidation.checkAccNoAvailable(con, accNo1)) {
									newLoanApplication.setAccNo(accNo1);
									newLoanApplication.setAmount(amount);
									newLoanApplication.setLoanDuration(duration);
									newLoanApplication.setLoanType(loanType);

									if (loanOperation.loanApproval(con, newLoanApplication)) {
										loanDao.insertApplication(con, newLoanApplication, "A");
									} else {
										loanDao.insertApplication(con, newLoanApplication, "N");
									}

								}

								else {
									System.out.println("Please enter a valid details");
								}

								break;

							case "N":
								break;

							default:
								System.out.println("Invalid option");
								break;
							}

						}

						catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());
						}

						break;

					case 3:

						loanType = "Vehicle loan";
						System.out.println("Enter Account Number");
						accNo1 = sc.nextLong();

						try {
							Class.forName("oracle.jdbc.driver.OracleDriver");
							con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
							System.out.println("Connection is successful !!!");

							loanEntity2 = loanDao.getAllRecords(con, accNo1);

							Iterator itr = loanEntity2.iterator();

							while (itr.hasNext()) {
								System.out.println(" - " + itr.next());
								System.out.println();
							}

							System.out.println("Press V if data is Valid else N");
							String lt = sc.next();

							switch (lt) {
							case "V":
								System.out.println("enter Loan amount");
								amount = sc.nextLong();

								System.out.println("enter Duration");
								duration = sc.nextInt();

								if (loanValidation.checkAccNoAvailable(con, accNo1)) {
									newLoanApplication.setAccNo(accNo1);
									newLoanApplication.setAmount(amount);
									newLoanApplication.setLoanDuration(duration);
									newLoanApplication.setLoanType(loanType);

									if (loanOperation.loanApproval(con, newLoanApplication)) {
										loanDao.insertApplication(con, newLoanApplication, "A");
									} else {
										loanDao.insertApplication(con, newLoanApplication, "N");
									}

								}

								else {
									System.out.println("Please enter a valid details");
								}

								break;

							case "N":
								break;

							default:
								System.out.println("Invalid option");
								break;
							}

						}

						catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());
						}

						break;

					case 4:
						break;

					default:
						System.out.println("Invalid option");
						break;

					}

				} while (ch4 != 4);
				break;

//		dishant
//      select total_amount_remaining from loan_report where loan_id=987876 order by payment_date desc;
			case 5:

				double payableAmount;

				System.out.println("Please Enter your Loan Id to proceed for Payment");
				long loanId2 = sc.nextLong();

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
					System.out.println("Connected");

					int duration1 = loanOperation.getLoanRemainingDuration(con, loanId2);

					if (loanValidation.checkLoanId(con, loanId2)) {

//						System.out.println("Enter Amount to Pay");
						payableAmount = loanOperation.getEmiAmount(con, loanId2);

						if (payableAmount <= loanDao.loanRemain(con, loanId2)) {

							loanOperation.updateLoanReport(con, loanId2, payableAmount, duration1);
							
							double newDuration=loanOperation.getLoanRemainingDuration(con, loanId2);
							
							if(newDuration==0) {
								long accNo3 = loanDao.searchAccountNumber(con, loanId2);
								loanOperation.updateLoanCleared(con, accNo3, loanId2);
							}
						}

					} else {
						System.out.println("Enter correct Loan ID");
					}
				} catch (SQLException e) {
					System.out.println(e);
				}

				break;

			case 6:
				System.out.println("display loan report");
				
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
	                con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
	                System.out.println("Connected");
	                System.out.println("enter loan Id");
	                long loanId=sc.nextLong();
	                loanDao.getReport(con,loanId);
				}
                catch(SQLException e) {
                	System.out.println(e);
                }
				
				break;

			case 7:
				
				try {
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
	                con = DriverManager.getConnection("jdbc:oracle:thin:@QNX-VDIPWV17:1521:xe", "loan", "loan");
	                System.out.println("Connected");
	                
	                System.out.println("Enter Loan Id");
	                long loanId=sc.nextLong();
	                
	                if(loanDao.loanClearedOrNot(con,loanId)) 
	                {
	                	System.out.println("Loan Cleared for LoanID:- "+ loanId);
	                }
	                else 
	                {
	                	System.out.println("Please clear your Loan");
	                }
	                
	                
				}
				catch(SQLException e) {
					System.out.println(e);
				}
				
				break;

			case 8:
				System.out.println("Enter Account Number to get information regarding the Active Loans");
//				long accNo1=sc.nextLong();

				break;

			default:
				System.out.println("Enter a Valid Option ");
			}

			System.out.println("Do you want to continue y/n?");
			choice = sc.next();
		} while (choice.equals("Y") || choice.equals("y"));

	}
}
