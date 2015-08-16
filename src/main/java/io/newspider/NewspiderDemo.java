package io.newspider;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class NewspiderDemo {
	public static void main(String[] args) throws IOException {

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
				"https://hacker-news.firebaseio.com/v0/topstories.json");
		List<NewSource> allobj = hnparser.getObjects();
		// title of the first story on hacker news
		System.out.println(allobj.get(0).title + allobj.get(0).url);
	}

}
