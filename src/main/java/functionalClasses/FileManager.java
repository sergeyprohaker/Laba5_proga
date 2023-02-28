package functionalClasses;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import moviesClasses.Movies;
import java.io.*;
import java.util.Scanner;



public class FileManager {
    private static Movies movies;
    static ObjectMapper objectMapper = new ObjectMapper();
    static BufferedReader currentReader;


    public static Movies fill() {
        try {
            objectMapper.registerModule(new JavaTimeModule());
             return objectMapper.readValue(new File(FilePathInitializer.getPath()), Movies.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void manageClass(Movies movies){
        FileManager.movies = movies;
    }

    public static void readFile(Scanner fileScanner, String fileName) { //TODO: переписать на Buffered
        try {
//            while ((currentReader.readLine()) != null) {
//                String line = currentReader.readLine();
//                CommandManager.startNewAction(line);
//            }
//            currentReader.close();
//            CommandManager.getExecutedFiles().remove(fileName);
//            //CommandManager.currentReader = new BufferedReader(new InputStreamReader(System.in));
//            CommandManager.chosenScanner = new Scanner(System.in);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                CommandManager.startNewAction(line);
            }
            fileScanner.close();
            CommandManager.getExecutedFiles().remove(fileName);
            CommandManager.chosenScanner = new Scanner(System.in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void save() {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(new File(FilePathInitializer.getPath()), movies);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
