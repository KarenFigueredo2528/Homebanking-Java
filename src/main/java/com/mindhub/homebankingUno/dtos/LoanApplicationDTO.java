package com.mindhub.homebankingUno.dtos;

public class LoanApplicationDTO {

	private long id;
	private String numberAccountDestination;
	private double amount;
	private int payments;

	public LoanApplicationDTO() {
	}

	public LoanApplicationDTO(long id, String numberAccountDestination, double amount, int payments) {
		this.id = id;
		this.numberAccountDestination = numberAccountDestination;
		this.amount = amount;
		this.payments = payments;
	}

	public long getId() {
		return id;
	}

	public String getNumberAccountDestination() {
		return numberAccountDestination;
	}

	public double getAmount() {
		return amount;
	}

	public int getPayments() {
		return payments;
	}


}
