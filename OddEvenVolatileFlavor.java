/*
Custom lock with boolean evenProceed=false
Odd thread prints, sets evenProceed to true, notifies and waits
Even thread waits in loop checking for the condition evenProceed to be true before proceeding,
prints, notifies and waits
*/
package org.practice.java.multithreading;

public class OddEvenVolatileFlavor {
	public static void main(String [] args){
		doWithVolatile();
	}

	private static void doWithVolatile(){
		CustomLock lock = new CustomLock();
        Thread oddThread = new OddThread(lock);
        Thread evenThread = new EvenThread(lock);
        oddThread.start();
        evenThread.start();
		try{
			evenThread.join();
		} catch (InterruptedException ex){
			System.out.println("Main thread interrupted while waiting for " +
				"Odd thread to finish execution"+ ex.getMessage());
		}
	}

	static class CustomLock {
		private boolean evenProceed = false;
		public void setEvenProceed(boolean isProceed){
			evenProceed = isProceed;
		}
		public boolean isEvenProceed(){
			return this.evenProceed;
		}
	}

	static class OddThread extends Thread {
	    private final CustomLock lock;
	    OddThread(CustomLock lock){
            this.lock = lock;
	    }

	    public void run(){
	        synchronized(lock){
	            for(int i=1;i<20;i=i+2){
	                System.out.println("Odd thread " + i);
	                lock.setEvenProceed(true);
	                try {	                
	                    lock.notify();
	                    lock.wait();
	                } catch(InterruptedException ex){
	                	System.out.println("Odd thread interrupted while waiting for " +
	                		"Even thread notification"+ ex.getMessage());
	                }
	            }
	        }	        
	    }
	}

	static class EvenThread extends Thread {
		private final CustomLock lock;    
		EvenThread(CustomLock lock){
		    this.lock = lock;	        
		}
		public void run(){
			synchronized(lock){	 
		    	while(!lock.isEvenProceed()){
		    		try {
		    			Thread.sleep(1000);
		    		} catch(InterruptedException ex){
		            	System.out.println("Even thread interrupted while sleeping for " +
		            		"Odd thread to set evenProceed"+ ex.getMessage());
		            }
		    	}	    	       	
		        for(int i=2;i<=20;i=i+2){
		            System.out.println("Even thread" + i);
		            try {	                
		                lock.notify();
				if(i != 20)
		                lock.wait();
		            } catch(InterruptedException ex){
		            	System.out.println("Even thread interrupted while waiting for " + 
		            		"Odd thread notification"+ ex.getMessage());
		            }
		        }
		    }
		}	    
	}
}

