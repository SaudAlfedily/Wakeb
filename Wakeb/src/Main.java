import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //take input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("please Enter Encrypted Invitation ");
        String encryptedInvitation = scanner.nextLine();//Encrypted Invitation from user

        boolean onlyNum;

        //check if the user enter only numbers in the massage
        try {
            Integer.parseInt(encryptedInvitation);
            onlyNum = true;

        } catch (NumberFormatException e) {
            onlyNum = false;
        }

        if (encryptedInvitation.trim().equalsIgnoreCase("") || onlyNum || Pattern.matches("^[^\\w\\s]+$", encryptedInvitation)) {
            System.out.println("you didn't enter valid value"); //well print if user enter only number or empty massage or only symbols
            System.exit(0); //stop the system
        }

//#1 Split the message into separate words, using any symbol as a separator including spaces .
        String[] splitedInvitation = encryptedInvitation.split("[\\W_]+");

//      System.out.println(Arrays.toString(splitedInvitation)); //test split Invitation


//#2 Calculate the sum of all numeric characters in each word. And #3  Assign the calculated sum to each respective word, which becomes its numerical value.
        Map<Integer, String> wordsValue = new HashMap<>();
        for (String word : splitedInvitation) {
            int wordValue = 0;
            for (char c : word.toCharArray()) {
                if (Character.isDigit(c)) {
                    wordValue += Character.getNumericValue(c);
                }
            }

//#6 If there are multiple words with the same numerical value, keep only the last of those words and disregard the others.
            Iterator<Map.Entry<Integer, String>> iterator = wordsValue.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, String> entry = iterator.next();
                if (entry.getKey() == wordValue) {
                    iterator.remove();
                }
            }
            wordsValue.put(wordValue, word);
        }
//        System.out.println(wordsValue); //test 2 And 3 And 6

//#4 Remove all numeric characters from the words.
        Map<Integer, String> noNumberMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : wordsValue.entrySet()) {
            String word = entry.getValue();

            word = word.replaceAll("[\\d]", ""); //replace digit in the words

            noNumberMap.put(entry.getKey(), word);

        }

//        System.out.println(noNumberMap); //test 4


//#5 Arrange the words in the decrypted message in ascending order of their numerical value
// we use it as extra step to make sure that the word are sorted
        LinkedHashMap<Integer, String> sortedMap = noNumberMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

//        System.out.println(sortedMap); //test 5

//make the output in string form
        List<String> stringKeys = new ArrayList<>();
        for (String value : sortedMap.values()) {
            stringKeys.add(value.toString());
        }
        String listString = String.join(" ", stringKeys);

        System.out.println(listString);

    }
}