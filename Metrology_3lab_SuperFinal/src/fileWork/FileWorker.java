package fileWork;

import parsing.Search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileWorker {

    public static String readFle (String filePath) {

        StringBuilder text = new StringBuilder() ;

        try (BufferedReader Reader = new BufferedReader((new InputStreamReader(new FileInputStream(filePath),
              StandardCharsets.UTF_8))) ) {

            String line = Reader.readLine();

            while (line != null) {
                text.append(line).append("\n");
                line = Reader.readLine() ;
            }

        } catch (IOException e) { e.printStackTrace(); }

        return String.valueOf(text) ;
    }
}
