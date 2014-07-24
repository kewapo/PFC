package pruebas.com.gmail.kewapo;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;

public class Vehiculo1 {
	@SuppressWarnings("unused")
	private final int	fordward = 1,
						backward = 2,
						stop = 3;
	private int value;
	private LightSensor S;
	private MotorPort M;
	
	public Vehiculo1() {
		
	}
	
	public void run() {
		S = new LightSensor(SensorPort.S1);
		M = MotorPort.A;
		Button.waitForAnyPress();
		while(!Button.ENTER.isDown()) {
			value = S.getLightValue();
			M.controlMotor(value, fordward);
		}		
		
	}


}
