package com.denisolek.doctor

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

class Doctor(
    var id: Int? = null,
    val name: String
)

@RestController
class DoctorController(val doctorService: DoctorService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/doctors")
    fun addDoctor(@RequestBody doctor: Doctor) = doctorService.add(doctor)

    @GetMapping("/doctors")
    fun getDoctors(
        @RequestParam(required = false, defaultValue = "") id: List<String>
    ): List<Doctor> = doctorService.getAll()

    @GetMapping("/doctors/{id}")
    fun getDoctor(@PathVariable id: Int): Doctor = doctorService.get(id)
}

@Service
class DoctorService {
    val map: MutableMap<Int, Doctor> = mutableMapOf()
    var maxId: Int = 0

    fun add(doctor: Doctor) {
        ++maxId
        doctor.id = maxId
        map[maxId] = doctor
    }

    fun getAll(): List<Doctor> {
        return ArrayList(map.values)
    }

    fun get(id: Int): Doctor {
        return map[id] ?: throw DoctorNotFound()
    }
}