package animata.controls;

import processing.xml.XMLElement;
import rwmidi.MidiInput;

public class ControlFactory {

	public static Control createControl(XMLElement element, MidiInput in) {
		String name = element.getName();
		if(name.equals("notebone")) return new NoteBone(element, in);
		if(name.equals("layeralpha")) return new LayerFader(element,in);
//		if(name.equals("faderbone")) return new FaderBone(element);
//		if(name.equals("freqbone")) return new FreqBone(element);
		if(name.equals("noterangebone")) return new NoteRangeBone(element,in);
		if(name.equals("bonetempokeys")) return new BoneTempoKeys(element,in);
		if(name.equals("bonetempokeyranges")) return new BoneTempoKeyRanges(element, in);
		if(name.equals("camera")) return new CameraPosition(element, in);
		if(name.equals("camerashake")) return new CameraShake(element,in);
		if(name.equals("layertoggle")) return new LayerToggle(element, in);
		if(name.equals("note_range_angle")) return new NoteRangeAngle(element,in);
		if(name.equals("note_range_scale")) return new NoteRangeScale(element,in);
		if(name.equals("scale")) return new LayerScaler(element,in);
		if(name.equals("rotate")) return new LayerRotator(element,in);

		return new Control(element, in);

	}

}
