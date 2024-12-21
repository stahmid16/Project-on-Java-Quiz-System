import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class admin_login {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/main/resources/user.json"));
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        boolean loginSuccess = false;

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String uname = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");

            if (uname.equals(inputUsername) && password.equals(inputPassword)) {
                loginSuccess = true;
                break;
            }
        }

        if (loginSuccess) {
            System.out.println("Login Successful!");
        } else {
            System.out.println("Login Unsuccessful. Please check your username and password.");
            return;
        }


        ArrayList<HashMap<String, String>> questions = new ArrayList<>();

        System.out.println("Welcome, Admin! Let's build the quiz bank.");
        while (true) {
            System.out.println("Press 's' to start adding questions or 'q' to quit:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            } else if (input.equalsIgnoreCase("s")) {
                HashMap<String, String> questionData = new HashMap<>();

                System.out.println("Input your question:");
                String question = scanner.nextLine();
                questionData.put("question", question);

                for (int i = 1; i <= 4; i++) {
                    System.out.println("Input option " + i + ":");
                    String option = scanner.nextLine();
                    questionData.put("option " + i, option);
                }

                System.out.println("What is the answer key? (1-4):");
                String answerKey = scanner.nextLine();
                questionData.put("answerkey", answerKey);

                questions.add(questionData);
                System.out.println("Saved successfully!");
            } else {
                System.out.println("Invalid input. Please press 's' to start or 'q' to quit.");
            }
        }

        System.out.println("Saving quiz bank to file...");

        try {
            JSONParser parser = new JSONParser();
            JSONArray existingQuestions;

            // Read existing data from the file
            try (FileReader reader = new FileReader("./src/main/resources/quiz.json")) {
                Object obj = parser.parse(reader);
                existingQuestions = (JSONArray) obj;
            } catch (IOException | ParseException e) {

                existingQuestions = new JSONArray();
            }

            for (HashMap<String, String> questionData : questions) {
                JSONObject questionObject = new JSONObject();
                questionObject.put("question", questionData.get("question"));
                for (int i = 1; i <= 4; i++) {
                    questionObject.put("option " + i, questionData.get("option " + i));
                }
                questionObject.put("answerkey", questionData.get("answerkey"));
                existingQuestions.add(questionObject);
            }


            try (FileWriter file = new FileWriter("./src/main/resources/quiz.json")) {
                file.write(existingQuestions.toJSONString());
                System.out.println("Quiz bank saved successfully! Goodbye.");
            }
        } catch (IOException e) {
            System.out.println("Error saving quiz bank: " + e.getMessage());
        }
    }
}
