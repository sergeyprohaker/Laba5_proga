package functionalClasses;

public class FilePathInitializer {
    private static String filePath;
    public static void formPath(String[] args) {
        if (args.length == 0) {
            System.out.println("Путь к файлу не может быть пустой.");
            return;
        }

        filePath = args[0];
    }

    public static String getPath(){
        return filePath;
    }

}
