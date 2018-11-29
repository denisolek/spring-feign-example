package com.denisolek.visit.client

import com.denisolek.visit.DoctorDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(url = "http://localhost:8081", name = "doctor")
interface DoctorClient {
    @GetMapping("/doctors/{id}")
    fun get(@PathVariable id: Int): DoctorDTO
}