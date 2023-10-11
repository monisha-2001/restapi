package com.example.bank.model

import com.example.bank.repository.model.TransactionDBModel

class TranscationModel(
    val targetAccount:String,
    val value:Double,
    val description:String="",
)


fun TranscationModel.convertToDBModel()= TransactionDBModel(
    accountIdentifier = this.targetAccount,
    value=this.value,
    description=this.description
)