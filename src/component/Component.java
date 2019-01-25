package component;

import object.GameObject;

public abstract class Component {

	private GameObject gameObject;
	
	public abstract void start();
	public abstract void update();
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
	public void attachToGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public void detach() {
		this.gameObject = null;
	}
	
}
