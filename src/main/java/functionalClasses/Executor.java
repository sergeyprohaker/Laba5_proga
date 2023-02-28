package functionalClasses;

import moviesClasses.Movie;
import moviesClasses.Movies;

import java.io.IOException;
import java.util.*;

import static moviesClasses.Movies.movieList;

public class Executor {
    private static Movies movies;
    private static List<String> commandHistory = new ArrayList<>();
    private static List<Movie> matchingGenres = new ArrayList<>();

    private static int NextId = 0;
    public static void setMovies(Movies movies){
        Executor.movies = movies;
    }

    public static void addMovie(HashMap data) throws IOException {
        int newID = getNextId();
        movies.getMovies().add(new Movie(newID, data));
        System.out.println(movies.getMovies().last());
    }


    private static int getNextId(){
        return NextId++;
    }
    public static boolean updateMovie(int id, HashMap data) throws IOException {
        for (Movie movie : movies.getMovies()) {
            if (movie.getId() == id) {
                movie.updateMovies(data);
                return true;
            }
        }
        return false;
    }

    public static boolean removeById(int enteredId) {
        for (Movie movie : movies.getMovies()) {
            if (movie.getId() == enteredId) {
                movies.getMovies().remove(movie);
                return true;
            }
        }
        return false;
    }

    public static void clear() {
        movies.getMovies().clear();
    }

    public static void addIfMin(HashMap data) throws IOException {
        boolean isMin = true;
        for (Movie movie : movies.getMovies()) {
            if (movie.getLength() <= (Integer) data.get(4)) {
                isMin = false;
                break;
            }
        }
        if (isMin) {
            addMovie(data);
        }
    }



    public static boolean removeAnyByOscarsCount(long enteredCount) {
        for (Movie movie : movies.getMovies()) {
            if (movie.getOscarsCount() == enteredCount) {
                movies.getMovies().remove(movie);
                return true;
            }
        }
        return false;
    }


    public static void formCommandHistory(String command){
        commandHistory.add(command);
    }

    public static List<String> getLast8Commands() {
        return commandHistory.subList(commandHistory.size() >= 8 ? commandHistory.size() - 8 : 0, commandHistory.size());
    }

    public static void removeGreater(HashMap data) {
        movies.getMovies().removeIf(movie -> movie.getLength() >= (int) data.get(4));
    }


    public static void filterByGenre(HashMap data) {
        for (Movie movie : movies.getMovies()){
            if (movie.getGenre() == data.get(5)){
                matchingGenres.add(movie);
            }
        }
    }

    public static List<Movie> printDescending(){
        List<Movie> sortedMovieList = new ArrayList<>(movieList);
        sortedMovieList.sort((o1, o2) -> (Integer.compare(o1.getId() - o2.getId(), 0)));
        return sortedMovieList;
    }

}
