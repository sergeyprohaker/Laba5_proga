package moviesClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import functionalClasses.FilePathInitializer;

import java.io.File;
import java.io.IOException;

public class Coordinates {

    private double x;

    private float y;


    public Coordinates(double x, float y) throws IOException {
        this.x = x;
        this.y = y;
    }

    public Coordinates(){}

    @JsonProperty("x")
    public double getX(){
        return x;
    }


    @JsonProperty("y")
    public float getY(){
        return y;
    }

}
