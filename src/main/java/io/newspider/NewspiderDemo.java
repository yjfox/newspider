package io.newspider;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 * 
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class NewspiderDemo extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NewspiderDemo.class);
    }

	public static void main(String[] args) throws IOException {
		SpringApplication.run(NewspiderDemo.class, args);

		HtmlParser hsparser = new HtmlParser("http://highscalability.com/",
				new String[] { "h2", ".title" });
		List<NewSource> hsobjs = hsparser.getObjects();
		// title of the first article
		System.out.println(hsobjs.get(0).title + "\n" + hsobjs.get(0).url);

		HtmlParser htmlparser = new HtmlParser("http://toutiao.io/",
				new String[] { "h3", ".title" });
		List<NewSource> objs = htmlparser.getObjects();
		// title of the first article
		System.out.println(objs.get(0).title + "\n" + objs.get(0).url);

		HNJsonParser hnparser = new HNJsonParser(
				"https://hacker-news.firebaseio.com/v0/topstories.json",
				"https://hacker-news.firebaseio.com/v0/item/%s.json");
		List<NewSource> allobj = hnparser.getObjects();
		// title of the first story on hacker news
		System.out.println(allobj.get(0).title + allobj.get(0).url);
	}

}
