package io.newspider.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser extends Parser {
	private final String source;
	private Elements allLines;

	public HtmlParser(String source, String url, String[] filters) {
		super(url);
		this.source = source;
		this.allLines = null;
		extractAllLines(filters);
		buildObjects();
	}

	private Document parseUrl() {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	private void extractAllLines(String[] filters) {
		Document doc = parseUrl();
		if (filters.length == 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		allLines = doc.select(filters[0]);
		for (int i = 1; i < filters.length; i++) {
			allLines = allLines.select(filters[i]);
		}
	}

	@Override
	protected void buildObjects() {
		for (Element line : allLines) {
			NewSource newObj = new NewSource();
			newObj.setTitle(line.text());
			String targetUrl = line.select("a").attr("href");
			//take care of relative path
			if (targetUrl.charAt(0) == '/') {
				if (url.charAt(url.length() - 1) == '/') {
					targetUrl = url + targetUrl.substring(1);
				} else {
					targetUrl = url + targetUrl;
				}
			}
			newObj.setUrl(targetUrl);
			newObj.setSource(source);
			newsObjs.add(newObj);
		}
	}

}
