package persistence;

import org.json.JSONObject;

// represents a class that can be written/saved to a JSON file
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
