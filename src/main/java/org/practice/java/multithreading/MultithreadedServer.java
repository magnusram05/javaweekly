package org.practice.java.multithreading;
import java.util.*;
public class MultithreadedServer {
	public static void main(String [] args){

		HelloWorld hello = new HelloWorld();
		hello.sayHello("World");

		List<String> users = Collections.unmodifiableList(Arrays.asList(args));
		sayHelloToMultipleUsers(users);

	}

	private static void sayHelloToMultipleUsers(List<String> users){

		HelloWorld helloWorld = new HelloWorld();
		
		/* Parallel execution */
        users.forEach( (user) -> {
        	Thread thread = new Thread( () -> helloWorld.sayHello(user), user );
        	thread.start();        	
        });

	}
}