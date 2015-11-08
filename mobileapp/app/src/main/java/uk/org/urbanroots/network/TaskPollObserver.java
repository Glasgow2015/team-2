package uk.org.urbanroots.network;

import org.json.JSONObject;

public interface TaskPollObserver {
    void onNewTaskData(JSONObject newTaskData);
}
