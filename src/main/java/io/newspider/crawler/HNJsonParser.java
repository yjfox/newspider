package io.newspider.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;

public class HNJsonParser extends Parser {
	private final Gson gson;
	private final String baseUrl;
	private final String source;

	private StringBuilder allContent;
	private long[] ids;

	public HNJsonParser(String source, String url, String baseUrl) {
		super(url);
		this.gson = new Gson();
		this.baseUrl = baseUrl;
		this.source = source;
		
		this.allContent = null;
		this.ids = null;
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
		for (int i = 0; i < ids.length && i < 10; i++) {
			parseUrl(String.format(baseUrl, ids[i]));
			NewSource newsObj = gson.fromJson(allContent.toString(),
					NewSource.class);
			newsObj.setSource(source);
			newsObjs.add(newsObj);
		}
	}

}
