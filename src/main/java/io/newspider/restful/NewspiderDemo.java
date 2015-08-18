package io.newspider.restful;

import io.newspider.crawler.ScheduledCrawlerRunner;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * where Spring Boot starts
 * 
 */

@SpringBootApplication
public class NewspiderDemo extends SpringBootServletInitializer {
	private static String redisNode = "fox1"; // by default, redis node hostname

	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length > 0 && args[0] != null && args[0].length() != 0) {
			redisNode = args[0];
		}
		SpringApplication.run(NewspiderDemo.class, args);
		scheduledRun();
	}

	@Scheduled(cron = "0/5 * * * * ?")
	public static void scheduledRun() {
		System.out.println("===================schedule running===============");
		ScheduledCrawlerRunner.run(redisNode);
	}

}
