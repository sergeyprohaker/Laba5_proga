package moviesClasses;

import Enums.Color;
import Enums.MovieGenre;
import Enums.MpaaRating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class Movie implements Comparable<Object>{
    private Integer id;
    private String name;
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private Integer length; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле не может быть null


    public Movie(Integer id, String name, Coordinates coordinates, LocalDate creationDate, int oscarsCount, Integer length, MovieGenre genre, MpaaRating mpaaRating, Person director){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.length = length;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }

    public Movie(int id, HashMap data) throws IOException {
        this.id = id;
        this.name = (String) data.get(0);
        this.coordinates = new Coordinates((double) data.get(1), (float) data.get(2));
        this.creationDate = LocalDate.now();
        this.oscarsCount = (int) data.get(3);
        this.length = (Integer) data.get(4);
        this.genre = (MovieGenre) data.get(5);
        if (data.get(6) == null){
            this.mpaaRating = null;
        }
        else {
            this.mpaaRating = (MpaaRating) data.get(6);
        }
        this.director = new Person((String) data.get(7), (LocalDate) data.get(8), (data.get(9) == null ? null : (String) data.get(9)),
                (data.get(10) == null ? null : (Color) data.get(10)),
                new Location((float) data.get(11), (Integer) data.get(12), (Float) data.get(13), (data.get(14) == null ? null : (String) data.get(14))));
    }


    public void updateMovies(HashMap data) throws IOException {
        this.name = (String) data.get(0);
        this.coordinates = new Coordinates((double) data.get(1), (float) data.get(2));
        this.creationDate = LocalDate.now();
        this.oscarsCount = (int) data.get(3);
        this.length = (Integer) data.get(4);
        this.genre = (MovieGenre) data.get(5);
        if (data.get(6) == null){
            this.mpaaRating = null;
        }
        else {
            this.mpaaRating = (MpaaRating) data.get(6);
        }
        this.director = new Person((String) data.get(7), (LocalDate) data.get(8), (data.get(9) == null ? null : (String) data.get(9)),
                (data.get(10) == null ? null : (Color) data.get(10)),
                new Location((float) data.get(11), (Integer) data.get(12), (Float) data.get(13), (data.get(14) == null ? null : (String) data.get(14))));
    }

    public int compareTo(Movie o1) {
        return this.id.compareTo(o1.id);
    }

    public Movie(){}

    @JsonProperty("id")
    public Integer getId(){
        return id;
    }

    @JsonProperty("name")
    public String getName(){
        return name;
    }

    @JsonProperty("length")
    public Integer getLength(){
        return length;
    }

    @JsonProperty("oscarsCount")
    public int getOscarsCount() {
        return oscarsCount;
    }

    @JsonProperty("genre")
    public MovieGenre getGenre(){
        return genre;
    }

    @JsonProperty("coordinates")
    public Coordinates getCoordinates(){
        return coordinates;
    }

    @JsonProperty("creationDate")
    public LocalDate getCreationDate(){
        return creationDate;
    }

    @JsonProperty("mpaaRating")
    public MpaaRating getMpaaRating(){
        return mpaaRating;
    }

    @JsonProperty("director")
    public Person getDirector(){
        return director;
    }

    @JsonIgnore
    public String[] getInstance(){
        return new String[]{"id=" + id + ", name=" + name + ", coordinates=[" + "x:" + coordinates.getX() + "y:" + coordinates.getY() + "]" + ", " +
                "creationDate=" + creationDate + ", oscarsCount=" + oscarsCount + ", length=" + length + ", genre=" + genre + ", " +
                "mpaaRating=" + mpaaRating + ", director: [" + director.getName() + director.getPassportID() + director.getBirthday() +
                "location:[" + director.getLocation().getX() + director.getLocation().getY() + director.getLocation().getZ() + director.getLocation().getName() + "]]" + "}"};
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}