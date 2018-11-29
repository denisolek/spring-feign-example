package com.denisolek.visit.client

import com.denisolek.visit.PatientDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(url = "http://localhost:8080", name = "patient")
interface PatientClient {
    @GetMapping("/patients/{id}")
    fun get(@PathVariable id: Int): PatientDTO

    @GetMapping("/patients")
    fun getMany(@RequestParam(required = false, defaultValue = "") id: List<String>): List<PatientDTO>
}