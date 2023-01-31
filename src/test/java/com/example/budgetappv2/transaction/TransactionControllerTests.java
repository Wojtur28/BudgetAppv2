package com.example.budgetappv2.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TransactionControllerTests {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    private final List<Transaction> transactions = new ArrayList<>(Arrays.asList(
            Transaction.builder()
                    .id(1L)
                    .date(LocalDate.of(2021, 1, 1))
                    .total(BigDecimal.valueOf(100.0))
                    .transactionType(TransactionType.TRANSFER)
                    .notes("Test")
                    .build(),
            Transaction.builder()
                    .id(2L)
                    .date(LocalDate.of(2021, 1, 2))
                    .total(BigDecimal.valueOf(200.0))
                    .transactionType(TransactionType.BLIK_PAYMENT)
                    .notes("Im poor")
                    .build()
    ));

    @Test
    @DisplayName("Should get all transactions and return status 200")
    public void should_get_all_transactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(new ResponseEntity<>(transactions, HttpStatus.OK));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date").value("2021-01-01"))
                .andExpect(jsonPath("$[0].total").value(100.0))
                .andExpect(jsonPath("$[0].transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$[0].notes").value("Test"))
                .andExpect(jsonPath("$[1].date").value("2021-01-02"))
                .andExpect(jsonPath("$[1].total").value(200.0))
                .andExpect(jsonPath("$[1].transactionType").value("BLIK_PAYMENT"))
                .andExpect(jsonPath("$[1].notes").value("Im poor"))
                .andDo(print());

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(transactions, response.getBody());
    }

    @Test
    @DisplayName("Should get transaction by id and return status 200")
    public void should_get_transaction_by_id() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(new ResponseEntity<>(transactions.get(0), HttpStatus.OK));

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2021-01-01"))
                .andExpect(jsonPath("$.total").value(100.0))
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.notes").value("Test"))
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(transactions.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should get transaction by wrong id and return status 404")
    public void should_get_transaction_by_wrong_id() throws Exception {
        when(transactionService.getTransactionById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/transactions/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.getTransactionById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add transaction and return status 201")
    public void should_add_transaction() throws Exception {

        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(transactions.get(0), HttpStatus.CREATED));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"2021-01-01\",\n" +
                        "    \"total\": 100.0,\n" +
                        "    \"transactionType\": \"TRANSFER\",\n" +
                        "    \"notes\": \"Test\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value("2021-01-01"))
                .andExpect(jsonPath("$.total").value(100.0))
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.notes").value("Test"))
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.addTransaction(transactions.get(0));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(transactions.get(0), response.getBody());
    }

    @Test
    @DisplayName("add transaction with wrong date and return status 400")
    public void should_add_transaction_with_wrong_date() throws Exception {

        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"2021-01-03\",\n" +
                        "    \"total\": 300.0,\n" +
                        "    \"transactionType\": \"TRANSFER\",\n" +
                        "    \"notes\": \"Test\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.addTransaction(transactions.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should update transaction and return status 200")
    public void should_update_transaction() throws Exception {

        when(transactionService.updateTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(transactions.get(0), HttpStatus.OK));

        mockMvc.perform(put("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"date\": \"2021-01-01\",\n" +
                        "    \"total\": 100.0,\n" +
                        "    \"transactionType\": \"TRANSFER\",\n" +
                        "    \"notes\": \"Test\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2021-01-01"))
                .andExpect(jsonPath("$.total").value(100.0))
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.notes").value("Test"))
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.updateTransaction(transactions.get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(transactions.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should update transaction with wrong id and return status 404")
    public void should_update_transaction_with_wrong_id() throws Exception {

        when(transactionService.updateTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"date\": \"2021-01-01\",\n" +
                        "    \"total\": 100.0,\n" +
                        "    \"transactionType\": \"TRANSFER\",\n" +
                        "    \"notes\": \"Test\"\n" +
                        "}"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<Transaction> response = transactionController.updateTransaction(transactions.get(0));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete transaction by id and return status 200")
    public void should_delete_transaction_by_id() throws Exception {
        when(transactionService.deleteTransactionById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(delete("/transactions/1"))
                .andExpect(status().isOk())
                .andDo(print());

        ResponseEntity<HttpStatus> response = transactionController.deleteTransactionById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete transaction by id and return status 404")
    public void should_delete_transaction_by_id_and_return_status_404() throws Exception {
        when(transactionService.deleteTransactionById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/transactions/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<HttpStatus> response = transactionController.deleteTransactionById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }




}
