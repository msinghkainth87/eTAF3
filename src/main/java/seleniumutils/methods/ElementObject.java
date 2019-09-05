package seleniumutils.methods;


import com.poiji.annotation.*;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.List;


public class ElementObject extends GetElementUsingBy {
    @ExcelRow
    private int rowIndex;

    @ExcelCellName("element_type")
    private String elementType;

    @ExcelCellName("variable")
    private String variable;

    @ExcelCellName("value")
    private String value;

    @ExcelCellName("id")
    private String id;

    @ExcelCellName("class")
    private String className;

    @ExcelCellName("name")
    private String name;

    @ExcelCellName("xpath")
    private String xpath;

    @ExcelCellName("css")
    private String css;

    @ExcelCellName("linkText")
    private String linkText;

    @ExcelCellName("partialLinkText")
    private String partialLinkText;

    @ExcelCellName("tagName")
    private String tagName;

    @ExcelCellName("abs location")
    private String absLocation;

    @ExcelCellName("alt")
    private String alt;

    @ExcelCellName("innerText")
    private String innerText;

    @ExcelCellName("outerHTML")
    private String outerHTML;

    @ExcelCellName("color")
    private String color;

    @ExcelCellName("href")
    private String href;

    @ExcelCellName("size")
    private String size;

    private String accessType;
    private String accessName;

    private WebElement element;

    public ElementObject(ElementObject copyElement) {
        this.elementType = copyElement.elementType;
        this.variable = copyElement.variable;
        this.value = copyElement.value;
        this.id = copyElement.id;
        this.className = copyElement.className;
        this.name = copyElement.name;
        this.xpath = copyElement.xpath;
        this.css = copyElement.css;
        this.linkText = copyElement.linkText;
        this.partialLinkText = copyElement.partialLinkText;
        this.tagName = copyElement.tagName;
		this.absLocation = copyElement.absLocation;
		this.alt = copyElement.alt;
		this.innerText = copyElement.innerText;
		this.outerHTML = copyElement.outerHTML;
		this.color = copyElement.color;
		this.href = copyElement.href;
		this.size = copyElement.size;
		this.accessType = copyElement.accessType;
        this.accessName = copyElement.accessName;
        this.element = copyElement.element;
    }


    public ElementObject() {
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getElementType() {
        return elementType;
    }

    public String getVariable() {
        return variable;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String getXpath() {
        return xpath;
    }

    public String getCss() {
        return css;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getPartialLinkText() {
        return partialLinkText;
    }

    public String getTagName() {
        return tagName;
    }

    public String getAbsLocation() {
        return absLocation;
    }

    public void setAbsLocation(String absLocation) {
        this.absLocation = absLocation;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public String getOuterHTML() {
        return outerHTML;
    }

    public void setOuterHTML(String outerHTML) {
        this.outerHTML = outerHTML;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

       /*public void decideTypeAndAccessName(String accessType) {
        this.accessType = accessType;
    }*/

    public void decideTypeAndAccessName() {
        //Assigning Type according to the rank of available object properties with id being of highest rank
        if (this.id != null){
            this.accessType = "id";
            this.accessName = this.id;
        } else if (this.name != null){
            this.accessType = "name";
            this.accessName = this.name;
        } else if(this.css!=null){
            this.accessType ="css";
            this.accessName = this.css;
        } else if(this.xpath!=null){
            this.accessType ="xpath";
            this.accessName = this.xpath;
        } else if(this.linkText !=null){
            this.accessType ="linkText";
            this.accessName = this.linkText;
        } else if(this.partialLinkText !=null){
            this.accessType ="partialLinkText";
            this.accessName = this.partialLinkText;
        } else if(this.className !=null){
            this.accessType ="className";
            this.accessName = this.className;
        } else if(this.tagName !=null){
            this.accessType ="tagName";
            this.accessName = this.tagName;
        } else if(this.value!=null){
            this.accessType ="value";
            this.accessName=this.value;
        }
    }

    public void locateElement(String accessType,String accessName){
        try {
            element=driver.findElement(getElementByAttributes(accessType, accessName));
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessName)));
            element=driver.findElement(getElementByAttributes(accessType, accessName));
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub

        return "{\"absLocation\":"+"\""+this.getAbsLocation()+"\",\"size\":"+"\""+this.getSize()+"\","
                + "\"color\":"+"\""+this.getColor()+"\","
                +"\"name\":"+"\""+this.getName()+"\",\"href\":"+"\""+this.getHref()+"\","
                + "\"altText\":"+"\""+this.getAlt()+"\","
                + "\"className\":"+"\""+this.getClassName()+"\","
                + "\"innerText\":"+"\""+this.getInnerText()+"\"," + "\"outerText\":"+"\""+this.getOuterHTML().replaceAll("\"", "\\\\\"")+"\","
                + "\"htmlId\":"+"\""+this.getId()+"\","
                + "\"tagName\":"+"\""+this.getTagName()+"\"}";

    }



}
