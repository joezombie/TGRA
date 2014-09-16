package is.ru.tgra.assignment02;

import java.awt.*;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */
public class Point2D {
    public float x;
    public float y;

    public Point2D(){
        this.x = 0f;
        this.y = 0f;
    }

    public Point2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D other){
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public boolean equals(Object other){
        boolean result = false;
        if(other instanceof Point2D){
            Point2D otherPoint2D = (Point2D) other;
            result = this.x == otherPoint2D.x && this.y == otherPoint2D.y;
        }
        return result;
    }

    public static Point2D multiply(Point2D a, Point2D b){
        return new Point2D(a.x * b.x, a.y * b.y);
    }
}