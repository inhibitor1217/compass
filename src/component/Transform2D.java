package component;

import org.lwjgl.util.vector.*;

public class Transform2D extends Component {

	private Vector2f position;
	private float rotation;
	private Vector2f scale;
	
	public Transform2D() {
		this(new Vector2f(0, 0), 0.0f, new Vector2f(1, 1));
	}
	
	public Transform2D(Vector2f position) {
		this(position, 0.0f, new Vector2f(1, 1));
	}

	public Transform2D(Vector2f position, float rotation, Vector2f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Vector2f getPosition() {
		return this.position;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public float getScale() {
		return this.getScale();
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		this.position = new Vector2f(x, y);
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setScale(float scale) {
		this.scale = new Vector2f(scale, scale);
	}
	
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public Transform2D translate(Vector2f translateVector) {
		this.position.x += translateVector.x;
		this.position.y += translateVector.y;
		return this;
	}
	
	public Transform2D translate(float dx, float dy) {
		this.position.x += dx;
		this.position.y += dy;
		return this;
	}
	
	public Transform2D rotate(float rotateAngle) {
		this.rotation += rotateAngle;
		return this;
	}
	
	public Transform2D scale(float scale) {
		this.scale.x *= scale;
		this.scale.y *= scale;
		return this;
	}
	
	public Transform2D scale(Vector2f scaleVector) {
		this.scale.x *= scaleVector.x;
		this.scale.y *= scaleVector.y;
		return this;
	}
	
	public Transform2D scale(float sx, float sy) {
		this.scale.x *= sx;
		this.scale.y *= sy;
		return this;
	}
	
	public Vector2f getUp() {
		return new Vector2f((float) Math.sin(rotation), -(float) Math.cos(rotation));
	}
	
	public Vector2f getRight() {
		return new Vector2f((float) Math.cos(rotation), (float) Math.sin(rotation));
	}
	
	public Matrix4f getTransformationMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate(rotation, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f getProjectionMatrix(float screenWidth, float screenHeight) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		float aspectRatio = screenWidth / screenHeight;
		Matrix4f.scale(new Vector3f(1.0f / scale.x, aspectRatio / scale.y, 1.0f), matrix, matrix);
		Matrix4f.rotate(-rotation, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.translate(new Vector2f(-position.x, -position.y), matrix, matrix);
		return matrix;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
