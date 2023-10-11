package com.example.bank.repository

import com.example.bank.repository.model.TransactionDBModel
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TransferRepository : CrudRepository<TransactionDBModel, UUID>