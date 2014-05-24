package ntut.restful.data;

import ntut.restful.object.StoryObject;
import ntut.restful.server.DBConnection;

public class CreateData {
	private DBConnection DBserver = new DBConnection();
	int storycount;
	
	public CreateData() {
		storycount = 1;
	}
	
	public CreateData(int count) {
		storycount = count;
	}

	public void exe() {
		for(int i = 1; i <= storycount; i++) {
			StoryObject story = new StoryObject();
			story.setName("TEST_STORY_NAME_" + i);
			story.setNotes("TEST_STORY_NOTES_" + i);
			story.setHowToDemo("TEST_STORY_HOWTODEMO_" + i);
			story.setImportance(String.valueOf(99 - i));
			story.setValue(String.valueOf(99 - i));
			story.setEstimate(String.valueOf(99 - 2*i));
			story.setTag("TEST_STORY_TAG_" + i);
			DBserver.createStory(story);
		}
	}
}
