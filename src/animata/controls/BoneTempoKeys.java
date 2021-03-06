package animata.controls;

import processing.xml.XMLElement;
import rwmidi.MidiInput;
import rwmidi.Note;
import animata.Controller;
import animata.NoteParser;
import animata.NoteParser.BadNoteFormatException;

public class BoneTempoKeys extends Control {

	private Float tempo;
	private String bone;
	private int keysPressed = 0;
	private int low;
	private int high;

	public BoneTempoKeys(MidiInput in, int low, int high, String bone, float tempo, int channel) {
		super(channel, in);
		this.low = low;
		this.high = high;
		this.bone = bone;
		this.tempo = tempo;
//		Controller.getInstance().setBoneTempo(scene,bone, 0f);
	}

	public BoneTempoKeys(XMLElement element, MidiInput in) {
		super(element, in);
		low = NoteParser.getNote(element.getStringAttribute("low", "1"));
		high = NoteParser.getNote(element.getStringAttribute("high", "100"));
		bone = element.getStringAttribute("bone");
		tempo = element.getFloatAttribute("tempo");
//		Controller.getInstance().setBoneTempo(scene,bone, 0f);
	}

	public void noteOnReceived(Note n) {
		if (n.getChannel() != channel) return;
		int pitch = n.getPitch();
		if (pitch < low) return;
		if (pitch > high) return;
		Float newTempo = 1f;
		keysPressed++;
		newTempo = tempo;

		Controller.getInstance().setBoneTempo(scene,bone, newTempo);
	}

	public void noteOffReceived(Note n) {
		if (n.getChannel() != channel) return;
		int pitch = n.getPitch();
		if (pitch < low) return;
		if (pitch > high) return;
		keysPressed--;
		float newTempo = 1f;
		if (keysPressed == 0) newTempo = 0f;
		Controller.getInstance().setBoneTempo(scene,bone, newTempo);
	}
}
