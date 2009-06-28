package animata.controls;

import animata.model.Layer;
import processing.xml.XMLElement;
import rwmidi.Controller;
import rwmidi.MidiInput;

public class LayerScaler extends Control{

	private int cc;
	private float min;
	private float max;
	private float range;
	private String layer;

	public LayerScaler(XMLElement element, MidiInput in) {
		super(element,in);
		layer = element.getStringAttribute("layer");
		cc = element.getIntAttribute("cc");
		min = element.getFloatAttribute("min");
		max = element.getFloatAttribute("max");
		range = max - min;
	}

	public void controllerChangeReceived(Controller controller){
		if(controller.getChannel() != channel) return;
		if(controller.getCC() != cc) return;
		float value = range * (controller.getValue() / 127f) + min;
		Layer.setScale(scene, layer, value);
	}

}
