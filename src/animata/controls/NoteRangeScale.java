package animata.controls;

import java.util.Observable;
import java.util.Observer;

import processing.xml.XMLElement;
import rwmidi.MidiInput;
import rwmidi.Note;
import animata.Animator;
import animata.NoteParser;
import animata.model.Layer;

public class NoteRangeScale extends Control implements Observer {

	private static final int ANIMATION_FRAMES_TO_USE = 4;
	private static final int OUT_FRAMES = 15;
	private String layer;
	private Integer high;
	private Integer low;
	private float off;
	private float on;
	private Animator animator;
	public NoteRangeScale(XMLElement element, MidiInput in) {
		super(element, in);
		low = NoteParser.getNote(element.getStringAttribute("low", "1"));
		high = NoteParser.getNote(element.getStringAttribute("high", "100"));
		off = element.getFloatAttribute("off", 1);
		on = element.getFloatAttribute("on", 1.5f);
		layer = element.getStringAttribute("layer");
		animator = new animata.Animator(off, this);
	}
	public void noteOnReceived(Note n) {
		if (n.getChannel() != channel) return;
		int pitch = n.getPitch();
		if (pitch < low) return;
		if (pitch > high) return;
		animator.set(on, ANIMATION_FRAMES_TO_USE);
	}
	public void noteOffReceived(Note n) {
		if (n.getChannel() != channel) return;
		int pitch = n.getPitch();
		if (pitch < low) return;
		if (pitch > high) return;
		animator.set(off, OUT_FRAMES);
	}
	public void update(Observable o, Object arg) {
		Layer.setScale(scene, layer, animator.currentValue);
	}

}
