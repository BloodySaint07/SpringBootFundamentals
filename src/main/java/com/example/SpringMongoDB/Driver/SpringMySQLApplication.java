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
public class SpringMySQLApplication implements CommandLineRunner {

	/** LOGGER */
	Logger LOGGER = LogManager.getLogger(SpringMySQLApplication.class);
	@Autowired
	IUserRepository userRepo;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	@Autowired
	private ICustomExceptionService customExceptionService;
	private int count;

	public static void main(String[] args) {
		SpringApplication.run(SpringMySQLApplication.class, args);
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

//		try {
//			List<String> userNameList = userRepo.findAllUserNames();
//			;
//			String[] usernameArr = userNameList.toArray(new String[userNameList.size()]);
//
//			LOGGER.info(" ********* Get Class 1 ********** " + userNameList.getClass().toString());
//			LOGGER.info(" ********* Get Class 2 ********** " + usernameArr.getClass().toString());
//
////			if (count < usernameArr.length) {
////
////				LOGGER.info(" *** Data Counter *** " + count);
////				LOGGER.info(" ********* Get Value 3 ********** " + usernameArr[count++]);
////
////			} else {
////				LOGGER.info("*** Get Value 4  ***" + count);
////				count = 0;
////			}
//
////			for(count=0;count< usernameArr.length;count++){
////				LOGGER.info(" *** Get Value 5  *** " + usernameArr[count]);
////			}
//
//		} catch (Exception batchException) {
//
//			CustomException customException = new CustomException(IErrorConstants.BATCFAILEDTORUN + " " + batchException.getMessage().toString());
//			customExceptionService.saveException(customException);
//		}

	}
}
