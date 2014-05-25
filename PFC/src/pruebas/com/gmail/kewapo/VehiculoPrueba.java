package pruebas.com.gmail.kewapo;

import lejos.nxt.BasicMotorPort;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;

public class VehiculoPrueba {
	private MotorPort MA;
	private MotorPort MB;
	
	public static void main(String[] args) {
		LCD.drawString("ENTER to stop", 0, 0);
		VehiculoPrueba v1 = new VehiculoPrueba();
		while (!Button.ENTER.isDown()) {
			v1.mover(50);
		}
	}
	
	VehiculoPrueba() {
		MA = MotorPort.A;
		MB = MotorPort.B;
	}

	public void mover(int fuerza) {
		MA.controlMotor(fuerza, BasicMotorPort.FORWARD);
		MB.controlMotor(fuerza, BasicMotorPort.FORWARD);
	}
}
