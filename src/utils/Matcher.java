package utils;

import java.util.regex.Pattern;

/**
 * Created by jhcpokemon on 05/12/15.
 */
public class Matcher {
    String forMatch;
    String waitMatch;

    public Matcher(String forMatch, String waitMatch) {
        String[] str = forMatch.split("=");
        forMatch = str[1];
        this.forMatch = forMatch;
        this.waitMatch = waitMatch;
    }

    public boolean match() {
        Pattern pattern = Pattern.compile(forMatch, Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(waitMatch);
        return (matcher.matches());
    }
}
