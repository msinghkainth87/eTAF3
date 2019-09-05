package seleniumutils.methods.SelfHealing.inputdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputPOJO {
	@JsonProperty
	private String name;
	
	@JsonProperty
	@JsonIgnore
	private String id;
	
	

	@JsonProperty
	@JsonIgnore
	private String htmlDom;
	
	@JsonProperty
	private String absLocation;
	
	@JsonProperty
	private String size;
	
	@JsonProperty
	private String color;
	
	@JsonProperty
	private String href;
	
	@JsonProperty
	private String altText;
	
	@JsonProperty
	private String className;
	
	@JsonProperty
	private String innerText;
	
	@JsonProperty
	private String outerText;
	
	@JsonProperty
	private String htmlId;
	
	@JsonProperty
	private String tagName;	
	

	public String getHtmlDom() {
		return htmlDom;
	}

	public void setHtmlDom(String htmlDom) {
		this.htmlDom = htmlDom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getHtmlId() {
		return htmlId;
	}

	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
