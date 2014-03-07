package pruebas.com.gmail.kewapo;

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;


public class Pruebas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NXTMotor M1 = new NXTMotor(MotorPort.A);
		NXTMotor M2 = new NXTMotor(MotorPort.B);
		System.out.println("Presiona un boton para comenzar...");
		Button.waitForAnyPress();
		M1.setPower(30);
		M2.setPower(60);
		M1.forward();
		M2.forward();
		Button.waitForAnyPress();
	}

}
