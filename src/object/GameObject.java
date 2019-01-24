package object;

import component.Transform2D;
import model.*;

public class GameObject {

	private Transform2D transform;
	private TexturedModel model;
	
	public GameObject() {
		this(new Transform2D(), null);
	}
	
	public GameObject(Transform2D transform, TexturedModel model) {
		this.transform = transform;
		this.model = model;
	}
	
	public Transform2D getTransform() {
		return this.transform;
	}
	
	public TexturedModel getModel() {
		return this.model;
	}
	
}
