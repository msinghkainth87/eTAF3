package seleniumutils.methods.SelfHealing;



public class HistoricalProperty {
	private String htmlId;
	private String name;
	private String absLocation;
	private String size;
	private String color;
	private String href;
	private String altText;
	private String className;
	private String innerText;	
	private String outerText;
	private String tagName;
	public String getHtmlId() {
		return htmlId;
	}
	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbsLocation() {
		return absLocation;
	}
	public void setAbsLocation(String absLocation) {
		this.absLocation = absLocation;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getInnerText() {
		return innerText;
	}
	public void setInnerText(String innerText) {
		this.innerText = innerText;
	}
	public String getOuterText() {
		return outerText;
	}
	public void setOuterText(String outerText) {
		this.outerText = outerText;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "{\"absLocation\":"+"\""+this.getAbsLocation()+"\",\"size\":"+"\""+this.getSize()+"\","
				+ "\"color\":"+"\""+this.getColor()+"\","
				+"\"name\":"+"\""+this.getName()+"\",\"href\":"+"\""+this.getHref()+"\","
				+ "\"altText\":"+"\""+this.getAltText()+"\","
				+ "\"className\":"+"\""+this.getClassName()+"\","
				+ "\"innerText\":"+"\""+this.getInnerText()+"\"," + "\"outerText\":"+"\""+this.getOuterText()+"\","
				+ "\"htmlId\":"+"\""+this.getHtmlId()+"\","
				+ "\"tagName\":"+"\""+this.getTagName()+"\"}";
		
	}

	
}