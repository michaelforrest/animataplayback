package animata;

import rwmidi.Controller;
import rwmidi.MidiInput;
import rwmidi.RWMidi;
import microkontrol.controls.Button;

public class FootController {
	public class FootSwitch {
		private int cc;
		public FootSwitch(int cc) {
			this.cc = cc;
			// all on channel 16
			in.plug(this, "controllerChangeReceived",15);
		}
		public void controllerChangeReceived(Controller controller){
			if(controller.getCC() == cc) button.press();
		}
		public Button button = new Button();

	}

	private static FootController instance;
	public static FootSwitch[] footSwitches;
	protected MidiInput in;
	public FootController(String inputName) {
		in = RWMidi.getInputDevice(inputName).createInput();
		int[] ccs = {115,114,116,101,100};
		footSwitches = new FootSwitch[ccs.length];
		for (int i = 0; i < ccs.length; i++) {
			int cc = ccs[i];
			footSwitches[i] = new FootSwitch(cc);

		}
	}

	public static FootController getInstance(){
		if(instance == null) instance = new FootController("MIDI IN <MIn:3> KORG INC.");
		return instance;
	}
}
