package animata;

import java.util.Observable;

import javax.sound.midi.*;


public class Clock extends Observable implements Receiver {

	public static final Integer TICK = 0;
	public static final Integer CROTCHET = 1;
	public static final Integer BAR = 2;
	private MidiDevice in;
	private int tick = 0;

	public Clock(MidiDevice device) {
		in = device;
		if (!in.isOpen()) try {
			in.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Transmitter transmitter;
		try {
			transmitter = in.getTransmitter();
			transmitter.setReceiver(this);
			System.out.println("successfully created clock");
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}



	public void close() {
		in.close();
	}

	public void send(MidiMessage message, long timestamp) {
		if(message.getStatus() == ShortMessage.START) tick = 0;
		if(message.getStatus() != ShortMessage.TIMING_CLOCK) return;
		setChanged();
		notifyObservers(TICK);

		if(tick == 0){
			setChanged();
			notifyObservers(BAR);
		}

		if(tick % 24 == 0){
			setChanged();
			notifyObservers(CROTCHET);
		}

		tick ++;
		if(tick>=96) {
			tick = 0;
		}
	}


}
