import java.io.*;
import java.util.ArrayList;
import java.util.Date;

class Account{
	private Double balance;
	private Double profit;
	private Integer accountNumber;
	private String status;
	private static int nextAccountNumber=16000;
	public static final double rateOfInterest = 4.00;
	
	Account(Double balance){
		this.balance=balance;
		accountNumber=nextAccountNumber++;
		if(balance>=0) status= "active";
	}
	
	public void showAccountDetails(){
		System.out.print("Account Number: ");
		System.out.println(this.getAccountNumber());
		System.out.print("Current Balance: ");
		System.out.println(this.getBalance());
		System.out.print("Account Status: ");
		System.out.println(this.getStatus());
		}

	private static Double takeNumberInput() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		Double temp;
		do{
			try{
				temp = Double.parseInt(br.readLine());
			}
			catch(NumberFormatException e){
				System.out.println("Please enter integer value.");
				continue;
			}
			break;
		}while(true);
		return temp;
	}

	public void deposit(){
		Double amount;	
		Integer input = 1;
		do{
			System.out.println("How much money you want to deposit?");
			amount = takeNumberInput();
			if(amount>0){
				double temp=balance;
				balance+=amount;
				Bank.setTotalMoneyDeposited(Bank.getTotalMoneyDeposited()+amount);
				Bank.setCashInHand(Bank.getCashInHand()+amount);
				if(temp<1000) {
					System.out.println("Rs. 100 is deducted as your account balance was below \"Minimum Balance\" i.e Rs. 1000, before this transaction.");
					balance-=100;
				}
				System.out.println("Your updated balance is: " + balance.toString());
				if(balance>=0) status="active";
				else status="frozen";
				input=0;
			}
			else{
				System.out.println("Amount can not be 0 or negative. Please enter a positive amount.");
				do{
					System.out.println("Press 1 to try again");
					System.out.println("Press 0 to go back to previous menu");
					input= int(takeNumberInput());
					if(input==1 || input==0) break;
					else continue;
				}while(true);
			}
		}while(input!=0);
	}
	
	public void withdraw(){
		Integer amount;	
		int input=0;
		do{
			System.out.println("How much money you want to withdraw?");
			amount = takeNumberInput();
			if(amount>0){
				if(amount<=balance+1000){
					balance-=amount;
					Bank.setTotalMoneyDeposited(Bank.getTotalMoneyDeposited()+amount);
					Bank.setCashInHand(Bank.getCashInHand()+amount);
					System.out.println("Your updated balance is: " + balance.toString());
					if(balance<0) status="frozen";
				}
				else{
					System.out.println("You donot have enough balance to withdraw");
				}
			}
			else{
				System.out.println("Amount can not be 0 or negative. Please enter a positive amount.");
				do{
					System.out.println("Press 1 to try again");
					System.out.println("Press 0 to go back to previous menu");
					input=int(takeNumberInput());
					if(input==1 || input==0) break;
					else continue;
				}while(true);
			}
		}while(input!=0);
	}
	
	public String getStatus(){
		return status;
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public Double getBalance(){
		return balance;
	}


	public Double getRateOfInterest() {
		return rateOfInterest;
	}


	public boolean grantProfit(Double interest) {
		profit+=interest;
		balance+=interest;
		Bank.setTotalMoneyDeposited(Bank.getTotalMoneyDeposited()+interest);
		Bank.setCashInHand(Bank.getCashInHand()-interest);
		return true;
	}
	
}



abstract class Loan{
	protected double amount;
	protected Date startDate;
	protected Date endDate;
	protected Double tenureInYears;
	public String status;
	private int loanID;
	private static int loanIssueNumber=1;
	public Loan(int amount,Double tenureInYears2){
		this.amount=amount;
		this.tenureInYears=tenureInYears2;
		startDate = new Date();
		endDate = new Date();
		endDate.setYear((int) (startDate.getYear()+tenureInYears2));
		status="active";
		loanID=loanIssueNumber++;
	}
	abstract public double getDueAmount();
	abstract public void showLoanDetails();
	abstract public double getLoanRate();

	public double getLoanAmount(){
		return amount;
	}

	public Date getStartDate(){
		return startDate;
	}

	public Date getEndDate(){
		return endDate;
	}
	
