package animata.controls;

import microkontrol.MicroKontrol;
import microkontrol.controls.FaderListener;
import processing.xml.XMLElement;
import rwmidi.MidiInput;
import animata.model.Layer;

public class LayerFader extends Control implements FaderListener {

	private String layer;

	public LayerFader(XMLElement element, MidiInput in) {
		super(element, in);
		layer = element.getStringAttribute("layer");
		MicroKontrol.getInstance().faders[element.getIntAttribute("fader")].listen(this);
		Layer.setAlpha(layer,0f);
	}

	public void moved(Float value) {
		Layer.setAlpha(layer,value);

	}

}
