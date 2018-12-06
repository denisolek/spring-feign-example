package com.denisolek.patient

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

class Patient(
    var id: Int? = null,
    val name: String,
    val email: String
)

@RestController
class PatientController(val patientService: PatientService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/patients")
    fun addPatient(@RequestBody patient: Patient) = patientService.add(patient)

    @GetMapping("/patients")
    fun getPatients(
        @RequestParam(required = false, defaultValue = "") id: List<String>
    ): List<Patient> = patientService.getMany(id)

    @GetMapping("/patients/{id}")
    fun getPatient(@PathVariable id: Int): Patient = patientService.get(id)
}

@Service
class PatientService {
    val map: MutableMap<Int, Patient> = mutableMapOf()
    var maxId: Int = 0

    fun add(patient: Patient) {
        if (map.values.find { it.email == patient.email } != null)
            throw PatientAlreadyExists()
        ++maxId
        patient.id = maxId
        map[maxId] = patient
    }

    fun getMany(ids: List<String>): List<Patient> {
        val patientIds = ids.map { it.toInt() }
        return map.values.filter { patientIds.contains(it.id) }
    }

    fun get(id: Int): Patient {
        return map[id] ?: throw PatientNotFound()
    }
}


