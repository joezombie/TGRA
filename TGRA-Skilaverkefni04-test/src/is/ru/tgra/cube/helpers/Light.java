package is.ru.tgra.cube.helpers;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public class Light {
    public int ID;
    public float[] lightDiffuse = new float[4];
    public float[] lightPosition = new float[4];
    public float[] lightSpecular = new float[4];

    public Light(int id){
        this.ID = id;
    }

    public float[] getLightDiffuse() {
        return lightDiffuse;
    }

    public void setLightDiffuse(ColorRGB color) {
        this.lightDiffuse[0] = color.r;
        this.lightDiffuse[1] = color.g;
        this.lightDiffuse[2] = color.b;
        this.lightDiffuse[3] = 1.0f;
    }

    public float[] getLightPosition() {
        return lightPosition;
    }

    public void setLightPosition(Point3D point) {
        this.lightPosition[0] = point.x;
        this.lightPosition[1] = point.y;
        this.lightPosition[2] = point.z;
        this.lightPosition[3] = 1.0f;
    }

    public void setLightSpecular(ColorRGB color){
        this.lightSpecular[0] = color.r;
        this.lightSpecular[1] = color.g;
        this.lightSpecular[2] = color.b;
        this.lightSpecular[3] = 1.0f;
    }

    public float[] getLightSpecular() {
        return lightSpecular;
    }
}
