package com.example.budgetappv2.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try{
            return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.FOUND);
        } catch (Exception e) {
            log.error("Error with \"getAllTransaction\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Transaction> getTransactionById(Long id){
        try{
            return new ResponseEntity<>(transactionRepository.findById(id).stream().findFirst()
                    .orElse(null), HttpStatus.FOUND);
        } catch (Exception e) {
            log.error("Error with \"getTransactionById\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Transaction> addTransaction(Transaction transaction) {
        try {
            return new ResponseEntity<>(transactionRepository.save(transaction), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("createTransaction exception: "+ transaction.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Transaction> updateTransaction(Transaction transaction){
        try{
            Transaction _transaction = transactionRepository.findById(transaction.getId())
                    .stream().findFirst().orElse(null);
            if(_transaction == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            _transaction.setDate(transaction.getDate());
            _transaction.setTotal(transaction.getTotal());
            _transaction.setTransactionType(transaction.getTransactionType());
            _transaction.setNotes(transaction.getNotes());
            return ResponseEntity.ok().body(transactionRepository.save(_transaction));
        } catch (Exception e) {
            log.error("Error with \"updateTransaction\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deleteTransactionById(Long id){
        try{
            transactionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error with \"deleteTransaction\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
