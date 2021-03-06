package animata;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.xml.XMLElement;
import rwmidi.MidiInput;
import animata.controls.Control;
import animata.controls.ControlFactory;
import animata.model.Layer;
import animata.model.Skeleton.Bone;

public class Scene implements Observer {

	private static final float OFF_CAMERA = -3000;
	private Control[] controls;
	private MidiInput in;
	private char toggle;
	private final PApplet applet;
	private String id;
	private Layer layer;
	private Animator animator;
	public ArrayList<Bone> bones;
	public ArrayList<Layer> layers;

	public Scene(XMLElement element, MidiInput in, PApplet applet, Layer layer) {
		this.in = in;
		this.applet = applet;
		this.id = element.getStringAttribute("id");
		this.layer = layer;
		toggle = element.getStringAttribute("toggle","�").charAt(0);
		if(toggle != '�') setupToggle();
		addControls(element.getChildren());
		animator = new Animator(OFF_CAMERA,this);

		bones = new ArrayList<Bone>();
		layer.getAllBones(bones);

		layers = new ArrayList<Layer>();
		layer.getAllLayers(layers);

	}

	private void setupToggle() {
		applet.registerKeyEvent(this);
		layer.y = OFF_CAMERA;
	}
	public void keyEvent(KeyEvent key){
		if(key.getKeyChar() == toggle && key.getID() == KeyEvent.KEY_PRESSED ){
			System.out.println("revealing " + id);
			animator.set(animator.currentValue < 0 ? 0 : OFF_CAMERA, 50);
		}
	}
	private void addControls(XMLElement[] elements) {
		controls = new Control[elements.length];
		for (int i = 0; i < elements.length; i++) {
			XMLElement element = elements[i];
			Control control = ControlFactory.createControl(element, in);
			controls[i] = control;
			control.scene = this;
		}
	}

	public void update(Observable o, Object arg) {
		layer.y = animator.currentValue;
		if(animator.currentValue == 0) Controller.getInstance().shakeCamera();
	}
	public ArrayList<Bone> findBones(String name) {
		ArrayList<Bone> result = new ArrayList<Bone>();
		for (Bone bone : bones) {
			if(bone.name.equals(name)) result.add(bone);
		}
		if(result.size() == 0) System.out.println("sorry, couldn't find a bone called " + name);
		return result;
	}

}
