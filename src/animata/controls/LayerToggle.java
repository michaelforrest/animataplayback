package animata.controls;

import java.awt.event.KeyEvent;

import animata.model.Layer;

import processing.core.PApplet;
import processing.xml.XMLElement;
import rwmidi.MidiInput;

public class LayerToggle extends Control {

	private static PApplet applet;

	private String layer;

	private char toggle;

	public LayerToggle(XMLElement element, MidiInput in) {
		super(element,in);
		toggle = element.getStringAttribute("key").charAt(0);
		layer =  element.getStringAttribute("layer");
		applet.registerKeyEvent(this);
		Layer.setVisibility(layer, false); // TODO confirm this is desirable default behaviour
	}
	public void keyEvent(KeyEvent key){
		if(key.getKeyChar() == toggle && key.getID() == KeyEvent.KEY_PRESSED ){
			System.out.println("toggling " + layer);
			Layer.toggle(layer);
		}
	}
	public static void init(PApplet theApplet) {
		applet = theApplet;

	}

}
