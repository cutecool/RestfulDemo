package ntut.restful.webservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ntut.restful.controller.ProductBacklogController;

import org.codehaus.jettison.json.JSONException;


@Path("/story")
public class ProductBacklogWebService {
	private ProductBacklogController backlog;
	
	@POST
	@Path("/createTable")
	public String createTable() {
		backlog = new ProductBacklogController();
		return backlog.createTable();
	}

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Jersey say : " + msg;
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String createStory(String storyJson) {
		String storyCreateJsonString = "";
		backlog = new ProductBacklogController();
		try {
	        storyCreateJsonString = backlog.addStory(storyJson);
        } catch (JSONException e) {
        	System.out.println("createStory Exception :" + e.toString());
        }
		return storyCreateJsonString;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String readStory() {
		String storyReadJsonString = "";
		backlog = new ProductBacklogController();
		try {
	        storyReadJsonString = backlog.getStorys();
        } catch (JSONException e) {
        	System.out.println("readStory Exception :" + e.toString());
        }
		return storyReadJsonString;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String updateStory(String storyJson) {
		String storyUpdateJsonString = "";
		backlog = new ProductBacklogController();
		try {
			storyUpdateJsonString = backlog.editStory(storyJson);
        } catch (JSONException e) {
        	System.out.println("updateStory Exception :" + e.toString());
        }
		return storyUpdateJsonString;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteStory(String storyJson) {
		String storyDeleteJsonString = "";
		backlog = new ProductBacklogController();
		System.out.println("*************************");
		System.out.println("storyJson = " + storyJson);
		System.out.println("*************************");
		try {
			storyDeleteJsonString = backlog.deleteStory(storyJson);
        } catch (JSONException e) {
        	System.out.println("deleteStory Exception :" + e.toString());
        	e.printStackTrace();
        }
		return storyDeleteJsonString;
	}
}
