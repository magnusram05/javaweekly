package org.practice.java.multithreading;

public class VisibilityTest {

	public static void main(String [] args){	
    CustomLock lock = new CustomLock();
	  Thread thread1 = new Thread1(lock);	
	  Thread thread2 = new Thread2(lock);	
		thread1.start();
		thread2.start();
	}
  
/*When the shared state of an object is modified within a synchronized block, then the changes are guaranteed to be visible to other 
threads.  This is because, 'synchronized block' is one of the ways in which happens-before relationship is established as provisioned 
by the platform.  Consider the following lock object with a boolean that is set by one thread and checked by another thread 
before proceeding with the execution*/
  
    static class Thread1 extends Thread {
		private CustomLock lock;
        Thread1(CustomLock lock){
        	this.lock = lock;
        }
        public void run(){
            System.out.println("Setting to true from thread1");
            synchronized(lock){
            	lock.setToTrue();
            }
        }
	}
	static class Thread2 extends Thread {
		private CustomLock lock;
        Thread2(CustomLock lock){
        	this.lock = lock;
        }
        public void run(){
            while(!lock.getState()){
            	System.out.println("Waiting for it to be true from thread2");
            }
            System.out.println("The wait is over!");
        }		
    }
  
/*The boolean, doesn't have to be marked volatile since the boolean state is set within the synchronized block.  
Meaning, any subsequent access to the same state by other threads is synchronized (and this state change happens-before).*/
  
	static class CustomLock {
		boolean isSet = false;

		void setToTrue(){
			isSet = true;
		}

		boolean getState(){
			return isSet;
		}
	}
  
}
