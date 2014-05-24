package ntut.restful.controller;

import java.util.List;

import ntut.restful.object.StoryObject;
import ntut.restful.server.DBConnection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

public class ProductBacklogController {
	private DBConnection DBserver;
	
	public ProductBacklogController() {
		DBserver = new DBConnection();
	}
	
	public String createTable() {
		return DBserver.createTable();
	}
	
	public String addStory(String storyJson) throws JSONException {
		StoryObject newStory = new Gson().fromJson(storyJson, StoryObject.class);
    	JSONObject createStoryResponse = new JSONObject();
    	int storyID = DBserver.createStory(newStory);
    	if(storyID == -1) {
    		createStoryResponse.put("status", "FAILED");
    		createStoryResponse.put("storyID", "NULL");
    	} else {
    		createStoryResponse.put("status", "SUCCESS");
    		createStoryResponse.put("storyID", String.valueOf(storyID));
    	}
    	
		return createStoryResponse.toString();
	}
	
	public String getStorys() throws JSONException {
		JSONArray readStoryResponse = new JSONArray();
		List<StoryObject> storylist = DBserver.getAllStories();
		for(StoryObject story : storylist) {
			JSONObject object = new JSONObject(new Gson().toJson(story));
			readStoryResponse.put(object);
		}
		return readStoryResponse.toString();
	}
	
	public String editStory(String storyJson) throws JSONException {
		StoryObject editStory = new Gson().fromJson(storyJson, StoryObject.class);
    	String storyID = editStory.getID();
    	DBserver.updateStory(editStory);
    	StoryObject newStory = DBserver.getStory(storyID);
    	JSONObject editStoryResponse = new JSONObject(newStory.toString());
    	return editStoryResponse.toString();
	}
	
	public String deleteStory(String storyJson) throws JSONException {
		JSONArray deleteStoryResponse = new JSONArray();
		JSONObject deleteID = new JSONObject(storyJson);
		DBserver.deleteStory(deleteID.getString("id"));
		List<StoryObject> storylist = DBserver.getAllStories();
		for(StoryObject story : storylist) {
			JSONObject object = new JSONObject(new Gson().toJson(story));
			deleteStoryResponse.put(object);
		}
		return deleteStoryResponse.toString();
	}
}
