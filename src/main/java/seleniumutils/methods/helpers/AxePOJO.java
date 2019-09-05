package seleniumutils.methods.helpers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AxePOJO {

    @JsonProperty("passes")
    private ArrayList<ResultType> passes;

    @JsonProperty("incomplete")
    private ArrayList<ResultType> warning;

    @JsonProperty("inapplicable")
    private ArrayList<ResultType> notice ;

    @JsonProperty("violations")
    private ArrayList<ResultType> error;

    @JsonProperty("PageName")
    private String pageName;

    @JsonProperty("timestamp")
    private String timeStamp;

    @JsonProperty("url")
    @JsonIgnore
    private String url;

    public ArrayList<ResultType> getPasses() {
        return passes;
    }

    public void setPasses(ArrayList<ResultType> passes) {
        this.passes = passes;
    }

    public ArrayList<ResultType> getWarning() {
        return warning;
    }

    public void setWarning(ArrayList<ResultType> warning) {
        this.warning = warning;
    }

    public ArrayList<ResultType> getNotice() {
        return notice;
    }

    public void setNotice(ArrayList<ResultType> notice) {
        this.notice = notice;
    }

    public ArrayList<ResultType> getError() {
        return error;
    }

    public void setError(ArrayList<ResultType> error) {
        this.error = error;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<LinkedHashMap<String,String>> flatten(){
        ArrayList<LinkedHashMap<String,String>> axeFlatten = new ArrayList<>();
        for(ResultType pass:passes){
            ArrayList<LinkedHashMap<String,String>> flattenedPasses= pass.flattenResultType();
            //Todo Add pagename and timestamp inside the hashmap
            axeFlatten.addAll(flattenedPasses);
        }
        for(ResultType warn:warning){
            ArrayList<LinkedHashMap<String,String>> flattenedWarning= warn.flattenResultType();
            //Todo Add pagename and timestamp inside the hashmap
            axeFlatten.addAll(flattenedWarning);
        }
        for(ResultType err:error){
            ArrayList<LinkedHashMap<String,String>> flattenedError= err.flattenResultType();
            //Todo Add pagename and timestamp inside the hashmap
            axeFlatten.addAll(flattenedError);
        }
        for(ResultType note:notice){
            ArrayList<LinkedHashMap<String,String>> flattenedNotice= note.flattenResultType();
            //Todo Add pagename and timestamp inside the hashmap
            axeFlatten.addAll(flattenedNotice);
        }
        return axeFlatten;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResultType {
    @JsonProperty("description")
    private String description;

    @JsonProperty("help")
    private String help;

    @JsonProperty("helpUrl")
    private String helpUrl;

    @JsonProperty("id")
    private String id;

    @JsonProperty("impact")
    private String impact;

    @JsonProperty("tags")
    private ArrayList<String> tags;

    @JsonProperty("nodes")
    private ArrayList<Node> nodes;

    public ArrayList<LinkedHashMap<String,String>> flattenResultType(){
        ArrayList<LinkedHashMap<String,String>> resultType = new ArrayList<>();
        for(Node node:nodes){
            ArrayList<LinkedHashMap<String,String>> flattenedNode= node.flattenNode();
            resultType.addAll(flattenedNode);
        }
        return resultType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

}

@JsonInclude(JsonInclude.Include.NON_NULL)
class Node{
    @JsonProperty("html")
    private String html;

    @JsonProperty("impact")
    @JsonIgnore
    private String impact;

    @JsonProperty("target")
    @JsonIgnore
    private ArrayList target;

    @JsonProperty("any")
    private ArrayList<PassCriteria> any;

    @JsonProperty("all")
    private ArrayList<PassCriteria> all;

    @JsonProperty("none")
    private ArrayList<PassCriteria> none;

    @JsonProperty("failureSummary")
    @JsonIgnore
    private String failureSummary;

    public ArrayList<LinkedHashMap<String,String>> flattenNode(){
        ArrayList<LinkedHashMap<String,String>> node = new ArrayList<>();
        for(PassCriteria criteria: any){
            LinkedHashMap<String,String> anyallnone= new LinkedHashMap<>();
            anyallnone.put("Test",criteria.getId());
            anyallnone.put("message",criteria.getMessage());
            anyallnone.put("HTML",this.getHtml());
            anyallnone.put("passCriteria","Any");
            node.add(anyallnone);
        }
        for(PassCriteria criteria: all){
            LinkedHashMap<String,String> anyallnone= new LinkedHashMap<>();
            anyallnone.put("Test",criteria.getId());
            anyallnone.put("message",criteria.getMessage());
            anyallnone.put("HTML",this.getHtml());
            anyallnone.put("passCriteria","All");
            node.add(anyallnone);
        }
        for(PassCriteria criteria: none){
            LinkedHashMap<String,String> anyallnone= new LinkedHashMap<>();
            anyallnone.put("Test",criteria.getId());
            anyallnone.put("message",criteria.getMessage());
            anyallnone.put("HTML",this.getHtml());
            anyallnone.put("passCriteria","None");
            node.add(anyallnone);
        }
        return node;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public ArrayList getTarget() {
        return target;
    }

    public void setTarget(ArrayList target) {
        this.target = target;
    }

    public ArrayList<PassCriteria> getAny() {
        return any;
    }

    public void setAny(ArrayList<PassCriteria> any) {
        this.any = any;
    }

    public ArrayList<PassCriteria> getAll() {
        return all;
    }

    public void setAll(ArrayList<PassCriteria> all) {
        this.all = all;
    }

    public ArrayList<PassCriteria> getNone() {
        return none;
    }

    public void setNone(ArrayList<PassCriteria> none) {
        this.none = none;
    }

    public String getFailureSummary() {
        return failureSummary;
    }

    public void setFailureSummary(String failureSummary) {
        this.failureSummary = failureSummary;
    }

}



@JsonInclude(JsonInclude.Include.NON_NULL)
class PassCriteria{
    @JsonProperty("id")
    private String id;

    @JsonProperty("impact")
    @JsonIgnore
    private String impact;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonIgnore
    private JSONObject data;

    @JsonProperty("relatedNodes")
    @JsonIgnore
    private JSONObject relatedNodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getRelatedNodes() {
        return relatedNodes;
    }

    public void setRelatedNodes(JSONObject relatedNodes) {
        this.relatedNodes = relatedNodes;
    }

}