package is.ru.tgra.cube.helpers;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public class ColorRGB {
    public float r;
    public float g;
    public float b;
    public float a;

    public ColorRGB(){
        this.r = 0;
        this.g = 0;
        this.g = 0;
        this.a = 0;
    }

    public ColorRGB(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public ColorRGB(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public float[] getArray(){
        return new float[]{r, g, b, a};
    }

}
