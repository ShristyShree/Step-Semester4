package week1;
import java.util.*;
public class program1 {


        private HashMap<String, Integer> usernameMap;
        private HashMap<String, Integer> attemptFrequency;

        public program1() {
            usernameMap = new HashMap<>();
            attemptFrequency = new HashMap<>();
        }

        public void registerUser(String username, int userId) {
            usernameMap.put(username, userId);
        }

        public boolean checkAvailability(String username) {
            attemptFrequency.put(username,
                    attemptFrequency.getOrDefault(username, 0) + 1);

            return !usernameMap.containsKey(username);
        }

        public List<String> suggestAlternatives(String username) {

            List<String> suggestions = new ArrayList<>();

            if (checkAvailability(username)) {
                suggestions.add(username);
                return suggestions;
            }

            int count = 1;
            while (suggestions.size() < 3) {
                String newName = username + count;

                if (!usernameMap.containsKey(newName)) {
                    suggestions.add(newName);
                }
                count++;
            }

            String modified = username.replace("_", ".");
            if (!usernameMap.containsKey(modified)) {
                suggestions.add(modified);
            }

            return suggestions;
        }

        public String getMostAttempted() {

            String mostTried = "";
            int maxAttempts = 0;

            for (String user : attemptFrequency.keySet()) {
                int freq = attemptFrequency.get(user);

                if (freq > maxAttempts) {
                    maxAttempts = freq;
                    mostTried = user;
                }
            }

            return mostTried;
        }

        public static void main(String[] args) {

            program1 checker = new program1();

            checker.registerUser("john_doe", 101);
            checker.registerUser("admin", 1);

            System.out.println("checkAvailability(\"john_doe\") → "
                    + checker.checkAvailability("john_doe"));

            System.out.println("checkAvailability(\"jane_smith\") → "
                    + checker.checkAvailability("jane_smith"));

            System.out.println("suggestAlternatives(\"john_doe\") → "
                    + checker.suggestAlternatives("john_doe"));

            for (int i = 0; i < 5; i++) checker.checkAvailability("admin");

            System.out.println("getMostAttempted() → "
                    + checker.getMostAttempted());
        }
    }


