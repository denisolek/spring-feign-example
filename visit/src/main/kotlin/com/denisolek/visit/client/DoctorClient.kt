package com.denisolek.visit.client

import com.denisolek.visit.DoctorDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(url = "http://localhost:8081", name = "doctorClient")
interface DoctorClient {
    @GetMapping("/doctors/{id}")
    fun get(@PathVariable id: Int): DoctorDTO

    @GetMapping("/doctors")
    fun getMany(@RequestParam(required = false, defaultValue = "") id: List<String>): List<DoctorDTO>
}