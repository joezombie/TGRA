package is.ru.tgra.cube.helpers;

public class Point3D
{
	public float x;
	public float y;
	public float z;
	
	public Point3D(float xx, float yy, float zz)
	{
		x = xx;
		y = yy;
		z = zz;
	}

    public Point3D(Point3D otherPoint)
    {
        x = otherPoint.x;
        y = otherPoint.y;
        z = otherPoint.z;
    }
	
	public void set(float xx, float yy, float zz)
	{
		x = xx;
		y = yy;
		z = zz;
	}
	
	public void add(Vector3D v)
	{
		x = x + v.x;
		y = y + v.y;
		z = z + v.z;
	}

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
