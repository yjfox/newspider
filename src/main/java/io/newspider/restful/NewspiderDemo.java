package io.newspider.restful;

import io.newspider.crawler.ScheduledCrawlerRunner;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * where Spring Boot starts
 * 
 */

@SpringBootApplication
public class NewspiderDemo extends SpringBootServletInitializer {
	public static String redisNode = "fox1"; // by default, redis node hostname

	private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;
	private final static int ONE_AM = 5;
	private final static int ZERO_MINUTES = 21;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length > 0 && args[0] != null && args[0].length() != 0) {
			redisNode = args[0];
		}
		SpringApplication.run(NewspiderDemo.class, args);
		scheduledRun();
	}

	private static void scheduledRun() {
		ScheduledCrawlerRunner runner = new ScheduledCrawlerRunner(redisNode);
		Timer timer = new Timer();
		timer.schedule(runner, getNext1AM(), ONCE_PER_DAY);
	}

	private static Date getNext1AM() {
		Calendar date1am = Calendar.getInstance();
		date1am.set(Calendar.HOUR_OF_DAY, ONE_AM);
		date1am.set(Calendar.MINUTE, ZERO_MINUTES);
		return date1am.getTime();
	}

}