	public int getLoanID(){
		return loanID;
	}
}

class EducationLoan extends Loan{
	public static String loanName = "\"Bright Future Education Loan\"";
	public static double rate = 10.0;
	private double amountDue;

	public EducationLoan(int amount, Double tenureInYears){
		super(amount,tenureInYears);
		amountDue=amount*tenureInYears*(100+rate)/100.0;
	}
	
	public void showLoanDetails(){
		System.out.print("Loan Name: ");
		System.out.println(getLoanName());
		System.out.print("Loan ID: ");
		System.out.println(getLoanID());
		System.out.print("Loan Amount: ");
		System.out.println(getLoanAmount());
		System.out.print("Loan Start Date: ");
		System.out.println(getStartDate());
		System.out.print("End Date: ");
		System.out.println(getEndDate());
		System.out.print("Due Amount: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}

	public String getLoanName(){
		return loanName;
	}

	public double getDueAmount(){
		return amountDue;
	}
}

class HomeLoan extends Loan{
	public static String loanName = "Secure life Home loans";
	public static double rate = 15.0;
	private double amountDue;
	
	public HomeLoan(int amount, Double tenureInYears){
		super(amount,tenureInYears);
		amountDue=amount*tenureInYears*(100+rate)/100;
	}

	public void showLoanDetails(){
		System.out.print("Loan Name: ");
		System.out.println(getLoanName());
		System.out.print("Loan ID: ");
		System.out.println(getLoanID());
		System.out.print("Loan Amount: ");
		System.out.println(getLoanAmount());
		System.out.print("Loan Start Date: ");
		System.out.println(getStartDate());
		System.out.print("End Date: ");
		System.out.println(getEndDate());
		System.out.print("Due Amount: ");
		System.out.println(getDueAmount());
	}

	public double getLoanRate(){
		return rate;
	}

	public String getLoanName(){
		return loanName;
	}

	public double getDueAmount(){
		return amountDue;
	}
}

class User{
	public String name;
	public String userName;
	public String password;

	public User(String name, String userName, String password){
		this.name=name;
		this.userName=userName;
		this.password=password;
	}
}

class Admin extends User{
	Bank bank;
	public Admin(String name, String userName, String password){
		super(name,userName,password);
	}
	public Object getUserName() {
		return userName;
	}
	public Object getPassword(){
		return password;
	}
}

class Customer extends User{
	private ArrayList<Account> accounts = new ArrayList<Account>();
	private ArrayList<Loan> loanList = new ArrayList<Loan>();
	public Customer(String name, String userName, String password){
		super(name,userName,password);
	}

	private static Double getIntegerInput() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		Integer temp;
		do{
			try{
				temp = Integer.parseInt(br.readLine());
			}
			catch(NumberFormatException e){
				System.out.println("Please enter integer value.");
				continue;
			}
			break;
		}while(true);
		return temp;
	}

	public Account getAccount(){
		Integer accountNumber;
		Account temp = null;
		Integer input = 1 ;
		do{
			System.out.println("Enter your account no");
			accountNumber= getIntegerInput();
			for(Account account : accounts){
				if(account.getAccountNumber()==accountNumber){
					temp=account;
				}
			}
			if(temp ==null){
				System.out.println("No account with that account Number");
				System.out.println("Press 1 to try again");
				System.out.println("Press 0 to go back to previous menu");
				input= getIntegerInput();
			}
			else{
				break;
			}
		}while(input!=0);
		return temp;
	}

	public void showAllLoanDetails(){
		System.out.print("Total Loans Issued are: ");
		System.out.println(loanList.size());
		for(Loan loan : loanList){
			if(loan.status=="active") loan.showLoanDetails();
		}
	}

	public double getTotalLoanAmount(){
		double temp=0;
		for(oan loan : loanList){
			temp+=loan.amount;
		}
		return temp;
	}

	public void openNewAccount(){
		Double amount;
		// Input Amount
		System.out.println("How much money would you like to deposit now? Minimum amount to open an account is Rs.1000");
		amount = getDoubleInput();
		if(amount<1000) System.out.println("Sorry we cant open an account with amount " + amount.toString());
		else {
			System.out.println("Okay! You will have "+ amount.toString() + " as opening balance"); 
			break;
		}
		Account temp = new Account(amount);
		if(accounts.add(temp)) {
			System.out.println("Account created.");
			temp.showAccountDetails();
		}
	}	

	public void issueNewLoan(){
		if (loanList.size()>10) {
			System.out.println("Sorry! You cannot have more than 10 loans");
			return;
		}
		String type;
		Double amount;
		Double tenureInYears;
		do{
			System.out.println("Enter Type of your loan:\nEnter \"HomeLoan\" for Home Loan\nEnter \"EducationLoan\" for Education Loan");
			type = getStringInput();
			if(type.equals("HomeLoan") || type.equals("EducationLoan")) break;
			else{
				System.out.println("Please enter one of the two mentioned types.\n Note: Enter type without quotes.");
			}
		}while(true);
		do{
			System.out.println("Enter loan amount");
			amount = getDoubleInput();
			if(this.getTotalLoanAmount()+amount>1000000){
			System.out.println("Sorry! We donot lend more than one million rupees per customer. Try a small amount.");
			}
			else{
				break;
			}
		}while(true);

		do{
			System.out.println("Enter loan duration in years");
			tenureInYears = getDoubleInput();
			if(tenureInYears<=0){
			System.out.println("Sorry! duration cannot be 0");
			}
			else{
				break;
			}
		}while(true);


		if(type=="HomeLoan") {
			HomeLoan temp = new HomeLoan(amount,tenureInYears);
			if(loanList.add(temp)){
				System.out.println("Your loan is issued");
				temp.showLoanDetails();
			}
		}
		if(type=="EducationLoan") {
			EducationLoan temp = new EducationLoan(amount,tenureInYears);
			if(loanList.add(temp)){
				System.out.println("Your loan is issued");
				temp.showLoanDetails();
			}
		}
	}

	public void payLoanDueAmount(){
		int loanID = getLoanID();
		for(Loan loan : loanList){
			if ( loan.getLoanID() == loanID ){
				if(loan.getDueAmount()<balance){
					System.out.println("Sorry! Not enough balance in your account");
				}
				else {
					System.out.println("From which account you would like to pay money?")
					Account temp =getAccount();
					temp.setBalance()=temp.getBalance()-loan.getDueAmount();
					loan.status="inactive";
					loanList.remove(loan);
				}
			}
		}

	public void showCustomerDetails() {
			
	}
	
	public Integer getNoOfAccount(){
		return accounts.size();
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}
}

class Bank{
	private Admin admin;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private static Double cashInitially=10000000.0;
	private static Double cashInHand = 10000000.0;
	private static Double totalMoneyDeposited=0.0;
	private static Double totalMoneyLent=0.0;
	private static boolean bankStarted=false;
	public static Bank bank;
	public static int customerID=0;

	private Bank(){
		admin = new Admin("Gopal Goel","admin","admin");
		admin.bank=this;
		bank=this;
		bankStarted=true;
		customers.add(new Customer("Gopal Goel","gopalgoel","iamgopal"));
	}
	public static Bank getBank(){
		if(bankStarted==false){
			return new Bank();
		}
		else return bank;
	}
	
	public static Double getCashInHand(){
		return cashInHand;
	}
	
	public static Double getTotalMoneyDeposited(){
		return totalMoneyDeposited;
	}
	
	public static void setCashInHand(Double amount){
		cashInHand=amount;
	}
	
	public static void setTotalMoneyDeposited(Double amount){
		totalMoneyDeposited=amount;
	}
	
	public static void setTotalMoneyLent(Double amount){
		totalMoneyLent=amount;
	}

	public void showBankDetails(Admin admin){
		if (admin!=this.admin) return;
		System.out.println("Total no. of customers: " + customers.size());
		System.out.println("Total no. of accounts: " + getAccountsCount());
		System.out.println("Total no. of loans: " + getTotalLoans());
		System.out.println("Total money deposited: " + getTotalMoneyDeposited());
		System.out.println("Total money lent: " + getTotalMoneyDeposited());
		System.out.println("Total cash in hand: " + getCashInHand());
		System.out.println("Total profit earned: " + getProfit());
	}

	public static double getProfit() {
		return (cashInHand-cashInitially);
	}

	public static Double getTotalLoans() {
		return totalMoneyLent;
	}
	public Integer getAccountsCount() {
		Integer count=0;
		for(Customer cus : customers){
			count+=cus.getNoOfAccount();
		}
		return count;
	}
	public void addCustomers() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		String name;
		String userName;
		String password;

		//input name
		do{
			System.out.println("Your full name please?");
			name= new String(br.readLine().toUpperCase());
			if(name.matches("[A-Z]+\\s[A-Z]+")){
				System.out.println("Welcome "+ name);
				break;
			}
			else{
				System.out.println("Sorry the format of name should be \"FirstName LastName\"");
			}
		}while(true);

		// input username
		do{
			System.out.println("Choose a username containing only letters.");
			userName= new String(br.readLine());
			if(userName.matches("[a-zA-Z]+")){
				if(checkUsername(userName)){
					System.out.println("Username already exists. Please Choose another.");
				}
				else{
					System.out.println("Your username is: "+ name);
					break;
				}
			}
			else{
				System.out.println("Invalid username.");
			}
		}while(true);

		// input password
		do{
			System.out.println("Choose a password.");
			password= new String(br.readLine());
			System.out.println("Type your chosen password again.");
			String temp= new String(br.readLine());
			if(password.equals(temp)){
				System.out.println("Password chosen succesfully.");
				break;
			}
			else{
				System.out.println("Passwords didn't match.");
			}
		}while(true);

		Customer newCustomer = new Customer(name,userName,password);
		if(customers.add(newCustomer)) {
			System.out.println("You are now our customer.");
			newCustomer.showCustomerDetails();
		}
	}
	
	private boolean checkUsername(String userName){
		for(Customer customer : customers){
			if (customer.userName.equals(userName)) return true;
		}
		return false;
	}


	public Customer customerLogin() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		Customer temp=null;
		String userName;
		String password;
		Integer input = 1;
		do{
			System.out.println("Enter username");
			userName= new String(br.readLine());
			System.out.println("Enter password.");
			password= new String(br.readLine());
			temp=null;
			for(Customer cust : customers){
				if(cust.userName.equals(userName) && cust.password.equals(password)){ 
					temp = cust;
				}
			}
			if (temp==null){
				System.out.println("username and password didnt match.");
				do{
					System.out.println("Press 1 to try again");
					System.out.println("Press 0 to go back to previous menu");
					try{
						input= Integer.parseInt(br.readLine());
					}
					catch(NumberFormatException e){
						System.out.println("Select one of the above options.");
						continue;
					}
					break;
				}while(true);
			}
			else{
				System.out.println("Logging in..");
				break;
			}
		}while(input!=0);
		return temp;
	}

