
package org.practice.java.multithreading;
import java.util.function.*;
public class RaceConditionDemo {
	public static void main(String [] args){
        while(true){
        	handleUserSession();
        }
    }

	static void handleUserSession(){
	    Account account = new Account(100.0);
	    UserSession session1 = new UserSession("withdraw", 100.0);
	    UserSession session2 = new UserSession("deposit", 100.0);
	    BiFunction<Account, UserSession, Boolean> accountOpertion
	        = new PersonalAccountOperation();
        Thread thread1 = new Thread1(accountOpertion, account, session1);
        Thread thread2 = new Thread2(accountOpertion, account, session2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex){
            System.out.println(ex);
        }
        if(account.getCurrentBalance() != 100){
            System.out.println("Race condition has occured: "+ account.getCurrentBalance());
            System.exit(1);
        } else {
            System.out.println("Current balance after withdrawal and deposit is" 
        	+ account.getCurrentBalance());
        }
	}

	static class Thread1 extends Thread {
	    private final BiFunction<Account, UserSession, Boolean> accountOperation;
	    private final Account account;
	    private final UserSession session;
	    Thread1(BiFunction<Account, UserSession, Boolean> accountOperation,
		            Account account,
		            UserSession session){
            this.accountOperation = accountOperation;
            this.account = account;
            this.session = session;
	    }
	    @Override
	    public void run(){
            this.accountOperation.apply(account, session);
	    }
	}

	static class Thread2 extends Thread {
	    private final BiFunction<Account, UserSession, Boolean> accountOperation;
	    private final Account account;
	    private final UserSession session;
	    Thread2(BiFunction<Account, UserSession, Boolean> accountOperation,
		            Account account,
		            UserSession session){
            this.accountOperation = accountOperation;
            this.account = account;
            this.session = session;
	    }
	    @Override
	    public void run(){
            this.accountOperation.apply(account, session);
	    }
	}

	static class PersonalAccountOperation implements BiFunction<Account, UserSession, Boolean>{
	    @Override
	    public Boolean apply(Account account, UserSession session){
	        String action = session.getAction();
	        double amount = session.getAmount();
            if("withdraw".equalsIgnoreCase(action)){
                account.decrementCurrentBalance(amount);
            } else if ("deposit".equalsIgnoreCase(action)) {
                account.incrementCurrentBalance(amount);
            } 
            return true;                        
	    }	    
	}

	static class Account {
	    private double currentBalance;

	    Account(double currentBalance){
	        this.currentBalance = currentBalance;
	    }

	    public void decrementCurrentBalance(double amount){
	    	if(currentBalance >= amount) {
	    		this.currentBalance = this.currentBalance - amount;
	    		System.out.println("Decrementing from thread: " + Thread.currentThread().getName()
	    			+ ": " + this.currentBalance);
	    	}
	    }
	    public void incrementCurrentBalance(double amount){
	    	this.currentBalance = this.currentBalance + amount;
	        System.out.println("Incrementing from thread: " + Thread.currentThread().getName()
	    		+ ": " + this.currentBalance);
	    }	    

	    public double getCurrentBalance(){
	        return this.currentBalance;
	    }
	}

	static class UserSession {	    
	    private final String action;
	    private final double amount;
	    UserSession(String action, double amount){
	        this.action = action;
	        this.amount = amount;
	    }

	    public String getAction(){
	        return this.action;
	    }

	    public double getAmount(){
	        return this.amount;
	    }

	}
}