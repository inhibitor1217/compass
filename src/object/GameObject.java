package object;

import java.util.ArrayList;
import java.util.List;

import component.*;
import model.*;

public class GameObject {

	private static ArrayList<GameObject> allGameObjects = new ArrayList<GameObject>();
	
	private Transform2D transform;
	private TexturedModel model;
	
	private ArrayList<Component> components;

	public GameObject() {
		this(new Transform2D(), null);
	}
	
	public GameObject(Transform2D transform, TexturedModel model) {
		this.transform = transform;
		this.model = model;
		
		components = new ArrayList<Component>();
		addComponent(this.transform);
		
		allGameObjects.add(this);
	}
	
	public Transform2D getTransform() {
		return this.transform;
	}
	
	public TexturedModel getModel() {
		return this.model;
	}
	
	public void destroy() {
		allGameObjects.remove(this);
	}
	
	public void addComponent(Component component) {
		if (component != null) {
			this.components.add(component);
			component.attachToGameObject(this);
		}
	}
	
	public void removeComponent(Component component) {
		if (component != null && this.components.contains(component)) {
			this.components.remove(component);
			component.detach();
		}
	}
	
	public List<Component> getComponents() {
		return components;
	}

	public static List<GameObject> getAllGameObjects() {
		return allGameObjects;
	}
	
}
