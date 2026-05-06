package com.fooddelivery; // [L1] Root namespace for the application

import org.springframework.boot.SpringApplication; // [L1] Framework bootstrapper class
import org.springframework.boot.autoconfigure.SpringBootApplication; // [L1] Mega-annotation for auto-config

/**
 * [L3] 📁 FILE: FdsysApplication.java
 * 🏗 LAYER: Backend — Server Entry Point
 * 📋 ROLE: The starting point for the entire Java application.
 * 🔗 USED BY: Maven Build System / IDE Execution
 * ⚡ CONTEXT: Bootstraps the embedded Apache Tomcat Server and loads the Spring Application Context.
 */
@SpringBootApplication // [L2] [⚡ CONCEPT] Annotation Magic: This is a 3-in-1 annotation.
// 1. @Configuration: Tags the class as a source of bean definitions.
// 2. @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings.
// 3. @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'com.fooddelivery' package.
public class FdsysApplication {

    /**
     * [L2] The Main Method.
     * Standard Java entry point. When you click "Run", the JVM looks for this exact signature.
     */
	public static void main(String[] args) {
		// [L1] Delegates the execution to SpringApplication, which sets up the server environment
		SpringApplication.run(FdsysApplication.class, args);
	}
}
