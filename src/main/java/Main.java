import functionalClasses.CommandManager;
import functionalClasses.Executor;
import functionalClasses.FileManager;
import functionalClasses.FilePathInitializer;
import moviesClasses.Movies;


import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            System.out.println("Введите путь к json файлу: ");
            FilePathInitializer.formPath(new Scanner(System.in).nextLine().split(" "));

            //System.out.println(FilePathInitializer.getPath().getClass());

            //Movies movies = new Movies();
            Movies movies = FileManager.fill();

            CommandManager.setMovies(movies);
            Executor.setMovies(movies);
            FileManager.manageClass(movies);

            CommandManager.suggestNewAction();
            while (!Objects.equals(CommandManager.getExecutedCommand(), "exit")) {
                CommandManager.startNewAction(CommandManager.getExecutedCommand());
                CommandManager.suggestNewAction();
            }
        } catch (Exception | ExceptionInInitializerError e) {
            System.out.println("Проверьте, что правильно указали название (адрес) файла, из которого считывается коллекция");
        }
    }
}