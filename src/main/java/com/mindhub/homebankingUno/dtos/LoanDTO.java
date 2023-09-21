package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.Loan;

import java.util.List;

public class LoanDTO {

	private long id;
	private String name;
	private double maxAmount;
	private List<Integer> payments;
	private double percentage;

	public LoanDTO(Loan loan) {
		this.id = loan.getId();
		this.name = loan.getName();
		this.maxAmount = loan.getMaxAmount();
		this.payments = loan.getPayments();
		this.percentage = loan.getPercentage();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public List<Integer> getPayments() {
		return payments;
	}

	public double getPercentage() {
		return percentage;
	}
}
