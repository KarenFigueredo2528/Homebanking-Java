package com.mindhub.homebankingUno.services.implement;

import com.mindhub.homebankingUno.models.Transaction;
import com.mindhub.homebankingUno.repositories.TransactionRepository;
import com.mindhub.homebankingUno.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Override
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
}
