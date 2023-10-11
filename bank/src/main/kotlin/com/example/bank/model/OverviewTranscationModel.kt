package com.example.bank.model

import com.example.bank.repository.model.TransactionDBModel
import java.util.*

class OverviewTranscationModel  (
    val targetAccount:String,
    val value:Double,
    val description:String,
    val date: Date,
    val id:String
)

fun TransactionDBModel.convertToOverviewTranscationModel()= OverviewTranscationModel(
    targetAccount = this.accountIdentifier,
    value=this.value,
    description=this.description,
    date=this.date,
    id=this.id.toString()
)