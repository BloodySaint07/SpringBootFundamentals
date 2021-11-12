package com.example.SpringMongoDB.Driver;

import com.example.SpringMongoDB.Driver.pkg.MessagingService.MessageSender;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
@EnableJms
public class SpringMySQLApplication implements CommandLineRunner {

	/**
	 * LOGGER
	 */
	Logger LOGGER = LogManager.getLogger(SpringMySQLApplication.class);
	@Autowired
	IUserRepository userRepo;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	@Autowired
	private ICustomExceptionService customExceptionService;
	@Autowired
	MessageSender messageSender;
	private int count;

	public static void main(String[] args) {
		SpringApplication.run(SpringMySQLApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/** Batch Test*/

		/*try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("Batch Fire Time", System.currentTimeMillis()).toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception batchException) {
			CustomException customException = new CustomException(IErrorConstants.BATCFAILEDTORUN + " " + batchException.getMessage().toString());
			customExceptionService.saveException(customException);
		}*/

		/**  Messaging  Test */

//		try {
//			String message = "You are a Genius Soumya !!! ";
//			messageSender.send(message);
//		} catch (Exception batchException) {
//			CustomException customException = new CustomException(IErrorConstants.MESSAGINGERROR + " " + batchException.getMessage().toString());
//			customExceptionService.saveException(customException);
//		}
	}
}
