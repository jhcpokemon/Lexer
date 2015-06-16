package src.utils;

import java.io.*;

/**
 * Created by jhcpokemon on 05/11/15.
 */
public class ClearComment {
    String source;
    File file;

    public String getSource(File file) {
        this.file = file;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            source = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return source;
    }

    public String getNewSource(String source) {
        String s = source.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
        s = s.replaceAll("\\s", "");
        return s;
    }
}
