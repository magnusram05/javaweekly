package org.practice.java.multithreading;

public class Singleton {
	private Singleton(){
	}

	private static Singleton INSTANCE = null;

	public static Singleton getInstance() {
/*		try{
			Thread.sleep(10000);
		} catch (InterruptedException ex){
			System.out.println("Interrupted"+ex.getMessage());
		}
		*/
	    if(INSTANCE == null) {
	    	System.out.println("Thread " + Thread.currentThread().getName() 
	    		+ " is about to enter the guarded block");
	        synchronized(Singleton.class){
	    	System.out.println("Thread " + Thread.currentThread().getName() 
	    		+ " has entered the guarded block");	        	
	            if(INSTANCE == null){
	            	System.out.println("Thread " + Thread.currentThread().getName() 
	    		+ " creating the object");
	                    INSTANCE = new Singleton();	                
	            }
	        }
	    }
	    return INSTANCE;
	}

	public static void main(String [] args) throws Exception {
		Runnable runnable = ()->System.out.println(Singleton.getInstance().hashCode());
		Thread thread1 = new Thread(runnable,"Thread-1");
		Thread thread2 = new Thread(runnable,"Thread-2");
		thread1.start();
		thread2.start();
//		Thread.sleep(15000);
	}
}
/*
public class Order {
	private final List<Item> items = new ArrayList<>();
	public List<Item> getItems(){
		return UnmodifiableList.of(items);
	}
	public void addItems(Item itemToAdd){
		if(Object.isNotNull(itemToAdd)){
			items.add(itemToAdd);
		}		
	}
}

public class Item {
	private final long product_id;
	private final String product_name;
	private final float unit_price;
	private final float quantity;

}*/
