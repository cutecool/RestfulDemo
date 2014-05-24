package ntut.restful.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ntut.restful.object.StoryObject;

public class DBConnection {
	private Connection connection = null;
	private Statement stat = null;
	private ResultSet result = null;
	private PreparedStatement pst = null;

	private String url = "jdbc:mysql://localhost:3306/Demotest?useUnicode=true&characterEncoding=Big5";
	private String username = "cutecool";
	private String userpswd = "1234";

	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, userpswd);
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Exception :");
			e.printStackTrace();
		}
	}

	public String createTable() {
		try {
			stat = connection.createStatement();
			stat.executeUpdate("CREATE TABLE Story ("
			        + "id int NOT NULL AUTO_INCREMENT,"
			        + "name text,"
			        + "value int,"
			        + "estimate int,"
			        + "importance int,"
			        + "notes text,"
			        + "tag text,"
			        + "howtodemo text,"
			        + "PRIMARY KEY(id));");
			close();
			return "Table Create Success";
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :");
			e.printStackTrace();
			close();
			return "Table Create Failed";
		}
	}

	public int createStory(StoryObject story) {
		try {
			pst = connection.prepareStatement("INSERT INTO Story(name,value,estimate,importance,notes,tag,howtodemo)"
			        + " VALUES ('" + story.getName() + "',"
			        + Integer.parseInt(story.getValue()) + ","
			        + Integer.parseInt(story.getEstimate()) + ","
			        + Integer.parseInt(story.getImportance()) + ","
			        + "'" + story.getNotes() + "',"
			        + "'" + story.getTag() + "',"
			        + "'" + story.getHowToDemo() + "');", Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			int storyid = rs.getInt(1);
			close();
			return storyid;
		} catch (SQLException e) {
			System.out.println("InsertDB Exception :");
			e.printStackTrace();
			close();
			return -1;
		}
	}

	public void dropTable() {
		try {
			stat = connection.createStatement();
			stat.executeUpdate("TRUNCATE TABLE Story;");
		} catch (SQLException e) {
			System.out.println("DropDB Exception :");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public StoryObject getStory(String id) {
		StoryObject story = new StoryObject();
		try {
			stat = connection.createStatement();
			result = stat.executeQuery("SELECT * FROM Story WHERE("
			        + "id=" + id + ");");
			result.next();
			story.setID(String.valueOf(result.getInt("id")));
			story.setName(result.getString("name"));
			story.setValue(String.valueOf(result.getInt("value")));
			story.setEstimate(String.valueOf(result.getInt("estimate")));
			story.setImportance(String.valueOf(result.getInt("importance")));
			story.setNotes(result.getString("notes"));
			story.setTag(result.getString("tag"));
			story.setHowToDemo(result.getString("howToDemo"));
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :");
			e.printStackTrace();
		} finally {
			close();
		}
		return story;
	}

	public List<StoryObject> getAllStories() {
		List<StoryObject> storylist = new ArrayList<StoryObject>();
		try {
			stat = connection.createStatement();
			result = stat.executeQuery("SELECT * FROM Story");
			while (result.next()) {
				StoryObject story = new StoryObject();
				story.setID(String.valueOf(result.getInt("id")));
				story.setName(result.getString("name"));
				story.setValue(String.valueOf(result.getInt("value")));
				story.setEstimate(String.valueOf(result.getInt("estimate")));
				story.setImportance(String.valueOf(result.getInt("importance")));
				story.setNotes(result.getString("notes"));
				story.setTag(result.getString("tag"));
				story.setHowToDemo(result.getString("howToDemo"));
				storylist.add(story);
			}
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :");
			e.printStackTrace();
		} finally {
			close();
		}
		return storylist;
	}

	public void updateStory(StoryObject story) {
		try {
			stat = connection.createStatement();
			String query = "UPDATE Story SET "
			        + "name='" + story.getName() + "',"
			        + "value=" + Integer.parseInt(story.getValue()) + ","
			        + "estimate=" + Integer.parseInt(story.getEstimate()) + ","
			        + "importance=" + Integer.parseInt(story.getImportance()) + ","
			        + "notes='" + story.getNotes() + "',"
			        + "tag='" + story.getTag() + "',"
			        + "howToDemo='" + story.getHowToDemo() + "' "
			        + "WHERE id=" + Integer.parseInt(story.getID()) + ";";
			stat.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("UpdateStory Exception :");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteStory(String id) {
		try {
			stat = connection.createStatement();
			String query = "DELETE FROM Story WHERE id=" + Integer.parseInt(id) + ";";
			stat.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("DeleteStory Exception :");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	private void close() {
		try {
			if (result != null) {
				result.close();
				result = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("CloseDB Exception :");
			e.printStackTrace();
		}
	}
}
