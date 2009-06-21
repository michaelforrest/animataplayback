package test;

import microkontrol.MicroKontrol;
import microkontrol.controls.Button;
import processing.core.PApplet;
import animata.Animata;
import animata.AnimataPlayback;
import animata.FootController;

public class CrazyLandscape extends PApplet {
	private AnimataPlayback playback;
	private MicroKontrol mk;

	public void setup() {
		size(950, 614, OPENGL);
		MicroKontrol.init(this);
		Animata.startOSC(this);
		playback = new AnimataPlayback(this);

		playback.loadSet("set.xml");
		//playback.debug();
		mk = MicroKontrol.getInstance();
		FootController.getInstance().footSwitches[0].button.listen(Button.PRESSED, this, "doSomething");
	}
	public void doSomething(){
		System.out.println("Pressed the switch!");
	}
	public void draw() {
		if (keyPressed) {
			if (keyCode == DOWN) playback.panCameraY(10);
			if (keyCode == UP) playback.panCameraY(-10);
			if (keyCode == LEFT) playback.panCameraX(-10);
			if (keyCode == RIGHT) playback.panCameraX(10);
			if( key == 'c') System.out.println("HASHS" + playback.camera);
		}
		playback.panCameraX(mk.joystick.getX() * 30);
		playback.zoomCamera(mk.joystick.getY() * 30);
		background(255);

		playback.draw();

	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--bgcolor=#c0c0c0", "TestScene" });

	}

}
