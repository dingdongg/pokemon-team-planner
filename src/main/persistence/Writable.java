package persistence;

import org.json.JSONObject;

// code in this class was referenced from JsonSerializationDemo in Phase 2, Task 4 of the personal project
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
