package com.example.budgetappv2.transaction;

import com.example.budgetappv2.transaction.dto.TransactionDto;
import com.example.budgetappv2.transaction.dto.TransactionReadDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static com.example.budgetappv2.transaction.mapper.TransactionMapper.mapToTransaction;
import static com.example.budgetappv2.transaction.mapper.TransactionReadDtoMapper.mapTransactionToTransactionReadDto;
import static com.example.budgetappv2.transaction.mapper.TransactionReadDtoMapper.mapTransactionsToTransactionsReadDto;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final long EMPTY_ID = -1;
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping
    public ResponseEntity<Stream<TransactionReadDto>> getTransactions() {
        return mapTransactionsToTransactionsReadDto(transactionService.getTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionReadDto> getTransactionById(@PathVariable long id) {
        return mapTransactionToTransactionReadDto(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.addTransaction(mapToTransaction(EMPTY_ID, transactionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable long id, @RequestBody TransactionDto transactionDto) {
        return transactionService.updateTransaction(mapToTransaction(id, transactionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransactionById(@PathVariable long id) {
        return transactionService.deleteTransactionById(id);
    }

}
