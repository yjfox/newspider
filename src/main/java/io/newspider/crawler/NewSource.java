package io.newspider.crawler;

public class NewSource {
	protected String title;
	protected String url;
	protected String source;

	public NewSource() {
		title = null;
		url = null;
		source = null;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return this.source;
	}
	
}
