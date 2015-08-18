package io.newspider.crawler;

import io.newspider.database.RedisDataController;

import java.util.List;

/*
 * add more method if you plan to add more New source
 */
public class ScheduledCrawlerRunner {

	// run tasks as scheduled
	public static void run(String redisNode) {
		RedisDataController.saveToRedis(
				redisNode,
				htmlParse("highscalability.com", "http://highscalability.com/",
						new String[] { "h2", ".title" }));
		RedisDataController.saveToRedis(
				redisNode,
				htmlParse("toutiao.io", "http://toutiao.io/", new String[] {
						"h3", ".title" }));
		RedisDataController
				.saveToRedis(
						redisNode,
						jsonParse(
								"Hacker-News",
								"https://hacker-news.firebaseio.com/v0/topstories.json",
								"https://hacker-news.firebaseio.com/v0/item/%s.json"));
	}

	/*
	 * @argument pageUrl: page rl, fist page usually
	 * 
	 * @argument patterns: patterns usually consist HTML Tag, used to target the
	 * title line
	 */
	private static List<NewSource> htmlParse(String source, String pageUrl,
			String[] patterns) {
		HtmlParser hsparser = new HtmlParser(source, pageUrl, patterns);
		return hsparser.getObjects();
	}

	/*
	 * @argument source: website that the news from
	 * 
	 * @argument listUrl: Url get all articles
	 * 
	 * @argument contentUrl: page Url of a given article
	 */
	private static List<NewSource> jsonParse(String source, String listUrl,
			String contentUrl) {
		// first argument is
		HNJsonParser hnparser = new HNJsonParser(source, listUrl, contentUrl);
		return hnparser.getObjects();
	}

}
