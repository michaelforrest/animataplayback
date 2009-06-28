package animata.controls;

import animata.model.Layer;
import processing.core.PApplet;
import processing.xml.XMLElement;
import rwmidi.Controller;
import rwmidi.MidiInput;

public class LayerRotator extends Control {

	private String layer;
	private int cc;
	private float min;
	private float max;
	private float range;

	public LayerRotator(XMLElement element, MidiInput in) {
		super(element, in);
		layer = element.getStringAttribute("layer");
		cc = element.getIntAttribute("cc");
		min = PApplet.radians(element.getFloatAttribute("min"));
		max = PApplet.radians(element.getFloatAttribute("max"));
		range = max - min;
	}
	public void controllerChangeReceived(Controller controller){
		if(controller.getChannel() != channel) return;
		if(controller.getCC() != cc) return;
		float value = range * (controller.getValue() / 127f) + min;
		Layer.setRotation(scene,layer, value);
	}


}
