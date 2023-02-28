package moviesClasses;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
public class Location{
    private float x;
    private Integer y;
    private Float z;
    private String name;


    public Location(float x, Integer y, Float z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location(){}

    @JsonProperty("x")
    public float getX(){
        return x;
    }

    @JsonProperty("y")
    public Integer getY(){
        return y;
    }

    @JsonProperty("z")
    public Float getZ(){
        return z;
    }

    @JsonProperty("name")
    public String getName(){
        return name;
    }

}
