package test;

import microkontrol.MicroKontrol;
import animata.Animata;
import animata.AnimataPlayback;
import processing.core.PApplet;
import processing.opengl.*;
public class TestBasicMetrics extends PApplet{

	AnimataPlayback playback;
	public void setup() {
		size(950, 614, OPENGL);
		MicroKontrol.init(this);
		Animata.startOSC(this);
		playback = new AnimataPlayback(this);
		playback.debug();
		playback.loadSet("set.xml");
	}

	public void draw() {
	  background(255);
	  playback.draw();
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--bgcolor=#c0c0c0", "TestBasicMetrics" });
	}

}
