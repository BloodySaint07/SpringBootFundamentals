package com.example.SpringMongoDB.Driver;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
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
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	@Autowired
	private ICustomExceptionService customExceptionService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	/** Batch Test*/

	try {
		JobParameters jobParameters = new JobParametersBuilder().addLong("Batch Fire Time", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(job, jobParameters);
	}catch (Exception batchException){
		CustomException customException =new CustomException(IErrorConstants.BATCFAILEDTORUN+" "+batchException.getMessage().toString());
		customExceptionService.saveException(customException);
	}

	}
}
