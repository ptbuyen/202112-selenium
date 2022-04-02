package test_data;

import com.google.gson.Gson;
import utils.data.DataObjectBuilder;

public class TestGson {

    public static void main(String[] args) {
        testFromObjectToJson();
        testFromJsonToObject();
    }

    private static void testFromObjectToJson() {
        User user = new User("Teo", 20);
        Gson gson = new Gson();
        System.out.println(gson.toJson(user));
    }

    private static void testFromJsonToObject() {
        String jsonFileLocation = "/src/main/resources/test-data/User.json";
        User[] users = DataObjectBuilder.buildDataObjectFrom(jsonFileLocation, User[].class);

        for (User user : users) {
            System.out.println(user);
        }
    }

}
