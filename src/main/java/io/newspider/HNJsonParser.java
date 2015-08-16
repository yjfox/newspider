package io.newspider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;

public class HNJsonParser extends Parser {
	private Gson gson;
	private StringBuilder allContent;
	private long[] ids;

	public HNJsonParser(String url) {
		super(url);
		gson = new Gson();
		allContent = null;
		ids = null;
		extractAllLines();
		buildObjects();
	}
	
	private void extractAllLines() {
		parseUrl(url);
		ids = gson.fromJson(allContent.toString(), long[].class);
	}

	private void parseUrl(String url) {
		try {
			URL urlObj = new URL(url);
			allContent = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlObj.openStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				allContent.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// log!
		}
	}
	
	@Override
	protected void buildObjects() {
		String baseUrl = "https://hacker-news.firebaseio.com/v0/item/";
		for (long id : ids) {
			parseUrl(baseUrl + id + ".json");
			NewSource newsObj = gson.fromJson(allContent.toString(),
					NewSource.class);
			newsObjs.add(newsObj);
		}
	}

}
