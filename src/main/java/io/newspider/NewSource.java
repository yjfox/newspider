package io.newspider;

public class NewSource {
	protected long id;
	protected String title;
	protected String url;
	protected String source;

	public NewSource() {
		id = 0;
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
