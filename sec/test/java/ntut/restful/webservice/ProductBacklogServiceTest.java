package ntut.restful.webservice;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static org.junit.Assert.assertEquals;
import ntut.restful.data.CreateData;
import ntut.restful.server.DBConnection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class ProductBacklogServiceTest {
	private static DBConnection DBserver = new DBConnection();

	@BeforeClass
	public static void setUp() {
		int storycount = 3;
		CreateData CD = new CreateData(storycount);
		CD.exe();
		System.out.println("setUp");
	}

	@AfterClass
	public static void tearDown() {
		DBserver.dropTable();
		System.out.println("tearDown");
	}

	@Rule
	public Destination destination = new Destination(this, "http://localhost:8080");

	@Context
	private Response response;

	/**
	 * POST
	 * testcreateStory
	 * {"status":"SUCCESS","storyID":"4"}
	 */
	@HttpTest(
	        method = Method.POST,
	        content = "{\"name\":\"TEST_STORY_NAME_4\","
	                + "\"notes\":\"TEST_STORY_NOTES_4\","
	                + "\"howToDemo\":\"TEST_STORY_HOWTODEMO_4\","
	                + "\"importance\":\"3\","
	                + "\"value\":\"5\","
	                + "\"estimate\":\"8\","
	                + "\"tag\":\"TEST_STORY_TAG_4\"}",
	        path = "/RestfulDemo/story",
	        order = 1
    )
    public void testcreateStory() {
		String id = String.valueOf(DBserver.getAllStories().size());
		try {
			JSONObject object = new JSONObject(response.getBody());
			assertEquals("SUCCESS", object.get("status"));
			assertEquals(id, object.get("storyID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("testcreateStory = " + response.getBody());
		assertOk(response);
	}
	
	/**
	 * GET
	 * testreadStory
	 * [{"id":"1","name":"TEST_STORY_NAME_1","notes":"TEST_STORY_NOTES_1","howToDemo":"TEST_STORY_HOWTODEMO_1","importance":"98","value":"98","estimate":"97","tag":"TEST_STORY_TAG_1"},
	 * {"id":"2","name":"TEST_STORY_NAME_2","notes":"TEST_STORY_NOTES_2","howToDemo":"TEST_STORY_HOWTODEMO_2","importance":"97","value":"97","estimate":"95","tag":"TEST_STORY_TAG_2"},
	 * {"id":"3","name":"TEST_STORY_NAME_3","notes":"TEST_STORY_NOTES_3","howToDemo":"TEST_STORY_HOWTODEMO_3","importance":"96","value":"96","estimate":"93","tag":"TEST_STORY_TAG_3"},
	 * {"id":"4","name":"TEST_STORY_NAME_1","notes":"TEST_STORY_NOTES_1","howToDemo":"TEST_STORY_HOWTODEMO_1","importance":"3","value":"5","estimate":"8","tag":"TEST_STORY_TAG_1"}]
	 */
	@HttpTest(
	        method = Method.GET,
	        path = "/RestfulDemo/story",
	        order = 2
    )
    public void testreadStory() {
		try {
	        JSONArray stories = new JSONArray(response.getBody());
	        for(int i = 0; i < stories.length(); i++) {
	        	JSONObject story = stories.getJSONObject(i);
	        	String id = DBserver.getAllStories().get(i).getID();
	        	String name = DBserver.getAllStories().get(i).getName();
	        	String notes = DBserver.getAllStories().get(i).getNotes();
	        	String howToDemo = DBserver.getAllStories().get(i).getHowToDemo();
	        	String importance = DBserver.getAllStories().get(i).getImportance();
	        	String value = DBserver.getAllStories().get(i).getValue();
	        	String estimate = DBserver.getAllStories().get(i).getEstimate();
	        	String tag = DBserver.getAllStories().get(i).getTag();
	        	
	        	assertEquals(id, story.get("id"));
	        	assertEquals(name, story.get("name"));
	        	assertEquals(notes, story.get("notes"));
	        	assertEquals(howToDemo, story.get("howToDemo"));
	        	assertEquals(importance, story.get("importance"));
	        	assertEquals(value, story.get("value"));
	        	assertEquals(estimate, story.get("estimate"));
	        	assertEquals(tag, story.get("tag"));
	        }
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		System.out.println("testreadStory = " + response.getBody());
		assertOk(response);
	}
	
	/**
	 * PUT
	 * testupdateStory
	 * {"id":"1","name":"TEST_STORY_NAME_1","notes":"TEST_STORY_NOTES_1","howToDemo":"TEST_STORY_HOWTODEMO_1","importance":"8","value":"8","estimate":"8","tag":"TEST_STORY_TAG_1"}
	 */
	@HttpTest(
	        method = Method.PUT,
	        path = "/RestfulDemo/story",
	        content = "{\"id\":\"1\","
	        		+ "\"name\":\"TEST_STORY_NAME_1\","
	        		+ "\"notes\":\"TEST_STORY_NOTES_1\","
	        		+ "\"howToDemo\":\"TEST_STORY_HOWTODEMO_1\","
	        		+ "\"importance\":\"8\","
	        		+ "\"value\":\"8\","
	        		+ "\"estimate\":\"8\","
	        		+ "\"tag\":\"TEST_STORY_TAG_1\"}",
	        order = 3
    )
    public void testupdateStory() {
		try {
        	JSONObject story = new JSONObject(response.getBody());
        	String id = "1";
        	String name = DBserver.getStory(id).getName();
        	String notes = DBserver.getStory(id).getNotes();
        	String howToDemo = DBserver.getStory(id).getHowToDemo();
        	String importance = DBserver.getStory(id).getImportance();
        	String value = DBserver.getStory(id).getValue();
        	String estimate = DBserver.getStory(id).getEstimate();
        	String tag = DBserver.getStory(id).getTag();
        	
        	assertEquals(id, story.get("id"));
        	assertEquals(name, story.get("name"));
        	assertEquals(notes, story.get("notes"));
        	assertEquals(howToDemo, story.get("howToDemo"));
        	assertEquals(importance, story.get("importance"));
        	assertEquals(value, story.get("value"));
        	assertEquals(estimate, story.get("estimate"));
        	assertEquals(tag, story.get("tag"));
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		System.out.println("testupdateStory = " + response.getBody());
		assertOk(response);
	}
	
	/**
	 * DELETE
	 * testdeleteStory
	 */
	@HttpTest(
	        method = Method.DELETE,
	        path = "/RestfulDemo/story",
	        content = "{\"id\":\"1\"}",
	        order = 4
    )
    public void testdeleteStory() {
		System.out.println("testdeleteStory = " + response.getBody());
		assertOk(response);
	}
}
