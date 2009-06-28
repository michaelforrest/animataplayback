package animata.controls;

import java.util.Observable;
import java.util.Observer;

import processing.xml.XMLElement;
import rwmidi.MidiInput;
import rwmidi.Note;
import animata.Animator;
import animata.NoteParser;
import animata.model.Layer;

public class NoteRangeAngle extends Control implements Observer{

	private static final int FRAMES_TO_USE = 4;
	private String layer;
	private Integer lowNote;
	private Integer highNote;
	private float lowAngle;
	private float highAngle;
	private Animator animator;
	private float angleRange;
	private float noteRange;
	public NoteRangeAngle(XMLElement element, MidiInput in) {
		super(element, in);
		layer = element.getStringAttribute("layer");

		XMLElement notes = element.getChild("notes");
		lowNote = NoteParser.getNote(notes.getStringAttribute("low", "1"));
		highNote = NoteParser.getNote(notes.getStringAttribute("high", "100"));

		XMLElement angles = element.getChild("angles");
		lowAngle = (float) Math.PI * angles.getFloatAttribute("low") / 180f;
		highAngle = (float) Math.PI * angles.getFloatAttribute("high") / 180f;

		angleRange = (float) highAngle - lowAngle;
		noteRange = (float) highNote - lowNote;
		animator = new animata.Animator(lowAngle, this);



	}
	public void noteOnReceived(Note n) {
		if (n.getChannel() != channel) return;
		int pitch = n.getPitch();
		if (pitch < lowNote) return;
		if (pitch > highNote) return;
		float proportion = ((float) ((pitch - lowNote)) / noteRange);
		float angle = lowAngle + (proportion * angleRange);
		animator.set(angle, FRAMES_TO_USE);
	}
	public void update(Observable o, Object arg) {
		Layer.setRotation(scene, layer, animator.currentValue);

	}
}
