package moviesClasses;

import java.util.*;

public class Movies{
    public static TreeSet<Movie> movieList = new TreeSet<>();
    private java.util.Date initializationDate;


    public Movies(){
        this.initializationDate = new Date();
    }

    public int countMovies(){
        return movieList.size();
    }

    public TreeSet<Movie> getMovies(){
        return movieList;
    }

    public Date getInitializationDate() {
        return initializationDate;
    }
    public int moviesCount(){
        return movieList.size();
    }


//    public static List<Movie> getSortedMovieList(){
//        List<Movie> sortedMovieList = new ArrayList<>(movieList);
//        sortedMovieList.sort((o1, o2) -> o2.compareTo(o1));
//        sortedMovieList.addAll(movieList);
//        return sortedMovieList;
//    }
}
