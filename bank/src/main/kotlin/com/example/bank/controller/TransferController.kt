package com.example.bank.controller

import com.example.bank.model.OverviewTranscationModel
import com.example.bank.model.TranscationModel
import com.example.bank.model.convertToDBModel
import com.example.bank.model.convertToOverviewTranscationModel
import com.example.bank.repository.TransferRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transfer")
class TransferController(val transferRepository: TransferRepository) {

    @PostMapping("/new")
    fun newTransfer(@RequestBody transcationModel: TranscationModel){
        transferRepository.save(transcationModel.convertToDBModel())
    }

    @GetMapping("all")
    fun getAllTransfers():List<OverviewTranscationModel> {
//        return transferRepository.findAll().map{ it.convertToOverviewTranscationModel()}
        return transferRepository.findAll().map { it.convertToOverviewTranscationModel() }
    }
}