	public Admin adminLogin() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Admin temp=null;
		String userName;
		String password;
		Integer input = 1;
		do{
			System.out.println("Enter username");
			userName = new String(br.readLine());
			System.out.println("Enter password.");
			password = new String(br.readLine());
			temp=null;
			if(admin.getUserName().equals(userName) && admin.getPassword().equals(password)){ 
				temp = admin;
			}
			if (temp==null){
				System.out.println("username and password didnt match.");
				System.out.println("Press 1 to try again");
				System.out.println("Press 0 to go back to previous menu");
				input= Integer.parseInt(br.readLine());
			}
			else{
				System.out.println("Logging in..");
				break;
			}
		}while(input!=0);
		return temp;
	}
	public void showProfitStatement(Admin admin) {
	}
	public void grantInterest(Admin admin) {
		if (admin!=this.admin) return;
		Double totalInterestGranted=0.0;
		for(Customer cus : customers){
			for(Account acc : cus.getAccounts()){
				Double interest;
				interest=(acc.getBalance()*acc.getRateOfInterest())/100.0;
				if(acc.grantProfit(interest)){
					totalInterestGranted+=interest;
				}
			}
		}
		cashInHand-=totalInterestGranted;
		if(cashInHand<0) System.out.println("Interest granted. Cash in hand is less than zero.");
		else System.out.println("Interest Granted");
	}
	public void showFrozenAccounts(Admin admin) {
		if (admin!=this.admin) return;
		for(Customer cus : customers){
			for(Account acc : cus.getAccounts()){
				if(acc.getStatus()=="frozen") System.out.println("Account No: " + acc.getAccountNumber());
			}
		}
	}
	public void changeInterestRate(Admin admin2) {
	}
}
	
