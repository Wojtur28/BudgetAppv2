package com.example.budgetappv2.service;

import com.example.budgetappv2.transaction.Transaction;
import com.example.budgetappv2.transaction.TransactionRepository;
import com.example.budgetappv2.transaction.TransactionService;
import com.example.budgetappv2.transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private List<Transaction> transactions;

    @BeforeEach
    public void setUp() {
        transaction = Transaction.builder()
                .id(1L)
                .date(LocalDate.now())
                .total(BigDecimal.valueOf(100))
                .transactionType(TransactionType.TRANSFER)
                .notes("test")
                .build();

         transactions = List.of(transaction);
    }

    @AfterEach
    public void tearDown() {
        transaction = null;
        transactions = null;
    }

    @Test
    @DisplayName("Should return all transactions")
    public void shouldReturnAllTransactions() {

        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<Transaction> foundTransactions = transactionService.getTransactions().getBody();

        assertEquals(transactions, foundTransactions);
    }

    @Test
    @DisplayName("Should return transaction by id")
    public void shouldReturnTransactionById() {

        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(transaction));

        Transaction foundTransaction = transactionService.getTransactionById(1L).getBody();

        assertEquals(transaction, foundTransaction);
    }

    @Test
    @DisplayName("Should create new transaction")
    public void shouldCreateNewTransaction() {

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction createdTransaction = transactionService.addTransaction(transaction).getBody();


        assertEquals(transaction, createdTransaction);
    }


    @Test
    @DisplayName("Should update transaction")
    public void shouldUpdateTransaction() {

        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateTransaction(transaction).getBody();

        assertEquals(transaction, updatedTransaction);
    }

    @Test
    @DisplayName("Should delete transaction")
    public void shouldReturnNotFoundStatus() {

        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(transaction));

        ResponseEntity<HttpStatus> response = transactionService.deleteTransactionById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }



}
