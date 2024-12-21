import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class creare_user {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader("./src/main/resources/user.json"));

        JSONObject jsonObject =new JSONObject();

        jsonObject.put("username","student2");
        jsonObject.put("password","1234");
        jsonObject.put("role","student");

        jsonArray.add(jsonObject);
        System.out.println(jsonArray);

        FileWriter writer= new FileWriter("./src/main/resources/user.json");
        writer.write(jsonArray.toJSONString());
        writer.flush();
        writer.close();


    }
}
