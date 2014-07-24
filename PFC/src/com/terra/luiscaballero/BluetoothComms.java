package com.terra.luiscaballero;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BluetoothComms extends Thread {
	
	//Atributos
	private NXTController parent;
	private BTConnection connection;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;

	/**
	 * Constructor de la clase.
	 * @param caller objeto que ha invocado a este thread.
	 */
	BluetoothComms(NXTController caller) {
		parent = caller;
	}
	
	/**
	 * Método principal del thread. 
	 * Únicamente se limita a leer un dato y a
	 * actualizar el atributo data de NXTController.
	 */
	public void run(){
		while(true){
			try {
				read();
				Thread.sleep(1000);
				} 
			catch (InterruptedException e) {							
				}
			}
		}

	/**
	 * Espera una conexión desde el PC. Si no llega, el 
	 * método se bloquea y no acaba nunca.
	 */
	public void connect() {
		LCD.clear();
		LCD.drawString("BUSCANDO AL PC", 0, 0);
		connection = Bluetooth.waitForConnection();
		LCD.clear();
		LCD.drawString("Conectado", 0, 0);
		dataIn = connection.openDataInputStream();
		dataOut = connection.openDataOutputStream();
	}

	/**
	 * Envía un comando al PC
	 * 
	 * @param value valor que enviamos al PC
	 */
	public void send(int value) {
		try {
			dataOut.writeInt(value);
			dataOut.flush();
		} 
		catch (IOException e) {
		}		
	}

	/**
	 * Espera un dato desde el PC
	 */
	public void read() {
		try {
			parent.setCode(dataIn.readInt());
		} 
		catch (IOException e) {	
		}
	}
}
