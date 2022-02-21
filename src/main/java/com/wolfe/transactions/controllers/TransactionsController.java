package com.wolfe.transactions.controllers;

import com.wolfe.transactions.TransactionNotFoundException;
import com.wolfe.transactions.models.Transaction;
import com.wolfe.transactions.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class TransactionsController {
    private final TransactionRepository repository;

    TransactionsController(TransactionRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/transactions")
    List<Transaction> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/transactions")
    Transaction newTransaction(@RequestBody Transaction newTransaction) {
        return repository.save(newTransaction);
    }

    @GetMapping("/transactions/{id}")
    Transaction one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @PutMapping("/transactions/{id}")
    Transaction replaceTransaction(@RequestBody Transaction newTransaction, @PathVariable Long id) {
        return repository.findById(id)
                .map(transaction -> {
                    transaction.setDate(newTransaction.getDate());
                    transaction.setAmount(newTransaction.getAmount());
                    transaction.setVendor(newTransaction.getVendor());
                    transaction.setCategory(newTransaction.getCategory());
                    transaction.setAccountId(newTransaction.getAccountId());
                    return repository.save(transaction);
                })
                .orElseGet(() -> {
                    newTransaction.setId(id);
                    return repository.save(newTransaction);
                });
    }

    @DeleteMapping("/transactions/{id}")
    void deleteTransaction(@PathVariable Long id) {
        repository.deleteById((id));
    }
}
