package animata;

import java.io.File;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice.Info;

import processing.core.PApplet;
import processing.xml.XMLElement;
import rwmidi.MidiInput;
import rwmidi.MidiInputDevice;
import rwmidi.RWMidi;
import animata.controls.ClockBobber;
import animata.controls.LayerToggle;
import animata.model.Layer;
import animata.views.LayerView;

public class AnimataPlayback {

	public static final float timeDivision = 42f;
	public static float gravity = 0;
	private static boolean debug;
	private  PApplet applet;
	private Layer root;
	private LayerView layersView;
	private Controller controller;
	public Camera camera;
	private MidiInput in;
	public static Clock clock;

	public AnimataPlayback(PApplet applet, String nmtFile){
		setup(applet,"IAC Bus 1","IAC Bus 2");
		addScene(nmtFile);
	}
	public AnimataPlayback(PApplet applet){
		setup(applet,"IAC Bus 1", "IAC Bus 2");
	}
	public AnimataPlayback(PApplet applet, String midiInPortName, String clockInPortName){
		setup(applet, midiInPortName, clockInPortName);
	}
	private void setup(PApplet applet, String midiInPortName, String clockInPortName) {
		this.applet = applet;
		Animator.init(applet);
		LayerToggle.init(applet);
		camera = new Camera(applet);

		root = new Layer();
		Info midiDeviceInfo = getMidiDeviceInfo(midiInPortName);
		String inputName = midiDeviceInfo.getName() + " " + midiDeviceInfo.getVendor(); //ooooohhhhh you bad people :-/ (copied from within the rwmidi getName() method)
		MidiInputDevice inputDevice = RWMidi.getInputDevice(inputName);
		in = inputDevice.createInput();
		clock = new Clock(getMidiDevice(clockInPortName));
		new ClockBobber(clock);
		new RandomCameraPanner(clock, this);
		Controller.init(applet,root,this);
		controller = Controller.getInstance();
		new GrimoniumInput(applet,root,controller,7111);
	}

	private Info getMidiDeviceInfo(String deviceName) {
		javax.sound.midi.MidiDevice.Info infos[] = MidiSystem.getMidiDeviceInfo();
		for (javax.sound.midi.MidiDevice.Info info : infos) {
			System.out.println("info:  " + info.getName());
			if (info.getName().matches(deviceName + ".*")) {
				System.out.println("found device matching " + deviceName);
				return info;
			}
		}
		System.out.println("Error - couldn't find " + deviceName);
		return null;
	}
	private MidiDevice getMidiDevice(String deviceName) {
		try {
			return MidiSystem.getMidiDevice(getMidiDeviceInfo(deviceName));
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			return null;
		}
	}


	public void loadSet(String xml){

		XMLElement scene = new XMLElement(applet,xml);
		XMLElement[] layers = scene.getChildren("layer");
		for (int i = 0; i < layers.length; i++) {
			XMLElement element = layers[i];
			Layer layer = addScene(element.getStringAttribute("nmt"));
			layer.visible = true;
			layer.alpha = 1;
			new Scene(element,in,applet,layer);
		}
		layersView = new LayerView(root, applet);
	}

	public Layer addScene(String xml){
		return addScene(xml, root);
	}
	public Layer addScene(String xml, Layer parent){
		String folder = new File(xml).getParent();
		if(folder == null) folder = ".";
		XMLElement element = new XMLElement(applet, xml);
		return parent.addLayer(folder, element);//element.getChildren("layer"), folder);

	}
	public void draw(){
		applet.camera(camera.x, camera.y, camera.z, camera.targetX, camera.targetY,camera.targetZ, 0f, 1f, 0f);
		root.simulate();
		applet.textureMode(PApplet.NORMAL);
		applet.noStroke();
		applet.fill(0,0);
		applet.translate(applet.width/2, applet.height/2);
		layersView.draw();
	}
	public void zoomCamera(float delta) {
		camera.zoomBy(-delta);
	}
	public void panCameraX(float delta) {
		camera.panXBy(delta);
	}
	public void panCameraY(float delta){
		camera.panYBy(delta);
	}
	public void debug() {
		debug = true;

	}
	public static boolean debugging() {
		return debug;
	}

}