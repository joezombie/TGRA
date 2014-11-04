package is.ru.tgra.cube.shapes;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import is.ru.tgra.cube.helpers.Point3D;
import is.ru.tgra.cube.shapes.Shape;
import is.ru.tgra.cube.shapes.ShapeAbstract;

public class Sphere extends ShapeAbstract
{
	private int stacks;
	private int slices;
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private int vertexCount;
	private boolean drawLines = false;
    private boolean moving;
    private Point3D P1;
    private Point3D P2;
    private Point3D P3;
    private Point3D P4;
    private float startTime;
    private float endTime;
    private float duration;
	
	
	public Sphere(int i_stacks, int i_slices) {
		stacks = i_stacks;
		slices = i_slices;
		vertexCount = 0;
		float[] array = new float[(stacks)*(slices+1)*6];
		float stackInterval = (float)Math.PI / (float)stacks;
		float sliceInterval = 2.0f*(float)Math.PI / (float)slices;
		float stackAngle, sliceAngle;
		for(int stackCount = 0; stackCount < stacks; stackCount++) {
			stackAngle = stackCount * stackInterval;
			for(int sliceCount = 0; sliceCount < slices+1; sliceCount++) {
				sliceAngle = sliceCount * sliceInterval;
				array[vertexCount*3] = 	 (float)Math.sin(stackAngle) * (float)Math.cos(sliceAngle);
				array[vertexCount*3 + 1] = (float)Math.cos(stackAngle);
				array[vertexCount*3 + 2] = (float)Math.sin(stackAngle) * (float)Math.sin(sliceAngle);

				array[vertexCount*3 + 3] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.cos(sliceAngle);
				array[vertexCount*3 + 4] = (float)Math.cos(stackAngle + stackInterval);
				array[vertexCount*3 + 5] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.sin(sliceAngle);
				
				vertexCount += 2;
			}
		}
		vertexBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
		normalBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
		normalBuffer.put(array);
		normalBuffer.rewind();
	}

    public boolean isMoving() {
        return moving;
    }

    public void setMovement(Point3D P1, Point3D P2, Point3D P3, Point3D P4){
        this.P1 = P1;
        this.P2 = P2;
        this.P3 = P3;
        this.P4 = P4;
    }

    public void startMovement(float runTime, float duration){
        this.startTime = runTime;
        this.endTime = runTime + duration;
        this.duration = duration;
        this.moving = true;
    }

    public void updateMovement(float runTime){
        if(runTime < endTime) {
            float t = (runTime - startTime) / (endTime - startTime);

            position.x = (1.0f - t) * (1.0f - t) * (1.0f - t) * P1.x
                    + 3 * (1.0f - t) * (1.0f - t) * t * P2.x
                    + 3 * (1.0f - t) * t * t * P3.x
                    + t * t * t * P4.x;

            position.y = (1.0f - t) * (1.0f - t) * (1.0f - t) * P1.y
                    + 3 * (1.0f - t) * (1.0f - t) * t * P2.y
                    + 3 * (1.0f - t) * t * t * P3.y
                    + t * t * t * P4.y;

            position.z = (1.0f - t) * (1.0f - t) * (1.0f - t) * P1.z
                    + 3 * (1.0f - t) * (1.0f - t) * t * P2.z
                    + 3 * (1.0f - t) * t * t * P3.z
                    + t * t * t * P4.z;
        } else {
            setMovement(P4, P3, P2, P1);
            startMovement(runTime, duration);
        }
    }

    public void draw() {
        Gdx.gl11.glTranslatef(position.x, position.y, position.z);
        Gdx.gl11.glScalef(size, size, size);
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		Gdx.gl11.glNormalPointer(GL11.GL_FLOAT, 0, normalBuffer);
		for(int i = 0; i < vertexCount; i += (slices+1)*2) {
			if(this.drawLines)
				Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, i, (slices+1)*2);
			else
				Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, i, (slices+1)*2);
		}
        Gdx.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
	}
	
	public void toggleDrawLines(){
		this.drawLines = this.drawLines ? false : true;
	}

    @Override
    public boolean collides(Shape shape) {
        boolean result = false;
        float deltaXCubed = position.x - shape.getPosition().x;
        deltaXCubed *= deltaXCubed;
        deltaXCubed *= deltaXCubed;
        float deltaYCubed = position.y - shape.getPosition().y;
        deltaYCubed *= deltaYCubed;
        deltaYCubed *= deltaYCubed;
        float deltaZCubed = position.z - shape.getPosition().z;
        deltaZCubed *= deltaZCubed;
        deltaZCubed *= deltaZCubed;

        float radiusSumCubed = radius + shape.getRadius();
        radiusSumCubed *= radiusSumCubed;
        radiusSumCubed *= radiusSumCubed;

        if(deltaXCubed + deltaYCubed + deltaZCubed <= radiusSumCubed){
            result = true;
        }

        return result;
    }
}
