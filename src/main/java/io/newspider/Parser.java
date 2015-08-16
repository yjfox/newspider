package io.newspider;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
	protected String url;
	protected List<NewSource> newsObjs;

	public Parser(String url) {
		this.url = url;
		this.newsObjs = new ArrayList<NewSource>();
	}
	
	protected abstract void buildObjects();
	
	public List<NewSource> getObjects() {
		return newsObjs;
	}
}
