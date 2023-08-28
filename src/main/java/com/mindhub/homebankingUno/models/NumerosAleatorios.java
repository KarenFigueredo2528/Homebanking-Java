package com.mindhub.homebankingUno.models;

public class NumerosAleatorios {

	public static String CardNumber() {
		return getRandomNumber(1000, 10000) + "-"
			  + getRandomNumber(1000, 10000) + "-" +
			  getRandomNumber(1000, 10000) + "-" +
			  getRandomNumber(1000, 10000);
	}

	public static int getCardCVV() {
		int cvv = getRandomNumber(000, 999);
		return cvv;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
