package io.newspider.crawler;

import io.newspider.database.RedisDataUtil;
import io.newspider.database.SingletonRedisConnector;

import java.util.List;
import java.util.TimerTask;

/*
 * add more method if you plan to add more New source
 */
public class ScheduledCrawlerRunner extends TimerTask {

	// run tasks as scheduled
	String redisNode;

	public ScheduledCrawlerRunner(String redisNode) {
		this.redisNode = redisNode;
	}

	@Override
	public void run() {
		System.out.println("===================schedule running===============");
		List<NewSource> news = jsonParse("Hacker-News",
				"https://hacker-news.firebaseio.com/v0/topstories.json",
				"https://hacker-news.firebaseio.com/v0/item/%s.json");
		news.addAll(htmlParse("highscalability.com",
				"http://highscalability.com/", new String[] { "h2", ".title" }));
		news.addAll(htmlParse("toutiao.io", "http://toutiao.io/", new String[] {
				"h3", ".title" }));
		RedisDataUtil.saveToRedis(redisNode, news);
		SingletonRedisConnector.destroy();
	}

	/*
	 * @argument pageUrl: page rl, fist page usually
	 * 
	 * @argument patterns: patterns usually consist HTML Tag, used to target the
	 * title line
	 */
	private List<NewSource> htmlParse(String source, String pageUrl,
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
	private List<NewSource> jsonParse(String source, String listUrl,
			String contentUrl) {
		// first argument is
		HNJsonParser hnparser = new HNJsonParser(source, listUrl, contentUrl);
		return hnparser.getObjects();
	}

}
