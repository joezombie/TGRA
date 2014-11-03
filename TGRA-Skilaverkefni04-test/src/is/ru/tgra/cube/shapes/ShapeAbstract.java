package is.ru.tgra.cube.shapes;


import is.ru.tgra.cube.helpers.ColorRGB;
import is.ru.tgra.cube.helpers.Point3D;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public abstract class ShapeAbstract implements Shape {
    public Point3D position;
    public float size;
    public float radius;
    public ColorRGB diffuse;
    public ColorRGB specular;
    public float shininess;
    public ColorRGB emission;

    @Override
    public void setSize(float size) {
        this.size = size;
        radius = size / 2;
    }

    @Override
    public void setPosition(Point3D position) {
        this.position = position;
    }


    @Override
    public float getSize() {
        return size;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    @Override
    public float getRadius(){ return radius; }

    @Override
    public void setRadius(float radius) {this.radius = radius;}

    @Override
    public void setDiffuse(ColorRGB diffuse) {
        this.diffuse = diffuse;
    }

    @Override
    public ColorRGB getDiffuse() {
        return diffuse;
    }

    @Override
    public void setSpecular(ColorRGB specular) {
        this.specular = specular;
    }

    @Override
    public ColorRGB getSpecular() {
        return specular;
    }

    @Override
    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    @Override
    public float getShininess() {
        return shininess;
    }

    @Override
    public ColorRGB getEmission() {
        return emission;
    }

    @Override
    public void setEmission(ColorRGB emission) {
        this.emission = emission;
    }

    @Override
    public String toString() {
        return "ShapeAbstract{" +
                "position=" + position +
                ", size=" + size +
                ", radius=" + radius +
                ", diffuse=" + diffuse +
                ", specular=" + specular +
                ", shininess=" + shininess +
                ", emission=" + emission +
                '}';
    }
}
