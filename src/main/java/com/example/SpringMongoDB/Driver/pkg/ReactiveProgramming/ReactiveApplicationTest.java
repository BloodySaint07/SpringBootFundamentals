package com.example.SpringMongoDB.Driver.pkg.ReactiveProgramming;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactiveApplicationTest {
    /**
     * Logger
     */
    static Logger LOGGER = LogManager.getLogger(ReactiveApplicationTest.class);

    @SuppressWarnings("SpellCheckingInspection")
    void testFlux() throws InterruptedException {

        LOGGER.info(" inside testFlux() in " + this.getClass().getName());

        Flux flux = Flux.just("iPhone 12 Mini", "AlienWare G11", "Cricket Cap","Lays Potato Chips","Dettol Soap","Kinghisher Beer","Basmati Rice");
        flux.delayElements(Duration.ofSeconds(2)).log().map(data -> data.toString().toUpperCase()).subscribe(new Subscriber() {

            private long count = 0;
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {

                LOGGER.info(" inside onSubscribe() " + subscription);
                subscription.request(3);
            }

            @Override
            public void onNext(Object o) {

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