public class Test {
	public static void main (String[] args) throws IOException{
		System.out.println("===============================");
		System.out.println("Welcome to the Bank of Westeros");
		System.out.println("===============================");
		Bank icici = Bank.getBank();
		int input1=0;
		int input2=0;
		int input3=0;
		do{
			showStartMenu();
			input1= takeIntegerInput();
			if(input1==1) {
				do{
					showUserMenu();
					input2 = takeIntegerInput();
					if(input2==1){
						Customer cust = icici.customerLogin();
						if(cust==null){}
						else{
							do{
								showCustomerMenu();
								input3=takeIntegerInput();
								if(input3==1){ // open account
									cust.openNewAccount();
								}
								else if(input3==2){ // deposit money
									Account temp = cust.getAccount();
									if(temp==null){}
									else{
										temp.deposit();
									}
								}
								else if(input3==3){
									Account temp = cust.getAccount();
									if(temp==null){}
									else{
										temp.withdraw();
									}									
								}
								else if(input3==4){
									Account temp = cust.getAccount();
									if(temp==null){}
									else{
										temp.showAccountDetails();
									}	
								}
								else if(input3==5){
									cust.issueNewLoan();
								}
								else if(input3==6){}
									cus.payLoanDueAmount();
								}
								else if(input3==7){
									
								}
								else if(input3!=0){
									System.out.println("Please select one of the above option");
								}
							}while(input3!=0);
						}
					}
					else if(input2==2){
						icici.addCustomers();
					}
					else if(input2!=0){
					System.out.println("Please select one of the above option");
					}
				}while(input2!=0);
			}
			else if(input1==2){
				Admin admin = icici.adminLogin();
				if(admin==null){}
				else{
					do{
						showAdminMenu();
						input2=takeIntegerInput();
						if(input2==1){
							icici.showBankDetails(admin);
						}
						else if(input2==2){
							icici.showProfitStatement(admin);
						}
						else if(input2==3){
							icici.grantInterest(admin);
						}
						else if(input2==4){
							icici.showFrozenAccounts(admin);
						}
						else if(input2==5){
							icici.changeInterestRate(admin);
						}
						else if(input2==6){
							icici.changeInterestRate(admin);
						}
						else if(input2!=0){
							System.out.println("Please select one of the above option");
						}
					}while(input2!=0);
				}
			}
			else if(input1!=0){
				System.out.println("Please select one of the above option");
			}
		}while(input1!=0);
	}


	public static int takeIntegerInput() throws IOException{
		BufferedReader br = new BufferedReader ( new InputStreamReader(System.in));
		int temp;
		do{
			try{
				temp = Integer.parseInt(br.readLine());
			}
			catch(NumberFormatException e){
				System.out.println("Please enter integer value.");
				continue;
			}
			break;
		}while(true);
		return temp;
	}

	public static void showStartMenu(){
		System.out.println();
		System.out.println("Press 1 if you are a user");
		System.out.println("Press 2 if you are a admin");
		System.out.println("Press 0 to exit");
	}

	public static void showAdminMenu(){
		System.out.println();
		System.out.println("Press 1 to check total money in bank");
		System.out.println("Press 2 to check total amount of loans leased");
		System.out.println("Press 3 to show all frozen Accounts");
		System.out.println("Press 4 to grant interest to all Accounts");
		System.out.println("Press 5 to change interest rate");
		System.out.println("Press 6 to change loan rate");
		System.out.println("Press 7 for other activities");
		System.out.println("Press 0 to go to previous menu.");
	}

	public static void showUserMenu(){
		System.out.println();
		System.out.println("Press 1 if you are already a customer of our bank");
		System.out.println("Press 2 to register youself as a customer");
		System.out.println("Press 0 to go back to previous menu");
	}
	
	public static void showCustomerMenu(){
		System.out.println();
		System.out.println("Press 1 to open a new account");
		System.out.println("Press 2 to deposit money");
		System.out.println("Press 3 to withdraw money");
		System.out.println("Press 4 to check account balance");
		System.out.println("Press 5 to apply for a loan");
		System.out.println("Press 6 to pay off old loan");
		System.out.println("Press 7 for other activities");
		System.out.println("Press 0 to go to previous menu");	
	}
}