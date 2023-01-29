package com.example.budgetappv2.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestTransactionService {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    @DisplayName("Should get all transactions and return status 200")
    public void getAllTransactions_returnsFound() throws Exception {
        //given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.getAllTransactions()).thenReturn(new ResponseEntity<>(transactions, HttpStatus.OK));
        //when
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should get transaction by id and return status 200 if found")
    public void getTransactionById_returnsFound() throws Exception {
        //given
        Transaction transaction = new Transaction();
        when(transactionService.getTransactionById(1L)).thenReturn(new ResponseEntity<>(transaction, HttpStatus.OK));
        //when
        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should get transaction by id and return status 404 if not found")
    public void getTransactionById_returnsNotFound() throws Exception {
        //given
        when(transactionService.getTransactionById(1L)).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        //when
        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
        //then
        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add transaction and return status 200")
    public void addTransaction_returnsCreated() throws Exception {
        //given
        Transaction transaction = new Transaction();
        when(transactionService.addTransaction(transaction)).thenReturn(new ResponseEntity<>(transaction, HttpStatus.CREATED));
        //when
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Transaction> response = transactionController.addTransaction(transaction);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add transaction and return status 400 if bad request")
    public void addTransaction_returnsBadRequest() throws Exception {
        //given
        Transaction transaction = new Transaction();
        when(transactionService.addTransaction(transaction)).thenReturn(new ResponseEntity<>(transaction, HttpStatus.BAD_REQUEST));
        //when
        mockMvc.perform(post("/transactions"))
                .andExpect(status().isBadRequest())
                .andDo(print());
        //then
        ResponseEntity<Transaction> response = transactionController.addTransaction(transaction);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @DisplayName("Should delete by id and return status 200 if found")
    public void deleteTransactionById_returnsFound() throws Exception {
        //given
        when(transactionService.deleteTransactionById(1L)).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));
        //when
        mockMvc.perform(delete("/transactions/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<HttpStatus> response = transactionController.deleteTransactionById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
