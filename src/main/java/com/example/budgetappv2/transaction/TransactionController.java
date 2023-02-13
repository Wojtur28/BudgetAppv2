package com.example.budgetappv2.transaction;

import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static com.example.budgetappv2.transaction.mapper.TransactionReadDtoMapper.mapTransactionToTransactionReadDto;

@RestController
@RequestMapping("/transactions")
public class TransactionController {


    @Autowired
    TransactionService transactionService;


    @GetMapping
    public ResponseEntity<Stream<TransactionReadDto>> getTransactions() {
        return mapTransactionToTransactionReadDto(transactionService.getTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransactionById(@PathVariable long id) {
        return transactionService.deleteTransactionById(id);
    }

}
