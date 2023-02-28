package moviesClasses;

import Enums.Color;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Optional;

public class Person{
    private String name;
    private LocalDate birthday;
    private String passportID;
    private Color hairColor;
    private Location location;



    public Person(String name, LocalDate birthday, String passportID, Color hairColor, Location location){
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }

    public Person(){}
    @JsonProperty("name")
    public String getName(){
        return name;
    }

    @JsonProperty("birthday")
    public LocalDate getBirthday(){
        return birthday;
    }

    @JsonProperty("passportID")
    public String getPassportID(){
        return passportID;
    }

    @JsonProperty("hairColor")
    public Color getHairColor(){
        return hairColor;
    }

    @JsonProperty("location")
    public Location getLocation(){
        return location;
    }
}
