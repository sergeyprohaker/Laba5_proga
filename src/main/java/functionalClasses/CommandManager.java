package functionalClasses;

import Enums.Color;
import Enums.MovieGenre;
import Enums.MpaaRating;
import moviesClasses.Movie;
import moviesClasses.Movies;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommandManager {

    private static Movies movies;
    static Scanner chosenScanner = new Scanner(System.in);

    //static BufferedInputStream bis = new BufferedInputStream(System.in);

    //static BufferedReader currentReader = new BufferedReader(new InputStreamReader(bis));
    static String currentCommand;
    public static List<BufferedInputStream> bufferedInputStreamsList;

    private static List<String> executedFiles = new ArrayList<>();

    static HashMap<String, String> commandList = new HashMap<>();


    static ArrayList<SetValues> settings = new ArrayList<>();
    static HashMap<Integer, Object> answers = new HashMap<>();


    static {
        commandList.put("help", "Вывести справку по доступным командам");
        commandList.put("info", "Вывести в стандартный поток вывода информацию о коллекции");
        commandList.put("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        commandList.put("add {element}", "добавить новый элемент в коллекцию");
        commandList.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        commandList.put("remove_by_id id", "удалить элемент из коллекции по его id");
        commandList.put("clear", "очистить коллекцию");
        commandList.put("save", "сохранить коллекцию в файл");
        commandList.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        commandList.put("exit", "завершить программу (без сохранения в файл)");
        commandList.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        commandList.put("remove_greater {element}", " удалить из коллекции все элементы, превышающие заданный");
        commandList.put("history", "вывести последние 8 команд (без их аргументов)");
        commandList.put("remove_any_by_oscars_count oscarsCount", "удалить из коллекции один элемент, значение поля oscarsCount которого эквивалентно заданному");
        commandList.put("filter_by_genre genre", "вывести элементы, значение поля genre которых равно заданному");
        commandList.put("print_descending", "вывести элементы коллекции в порядке убывания");

        settings.add(new SetValues(0, "String", true, "Название фильма: "));
        settings.add(new SetValues(1, "double", true, "Координата X (можно дробное, больше -201): "));
        settings.add(new SetValues(2, "float", true, "Координата Y (тоже дробное, больше -838): "));
        settings.add(new SetValues(3, "int", true, "Количество Оскаров у фильма (больше 0): "));
        settings.add(new SetValues(4, "Integer", true, "Длина (целое, больше 0): "));
        settings.add(new SetValues(5, "MovieGenre", true, "Жанр фильма: " + Arrays.asList(MovieGenre.values())));
        settings.add(new SetValues(6, "MpaaRating", false, "Рейтинг фильма: "  + Arrays.asList(MpaaRating.values())));
        settings.add(new SetValues(7, "String", true, "Данные режиссера. Имя: "));
        settings.add(new SetValues(8, "LocalDate", false, "Дата рождения: "));
        settings.add(new SetValues(9, "String", false, "Данные паспорта (можно ваши): "));
        settings.add(new SetValues(10, "Color", false, "Цвет волос: " + Arrays.asList(Color.values())));
        settings.add(new SetValues(11, "float", true, "Местоположение режиссера. Координата Х: "));
        settings.add(new SetValues(12, "Integer", true, "Координата Y: "));
        settings.add(new SetValues(13, "Float", true, "Координата Z: "));
        settings.add(new SetValues(14, "String", false, "Название локации: "));
    }

    public static void setMovies(Movies movies){
        CommandManager.movies = movies;
    }

    public static void suggestNewAction() throws IOException {
//        System.out.println();
        System.out.println("Введите команду. Чтобы узнать перечень доступных команд введите help");
        currentCommand = chosenScanner.nextLine();
    }

    public static void startNewAction(String executedCommand) throws Exception {
        try {
            Executor.formCommandHistory(executedCommand.split(" ")[0]);
            if (Objects.equals(executedCommand.split(" ")[0], "execute_script") && !chosenScanner.equals(new Scanner(System.in)) && executedFiles.contains(executedCommand.split(" ")[1])) {
                System.out.println("Рекурсия!");
                return;
            } else if (Objects.equals(executedCommand.split(" ")[0], "execute_script") && (chosenScanner.equals(new Scanner(System.in)) || !executedFiles.contains(executedCommand.split(" ")[1]))) {
                Path path = Paths.get(executedCommand.split(" ")[1]);
                chosenScanner = new Scanner(path);
                executedFiles.add(executedCommand.split(" ")[1]);
            }
            switch (executedCommand.split(" ")[0]) {
                case ("help") -> help();
                case ("info") -> info();
                case ("add") -> Executor.addMovie(readInputNewMovieData());
                case ("add_if_min") -> Executor.addIfMin(readInputNewMovieData());
                case("remove_greater") -> Executor.removeGreater(readInputNewMovieData());
                case ("clear") -> Executor.clear();
                case ("history") -> System.out.println(Executor.getLast8Commands());
                case ("execute_script") -> FileManager.readFile(chosenScanner, executedCommand.split(" ")[1]);
                case ("save") -> FileManager.save();
                case ("show") -> show();
                case("filter_by_genre") -> Executor.filterByGenre(readInputNewMovieData());
                case ("remove_by_id") -> {
                    if (executedCommand.matches("remove_by_id \\d*")) {
                        if (!Executor.removeById(Integer.parseInt(executedCommand.split(" ")[1]))) {
                            System.out.println("Фильма с таким id нет в коллекции");
                        };
                    } else {
                        System.out.println("id должно быть целым числом");
                    }
                }
                case ("remove_any_by_oscars_count") -> {
                    if (executedCommand.matches("remove_any_by_oscars_count \\d*")) {
                        if (!Executor.removeAnyByOscarsCount(Integer.parseInt(executedCommand.split(" ")[1]))) {
                            System.out.println("В коллекци нет ни 1 фильма с таким количеством оскаров");
                        }
                    } else {
                        System.out.println("Количество оскаров должно быть целым числом");
                    }
                }
                case ("update") -> {
                    if (executedCommand.matches("update \\d*") && Integer.parseInt(executedCommand.split(" ")[1]) >= 0) {
                        if (!Executor.updateMovie(Integer.parseInt(executedCommand.split(" ")[1]), readInputNewMovieData())) {
                            System.out.println("В коллекци нет ни одного фильма с таким id (введите add, чтобы создать)");
                        }
                    } else {
                        System.out.println("id должно быть целым числом");
                    }

                }
                case("print_descending") -> Executor.printDescending();
                default -> System.out.println("Введите команду из доступного перечня");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static HashMap<Integer, Object> readInputNewMovieData() {
        int step = 0;
        while (step < CommandManager.settings.size()) {
            try {
                System.out.println(CommandManager.settings.get(step).getComment() + ". Тип этого значения: " + CommandManager.settings.get(step).getValueType() + (CommandManager.settings.get(step).getIsRequired() ? ". Обязательное значение" : ". Необязательное значение"));
                String line = chosenScanner.nextLine().trim();
                if (line.equals("exit")) {System.exit(0);}
                if (line.length() == 0 && CommandManager.settings.get(step).getIsRequired()) {
                    System.out.println("Значение не может быть пустым");
                    continue;
                } else {
                    if (line.length() == 0) {
                        answers.put(step, null);
                        step += 1;
                        continue;
                    }
                }
                    switch (CommandManager.settings.get(step).getValueType()) {
                        case ("float"), ("Float") -> {
                            float parsedValue = Float.parseFloat(line);
                            if ((settings.get(step).getKey() == 2 && parsedValue <= -838) ) {
                                System.out.println("Значение должно быть больше -838");
                            }
                            else {
                                answers.put(step, parsedValue);
                                step += 1;
                            }
                            answers.put(step, parsedValue);
                        }

                        case ("double") -> {
                            double parsedValue = Double.parseDouble(line);
                            if (settings.get(step).getKey() == 2 && parsedValue <= -201){
                                System.out.println("Значение должно быть больше -201");
                            }
                            else {
                                answers.put(step, parsedValue);
                                step += 1;
                            }
                        }

                        case ("LocalDate") -> {
                            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
                            Matcher matcher = pattern.matcher(line);
                            try {
                                LocalDate parsedValue = LocalDate.parse(line);
                                answers.put(step, parsedValue);
                                step += 1;
                            }
                            catch (DateTimeParseException e){
                                System.out.println("Введите валидную дату в формате yyyy-mm-dd");
                            }
                        }

                        case ("int"), ("Integer") -> {
                            int parsedValue = Integer.parseInt(line);
                            if ((settings.get(step).getKey() == 3 || settings.get(step).getKey() == 4) && parsedValue <= 0) {
                                System.out.println("Значение должно быть больше нуля");
                            } else {
                                answers.put(step, parsedValue);
                                step += 1;
                            }

                        }
                        case ("String") -> {
                            if ((settings.get(step).getKey() == 0 || settings.get(step).getKey() == 7 || settings.get(step).getKey() == 9) && line.trim().isEmpty()) {
                                System.out.println("Значение не может быть пустым");
                            } else {
                                if (settings.get(step).getKey() == 8 && line.length() < 10) {
                                    System.out.println("Значение должно состоять не менее чем из 10 символов");
                                } else {
                                    answers.put(step, line);
                                    step += 1;
                                }
                            }

                        }
                        case ("MovieGenre") -> {
                            MovieGenre parsedValue = Enum.valueOf(MovieGenre.class, line);
                            answers.put(step, parsedValue);
                            step += 1;
                        }
                        case ("MpaaRating") -> {
                            MpaaRating parsedValue = Enum.valueOf(MpaaRating.class, line);
                            answers.put(step, parsedValue);
                            step += 1;
                        }
                        case ("Color") -> {
                            Color parsedValue = Enum.valueOf(Color.class, line);
                            answers.put(step, parsedValue);
                            step += 1;
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введите значение правильного типа данных: " + CommandManager.settings.get(step).getValueType());
                } catch (IllegalArgumentException e) {
                    System.out.println("Введите значение из списка допустимых значений ->");
                }
        }
            return answers;
        }

        public static void help() {
            for (var entry : commandList.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }

        public static void info() {
            System.out.println("Класс элементов коллекции: " + movies.getClass());
            System.out.println("Дата и время ининциализации коллекции: " + movies.getInitializationDate());
            System.out.println("Количество элементов в колллекции: " + movies.moviesCount());

            System.out.println("Список имеющихся в коллекции фильмов (id + название)");

            for (Movie movie : movies.getMovies()) {
                System.out.println(movie.getId() + " - " + movie.getName());
            }
            System.out.println("Исполняемые в данный момент файлы: " + getExecutedFiles());
        }

        public static void show() {
            for (Movie movie : movies.getMovies()) {
                for (String s : movie.getInstance()) {
                    System.out.println(s);
                }
            }
        }

        // getters

        public static String getExecutedCommand() {
            return currentCommand;
        }

        public static List getExecutedFiles() {
            return executedFiles;
        }
    }

