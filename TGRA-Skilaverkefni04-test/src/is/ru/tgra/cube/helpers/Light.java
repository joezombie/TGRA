package is.ru.tgra.cube.helpers;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public class Light {
    public float[] lightDiffuse = new float[4];
    public float[] lightPosition = new float[4];

    public float[] getLightDiffuse() {
        return lightDiffuse;
    }

    public void setLightDiffuse(float r, float g, float b, float d) {
        this.lightDiffuse[0] = r;
        this.lightDiffuse[1] = g;
        this.lightDiffuse[2] = b;
        this.lightDiffuse[3] = d;
    }

    public float[] getLightPosition() {
        return lightPosition;
    }

    public void setLightPosition(Point3D point) {
        this.lightPosition[0] = point.x;
        this.lightPosition[1] = point.y;
        this.lightPosition[2] = point.z;
        this.lightPosition[3] = 0.0f;
    }
}
