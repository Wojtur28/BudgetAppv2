package com.example.budgetappv2.controller;

import com.example.budgetappv2.transaction.Transaction;
import com.example.budgetappv2.transaction.TransactionController;
import com.example.budgetappv2.transaction.TransactionService;
import com.example.budgetappv2.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;


@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    private ResponseEntity<Transaction> transaction1;
    private ResponseEntity<List<Transaction>> transactions;

    @BeforeEach
    public void setUp() {
        transaction1 = ResponseEntity.ok(Transaction.builder()
                .id(1L)
                .date(LocalDate.now())
                .total(BigDecimal.valueOf(100))
                .transactionType(TransactionType.TRANSFER)
                .notes("test1")
                .build());

        ResponseEntity<Transaction> transaction2 = ResponseEntity.ok(Transaction.builder()
                .id(2L)
                .date(LocalDate.of(2021, 1, 1))
                .total(BigDecimal.valueOf(2500))
                .transactionType(TransactionType.BLIK_PAYMENT)
                .notes("test2")
                .build());

         transactions = ResponseEntity.ok(List.of(Objects.requireNonNull(transaction1.getBody()), Objects.requireNonNull(transaction2.getBody())));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return all transactions")
    public void getAllTransactions() throws Exception {
        when(transactionService.getTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.valueOf("application/json")))
                .andExpect(result -> hasSize(2))
                .andExpect(result -> jsonPath("$[0].id", is(1L)))
                .andExpect(result -> jsonPath("$[0].date", is(LocalDate.now())))
                .andExpect(result -> jsonPath("$[0].total", is(BigDecimal.valueOf(100))))
                .andExpect(result -> jsonPath("$[0].transactionType", is(TransactionType.TRANSFER)))
                .andExpect(result -> jsonPath("$[0].notes", is("test1")))
                .andExpect(result -> jsonPath("$[1].id", is(2L)))
                .andExpect(result -> jsonPath("$[1].date", is(LocalDate.of(2021, 1, 1))))
                .andExpect(result -> jsonPath("$[1].total", is(BigDecimal.valueOf(2500))))
                .andExpect(result -> jsonPath("$[1].transactionType", is(TransactionType.BLIK_PAYMENT)))
                .andExpect(result -> jsonPath("$[1].notes", is("test2")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return transaction by id")
    public void getTransactionById() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(transaction1);

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.valueOf("application/json")))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.date", is(LocalDate.now())))
                .andExpect(result -> jsonPath("$.total", is(BigDecimal.valueOf(100))))
                .andExpect(result -> jsonPath("$.transactionType", is(TransactionType.TRANSFER)))
                .andExpect(result -> jsonPath("$.notes", is("test1")));
    }


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should create transaction")
    public void addTransaction() throws Exception {

        when(transactionService.addTransaction(transaction1.getBody())).thenReturn(transaction1);

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "date": "2021-01-01",
                                    "total": 100,
                                    "transactionType": "TRANSFER",
                                    "notes": "test1"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.valueOf("application/json")))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.date", is(LocalDate.now())))
                .andExpect(result -> jsonPath("$.total", is(BigDecimal.valueOf(100))))
                .andExpect(result -> jsonPath("$.transactionType", is(TransactionType.TRANSFER)))
                .andExpect(result -> jsonPath("$.notes", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should update transaction")
    public void updateTransaction() throws Exception {
        when(transactionService.updateTransaction(transaction1.getBody())).thenReturn(transaction1);

        mockMvc.perform(put("/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "date": "2021-01-01",
                                    "total": 100,
                                    "transactionType": "TRANSFER",
                                    "notes": "test1"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.valueOf("application/json")))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.date", is(LocalDate.now())))
                .andExpect(result -> jsonPath("$.total", is(BigDecimal.valueOf(100))))
                .andExpect(result -> jsonPath("$.transactionType", is(TransactionType.TRANSFER)))
                .andExpect(result -> jsonPath("$.notes", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should delete transaction by id")
    public void deleteTransactionById() throws Exception {

        when(transactionService.deleteTransactionById(1L)).thenReturn(ResponseEntity.ok().build());


        mockMvc.perform(delete("/transactions/1"))
                .andExpect(status().isOk());
    }




}
