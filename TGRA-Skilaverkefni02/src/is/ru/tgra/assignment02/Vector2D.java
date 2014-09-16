package is.ru.tgra.assignment02;

/**
 * Created by Johannes Gunnar Heidarsson on 16.9.2014.
 */
public class Vector2D {
    public float x;
    public float y;

    public Vector2D(){
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other){
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public boolean equals(Object other){
        boolean result = false;
        if(other instanceof Vector2D){
            Vector2D otherVector2D = (Vector2D) other;
            result = this.x == otherVector2D.x && this.y == otherVector2D.y;
        }
        return result;
    }

    public static Vector2D multiply(Vector2D a, Vector2D b){
        return new Vector2D(a.x * b.x, b.y * a.y);
    }
}
