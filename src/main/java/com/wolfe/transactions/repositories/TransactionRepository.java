package com.wolfe.transactions.repositories;

import com.wolfe.transactions.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
