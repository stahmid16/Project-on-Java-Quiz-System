import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class student_login {

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONArray usersArray = (JSONArray) jsonParser.parse(new FileReader("./src/main/resources/user.json"));
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        boolean loginSuccess = false;

        for (Object obj : usersArray) {
            JSONObject userObject = (JSONObject) obj;
            String username = (String) userObject.get("username");
            String password = (String) userObject.get("password");
            String role = (String) userObject.get("role");

            if (username.equals(inputUsername) && password.equals(inputPassword)) {
                loginSuccess = true;
                System.out.println("Login Successful! Welcome, " + role + "!");
                break;
            }
        }

        if (!loginSuccess) {
            System.out.println("Login Unsuccessful. Please check your username or password.");
            return;
        }

        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String[]> options = new ArrayList<>();
        ArrayList<Integer> answers = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONArray quizArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/quiz.json"));

        for (Object obj : quizArray) {
            JSONObject quizObject = (JSONObject) obj;
            String questionText = (String) quizObject.get("question");
            String option1 = (String) quizObject.get("option 1");
            String option2 = (String) quizObject.get("option 2");
            String option3 = (String) quizObject.get("option 3");
            String option4 = (String) quizObject.get("option 4");
            int answerKey = Integer.parseInt(quizObject.get("answerkey").toString());

            questions.add(questionText);
            options.add(new String[]{option1, option2, option3, option4});
            answers.add(answerKey);
        }

        System.out.println("System:> Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' for start.");
        String startCommand = scanner.nextLine();
        if (!startCommand.equalsIgnoreCase("s")) {
            System.out.println("System:> Exiting the quiz. See you next time!");
            return;
        }

        Random random = new Random();
        int score = 0;
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(questions.size());

            System.out.println("System:>\n[Question " + (i + 1) + "] " + questions.get(index));
            System.out.println("1. " + options.get(index)[0]);
            System.out.println("2. " + options.get(index)[1]);
            System.out.println("3. " + options.get(index)[2]);
            System.out.println("4. " + options.get(index)[3]);
            System.out.print("Student:> ");
            int answer = scanner.nextInt();

            if (answer == answers.get(index)) {
                score++;
            }
        }

        System.out.println("System:> Quiz completed! Your score is " + score + " out of 10");
    }
}
