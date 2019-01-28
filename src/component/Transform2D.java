package component;

import java.util.ArrayList;

import org.lwjgl.util.vector.*;

public class Transform2D extends Component {

	// These are local transformation values
	private Vector2f position;
	private float rotation;
	private Vector2f scale;
	
	private Transform2D parent;
	private ArrayList<Transform2D> children;
	
	private Matrix4f localTransformationMatrix;
	private Matrix4f worldTransformationMatrix;
	private Matrix4f projectionMatrix;
	
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
		
		this.parent = null;
		this.children = new ArrayList<Transform2D>();
		
		localTransformationMatrix = new Matrix4f();
		worldTransformationMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
	}
	
	public Vector2f getPosition() {
		if (parent == null)
			return new Vector2f(this.position);
		else {
			Vector2f parentPos = parent.getPosition();
			return Vector2f.add(parentPos, this.position, parentPos);
		}
	}
	
	public float getRotation() {
		if (parent == null)
			return this.rotation;
		else
			return parent.getRotation() + this.rotation;
	}
	
	public Vector2f getScale() {
		return new Vector2f(this.scale);
	}
	
	public void setPosition(Vector2f position) {
		translate(Vector2f.sub(position, getPosition(), null));
	}
	
	public void setPosition(float x, float y) {
		Vector2f newPosition = new Vector2f(x, y);
		translate(Vector2f.sub(newPosition, getPosition(), newPosition));
	}
	
	public void setLocalPosition(Vector2f position) {
		translate(position.x - this.position.x, position.y - this.position.y);
	}
	
	public void setLocalPosition(float x, float y) {
		translate(x - this.position.x, y - this.position.y);
	}
	
	public void setRotation(float rotation) {
		rotate(rotation - getRotation());
	}
	
	public void setLocalRotation(float rotation) {
		rotate(rotation);
	}
	
	public void setScale(float scale) {
		this.scale = new Vector2f(scale, scale);
	}
	
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public Transform2D translate(Vector2f translateVector) {
		return translate(translateVector.x, translateVector.y);
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
		float rot = getRotation();
		return new Vector2f((float) Math.sin(rot), -(float) Math.cos(rot));
	}
	
	public Vector2f getRight() {
		float rot = getRotation();
		return new Vector2f((float) Math.cos(rot), (float) Math.sin(rot));
	}
	
	public void setParent(Transform2D parentTransform) {
		if (parent != null) {
			detachTransforms(parent, this);
		}
		
		if (parentTransform != null) {
			attachTransforms(parentTransform, this);
		}
	}
	
	public void addChild(Transform2D childTransform) {
		if (childTransform == null)
			throw new IllegalStateException("Child Transform2D is null");
		
		attachTransforms(this, childTransform);
	}
	
	public void removeChild(Transform2D childTransform) {
		if (childTransform == null)
			throw new IllegalStateException("Child Transform2D is null");
		
		detachTransforms(this, childTransform);
	}
	
	public void detachAllChildren() {
		for(Transform2D child: children) {
			detachTransforms(this, child);
		}
	}
	
	private static void attachTransforms(Transform2D parent, Transform2D child) {
		if (parent == null || child == null)
			throw new IllegalStateException("Given transform is null");
		if (child.parent == parent) {
			if (parent.children.contains(child))
				return;
			else
				throw new IllegalStateException("Child is parented but parent does not own the child");
		}
		else {
			if (parent.children.contains(child))
				throw new IllegalStateException("Parent owns the child but child is not parented");
			else {
				parent.children.add(child);
				child.parent = parent;
				
				Matrix4f relativeTransform = calculateRelativeTransformationMatrix(child, parent);
				child.recalculateFromTransformationMatrix(relativeTransform);
			}
		}
	}
	
	private static void detachTransforms(Transform2D parent, Transform2D child) {
		if (parent == null || child == null)
			throw new IllegalStateException("Given transform is null");
		if (child.parent != parent)
			throw new IllegalStateException("Child is not parented to the parent");
		if (!parent.children.contains(child))
			throw new IllegalStateException("Parent does not own the child");
		
		parent.children.remove(child);
		child.parent = null;
		
		child.recalculateFromTransformationMatrix(child.worldTransformationMatrix);
	}
	
	public Matrix4f getTransformationMatrix() {
		if (parent == null)
			return worldTransformationMatrix = getLocalTransformationMatrix();
		else
			return Matrix4f.mul(parent.getTransformationMatrix(), getLocalTransformationMatrix(), worldTransformationMatrix);
	}
	
	public Matrix4f getLocalTransformationMatrix() {
		return calculateLocalTransformationMatrix(this.position, this.rotation, this.scale, this.localTransformationMatrix);
	}
	
	private static Matrix4f calculateLocalTransformationMatrix(Vector2f position, float rotation, Vector2f scale, Matrix4f matrix) {
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate(rotation, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1), matrix, matrix);
		return matrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return calculateProjectionMatrix(this.position, this.rotation, this.scale, this.projectionMatrix);
	}
	
	private static Matrix4f calculateProjectionMatrix(Vector2f position, float rotation, Vector2f scale, Matrix4f matrix) {
		matrix.setIdentity();
		Matrix4f.scale(new Vector3f(1.0f / scale.x, 1.0f / scale.y, 1.0f), matrix, matrix);
		Matrix4f.rotate(-rotation, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.translate(new Vector2f(-position.x, -position.y), matrix, matrix);
		return matrix;
	}
	
	private void recalculateFromTransformationMatrix(Matrix4f transformation) {
		this.position = getPositionFromTransformationMatrix(transformation);
		this.rotation = getRotationFromTransformationMatrix(transformation);
		this.scale = getScaleFromTransformationMatrix(transformation);
	}
	
	private static Matrix4f calculateRelativeTransformationMatrix(Transform2D src, Transform2D other) {
		return Matrix4f.mul(Matrix4f.invert(other.getTransformationMatrix(), null), src.getTransformationMatrix(), null);
	}
	
	private static Vector2f getPositionFromTransformationMatrix(Matrix4f transformation) {
		return new Vector2f(transformation.m30, transformation.m31);
	}
	
	private static float getRotationFromTransformationMatrix(Matrix4f transformation) {
		return (float) Math.atan2(transformation.m01, transformation.m00);
	}
	
	private static Vector2f getScaleFromTransformationMatrix(Matrix4f transformation) {
		return new Vector2f(
				(float) Math.sqrt(transformation.m00 * transformation.m00 + transformation.m01 * transformation.m01),
				(float) Math.sqrt(transformation.m10 * transformation.m10 + transformation.m11 * transformation.m11)
			);
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
