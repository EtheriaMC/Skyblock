package xyz.etheriamc.skyblock.util;

import co.aikar.commands.InvalidCommandArgument;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class NumberUtil {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)([kKmMbB]?)");

    public static int convertShorthandToInt(String shorthand) throws Exception {
        Matcher matcher = NUMBER_PATTERN.matcher(shorthand);

        if (matcher.matches()) {
            String numberPart = matcher.group(1);
            String suffix = matcher.group(3);

            double number = Double.parseDouble(numberPart);
            int multiplier;
            switch (suffix.toLowerCase()) {
                case "k":
                    multiplier = 1_000;
                    break;
                case "m":
                    multiplier = 1_000_000;
                    break;
                case "b":
                    multiplier = 1_000_000_000;
                    break;
                default:
                    multiplier = 1;
                    break;
            }

            long result = (long) (number * multiplier);

            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                throw new Exception("Resulting number is out of Int range: " + result);
            }

            return (int) result;
        } else {
            throw new InvalidCommandArgument("You can only pay using shorthands or numbers!", false);
        }
    }
}

