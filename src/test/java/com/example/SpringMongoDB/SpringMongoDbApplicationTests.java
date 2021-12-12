package com.example.SpringMongoDB;

import com.example.SpringMongoDB.Driver.SpringMySQLApplication;
import com.example.SpringMongoDB.Driver.pkg.ReactiveProgramming.ReactiveApplicationTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringMySQLApplication.class)
 class SpringMongoDbApplicationTests {

	/**
	 * Logger
	 */
	Logger LOGGER = LogManager.getLogger(ReactiveApplicationTest.class);


	/*
	@Test
	void runBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder().addLong("Batch Fire Time", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(job, jobParameters);

	}
	*/


	@Test
	@SuppressWarnings("SpellCheckingInspection")
	void testFlux() throws InterruptedException {

		LOGGER.info(" inside testFlux() in " + this.getClass().getName());

		//Flux flux = Flux.just("iPhone 12 Mini", "AlienWare G11", "Cricket Cap", "Lays Potato Chips", "Dettol Soap", "Kinghisher Beer", "Basmati Rice");
		Flux flux = Flux.fromIterable(Arrays.asList("iPhone 12 Mini", "AlienWare G11", "Cricket Cap", "Lays Potato Chips", "Dettol Soap", "Kinghisher Beer"));
		flux
				//.delayElements(Duration.ofSeconds(2))
				.log().map(data -> data.toString()
						.toUpperCase())
				.subscribe(new Subscriber<String>() {

			private long count = 0;
			private Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {

				LOGGER.info(" inside onSubscribe() " + subscription);
				this.subscription=subscription;
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(String o) {

				LOGGER.info(" inside onNext() " + o);
				count++;
				if (count >= 3) {
					count = 0;
					subscription.request(3);
				}
				LOGGER.info(" Final Val " + o);
				LOGGER.info(" Running count Val " + count);
			}

			@Override
			public void onError(Throwable throwable) {

				LOGGER.info(" inside onError() " + throwable.getMessage());
			}

			@Override
			public void onComplete() {
				LOGGER.info(" inside onComplete() ");
				LOGGER.info(" *** Process Completed *** ");

			}

		});
		Thread.sleep(2000);
	}

}
