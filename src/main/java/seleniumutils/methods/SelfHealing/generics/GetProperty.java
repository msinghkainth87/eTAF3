package seleniumutils.methods.SelfHealing.generics;

import org.jsoup.nodes.Element;
import seleniumutils.methods.SelfHealing.inputdto.InputPOJO;

public class GetProperty {
	InputPOJO inputPojo;
	Element element;
	public GetProperty(InputPOJO inputPojo){
		this.inputPojo = inputPojo;
	}
	
	public GetProperty(Element element) {
		this.element = element;
	}
	
	public String[] getPropValue(String fieldName) {
		switch(fieldName) {
		case "name":
			return new String[] {inputPojo.getName(),"name"};
		case "htmlId":
			return new String[] {inputPojo.getId(),"id"};
		case "outerText":
			return new String[] {inputPojo.getOuterText(),"outertext"};
		case "absLocation":
			return new String[] {inputPojo.getAbsLocation(),"location"};
		case "innerText":
			return new String[] {inputPojo.getInnerText(), "innertext"};
		case "size":
			return new String[] {inputPojo.getSize(),"size"};
		case "href":
			return new String[] {inputPojo.getHref(), "href"};
		case "altText":
			return new String[] {inputPojo.getAltText(), "href"};
		case "className":
			return new String[] {inputPojo.getClassName(),"class"};
		case "color":
			return new String[] {inputPojo.getColor(),"color"};
		case "tagName":
			return new String[] {inputPojo.getTagName(),"tagName"};
		
		default:
			return new String[] {"no property found",fieldName};					
		}
		
	}
	
	public String getElementValue(String fieldName) {
		switch(fieldName) {
		case "name":
			return element.attr("name");
		case "htmlId":
			return element.id();
		case "outerText":
			return element.outerHtml();
		case "absLocation":
			return "location";
		case "innerText":
			return element.ownText();
		case "size":
			return "size";
		case "href":
			return element.attr("href");
		case "altText":
			return element.text();
		case "className":
			return element.className();
		case "color":
			return element.attr("color");
		case "tagName":
			return element.tagName();
		
		default:
			return "no property found";					
		}
		
	}
}

enum Properties{
	ID, NAME;
}
