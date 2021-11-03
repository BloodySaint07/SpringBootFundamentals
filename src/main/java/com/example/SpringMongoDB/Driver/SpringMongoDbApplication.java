package com.example.SpringMongoDB.Driver;

import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
public class SpringMongoDbApplication implements CommandLineRunner {

	/** LOGGER */
	Logger LOGGER = LogManager.getLogger(SpringMongoDbApplication.class);
	@Autowired
	IUserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/** Commenting Temporary Test
		 LOGGER.info("Inside Run() method of "+this.getClass().getName().toString());
		 Address address=new Address("33", "MC Garden", "Kolkata", "West Bengal","700031");
		 User user=new User("soumyajit07","Soumyajit","India", address);
		 LOGGER.info("**** User Details ***** "+user);
		 LOGGER.info("**** Address Details ***** "+address);
		 try {
		 userRepo.save(user);
		 }
		 catch(Exception ex2){
		 LOGGER.info("**** Exception Fired in Run() ***** "+ex2.getMessage());
		 }

		 */
	}
}
