package ntut.restful.object;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class StoryObject {
	public String id = "";
	public String name = "";
	public String notes = "";
	public String howToDemo = "";
	public String importance = "";
	public String value = "";
	public String estimate = "";
	public String tag = "";
	
	public StoryObject() {
		
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setHowToDemo(String howToDemo) {
		this.howToDemo = howToDemo;
	}
	
	public String getHowToDemo() {
		return howToDemo;
	}
	
	public void setImportance(String importance) {
		this.importance = importance;
	}
	
	public String getImportance() {
		return importance;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}
	
	public String getEstimate() {
		return estimate;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public String toString() {
		JSONObject storyInfo = new JSONObject();
		try {
	        storyInfo.put("id", this.id);
			storyInfo.put("name", this.name);
			storyInfo.put("notes", this.notes);
			storyInfo.put("howToDemo", this.howToDemo);
			storyInfo.put("importance", this.importance);
			storyInfo.put("value", this.value);
			storyInfo.put("estimate", this.estimate);
			storyInfo.put("tag", this.tag);
        } catch (JSONException e) {
        	System.out.println("StoryObject JSON Exception :" + e.toString());
        }
		return storyInfo.toString();
	}
}
