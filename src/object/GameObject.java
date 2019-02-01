package object;

import java.util.ArrayList;
import java.util.List;

import component.*;
import model.*;

public class GameObject {

	private static ArrayList<GameObject> allGameObjects = new ArrayList<GameObject>();
	
	private Transform2D transform;
	private TexturedModel texturedModel;
	
	private ArrayList<Component> components;

	public GameObject() {
		this(new Transform2D());
	}
	
	public GameObject(Transform2D transform) {
		this.transform = transform;
		
		components = new ArrayList<Component>();
		addComponent(this.transform);
		
		allGameObjects.add(this);
	}
	
	public GameObject(Transform2D transform, TexturedModel model) {
		this(transform);
		this.texturedModel = model;
	}
	
	public Transform2D getTransform() {
		return this.transform;
	}
	
	public TexturedModel getTexturedModel() {
		return this.texturedModel;
	}
	
	public void destroy() {
		allGameObjects.remove(this);
	}
	
	public void addComponent(Component component) {
		if (component != null) {
			if (getComponent(component.getClass()) != null)
				throw new IllegalStateException("Gameobject already contains the component of the same type");
			this.components.add(component);
			component.attachToGameObject(this);
		}
		else
			throw new IllegalStateException("Component is null");
	}
	
	public void removeComponent(Component component) {
		if (component != null && this.components.contains(component)) {
			this.components.remove(component);
			component.detach();
		}
		else
			throw new IllegalStateException("Component is null");
	}
	
	public List<Component> getComponents() {
		return components;
	}
	
	public Component getComponent(Class<? extends Component> cls) {
		for(Component component: this.components) {
			if (component.getClass() == cls)
				return component;
		}
		return null;
	}

	public static List<GameObject> getAllGameObjects() {
		return allGameObjects;
	}
	
}
