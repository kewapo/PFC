package com.terra.luiscaballero;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;

/**
 * Clase responsable del control del robot. Recibe los comandos
 * del PC desde la clase PCController.
 * 
 * @author Luis Javier Gonzalez Caballero
 * @version 2014_01_01
 *
 */
public class NXTController {

	//Atributos
	private MotorPort MA;
	private MotorPort MB;
	private LightSensor S1;
	private LightSensor S2;
	
	private Command code = Command.NOTHING;
	private BluetoothComms coms;
	
	private int cuenta = 0, i = 0, j = 0;
	
	public static void main(String[] args) {
		new NXTController();	}

	//Constructor
	public NXTController() {
		coms = new BluetoothComms(this);
		coms.setDaemon(true);
		MA = MotorPort.A;
		MB = MotorPort.B;
		S1 = new LightSensor(SensorPort.S1, false);
		S2 = new LightSensor(SensorPort.S2, false);
		coms.connect();
		coms.start();
		while(!Button.ENTER.isDown()){
			executeCommand();			
		}
	}
	
	/**
	 * Ejecuta el comando que recibe del PC
	 * 
	 * @param code2 Comando a ejecutar
	 * @param value1 Valor a pasar al comando, — cero
	 */
	public void executeCommand() {
		int S, Sa, Sb;
		Sa = S1.getLightValue();
		Sb = S2.getLightValue();
		coms.send(Sa);
		coms.send(Sb);
		LCD.drawString("send " + Sa + " - " + Sb, 0, 2);
		if (cuenta++ == 0) {
			i = MA.getTachoCount();
			j = MB.getTachoCount();
			LCD.drawString("Motores: " + (i - j), 0, 3);
			MA.resetTachoCount();
			MB.resetTachoCount();
		}
		coms.send(j-i);
		cuenta %= 10;
		switch (code) {
		case STOP:
			LCD.drawString("Stop received", 0, 1);
			Sa = 50 + Sa / 2; //No incluir en la documentaci—n. Son los roces.
			Sb = 50 + Sb / 2; //No incluir en la documentaci—n. Son los roces.
			MA.controlMotor(0, BasicMotorPort.STOP);
			MB.controlMotor(0, BasicMotorPort.STOP);;
			code = Command.NOTHING;
			break;
		case START:
		case VEHICLE1:
			LCD.drawString("V1 received", 0, 1);
			Sa = 50 + Sa / 2; //No incluir en la documentaci—n. Son los roces.
			Sb = 50 + Sb / 2; //No incluir en la documentaci—n. Son los roces.
			S = (Sa + Sb) / 2;
			MA.controlMotor(S, BasicMotorPort.FORWARD);
			MB.controlMotor(S, BasicMotorPort.FORWARD);
			break;
		case VEHICLE2A:
			LCD.drawString("V2a received", 0, 1);
			Sa = 50 + Sa / 2; //No incluir en la documentaci—n. Son los roces.
			Sb = 50 + Sb / 2; //No incluir en la documentaci—n. Son los roces.
			MA.controlMotor(Sa,BasicMotorPort.FORWARD);
			MB.controlMotor(Sb,BasicMotorPort.FORWARD);
			break;
		case VEHICLE2B:
			LCD.drawString("V2b received", 0, 1);
			Sa = 50 + Sa / 2; //No incluir en la documentaci—n. Son los roces.
			Sb = 50 + Sb / 2; //No incluir en la documentaci—n. Son los roces.
			MA.controlMotor(Sb,BasicMotorPort.FORWARD);
			MB.controlMotor(Sa,BasicMotorPort.FORWARD);
			break;
		case VEHICLE3A:
			LCD.drawString("V3a received", 0, 1);
			MA.controlMotor(120 - Sa, BasicMotorPort.FORWARD);  //documentar 100 - Sa
			MB.controlMotor(120 - Sb, BasicMotorPort.FORWARD);  //documentar 100 - Sb
			break;
		case VEHICLE3B:
			LCD.drawString("V3b received", 0, 1);
			MA.controlMotor(120 - Sb, BasicMotorPort.FORWARD);  //documentar 100 - Sa
			MB.controlMotor(120 - Sa, BasicMotorPort.FORWARD);  //documentar 100 - Sb
			break;	
		case VEHICLE4A: //TODO: revisar
			LCD.drawString("V4 received", 0, 1);
			MA.controlMotor((100 - Sa - Sa^2)/25, BasicMotorPort.FORWARD);
			MB.controlMotor((100 - Sb - Sb^2)/25, BasicMotorPort.FORWARD);
		case VEHICLE4B: //TODO esto es incorrecto
			LCD.drawString("V4 received", 0, 1);
			MA.controlMotor((100 - Sb - Sb^2)/25, BasicMotorPort.FORWARD);
			MB.controlMotor((100 - Sa - Sa^2)/25, BasicMotorPort.FORWARD);
		case NOTHING:
		default:
			break;
		}
	}

	public void setCode(int code) {
		//Traduce code a un Command v‡lido
		switch (code) {
		case 1:
			this.code = Command.STOP;
			break;
		case 2:
			this.code = Command.START;
			break;
		case 3:
			this.code = Command.VEHICLE1;
			break;
		case 4:
			this.code = Command.VEHICLE2A;
			break;
		case 5:
			this.code = Command.VEHICLE2B;
			break;
		case 6:
			this.code = Command.VEHICLE3A;
			break;
		case 7:
			this.code = Command.VEHICLE3B;
			break;
		case 0:
		default:
			this.code = Command.NOTHING;
			break;
		}
	}
}
