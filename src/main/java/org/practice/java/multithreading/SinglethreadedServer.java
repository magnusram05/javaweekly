package org.practice.java.multithreading;
import java.util.*;
public class SinglethreadedServer {
	public static void main(String [] args){

		HelloWorld hello = new HelloWorld();
		hello.sayHello("World");

		List<String> users = Collections.unmodifiableList(Arrays.asList(args));
		sayHelloToMultipleUsers(users);

	}

	private static void sayHelloToMultipleUsers(List<String> users){

		HelloWorld helloWorld = new HelloWorld();
		
		/* Sequential execution */
        users.forEach( (user) -> {
        	helloWorld.sayHello(user);      	
        });

	}
}