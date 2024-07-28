package xyz.etheriamc.skyblock.util;

public class StringUtils {

    public static String toTitleCase(String input) {
        String[] words = input.split("_");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return titleCase.toString().trim();
    }
}