package com.denisolek.patient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@SpringBootApplication
class PatientApplication

fun main(args: Array<String>) {
    runApplication<PatientApplication>(*args)
}

@Component
class Init {
    @Autowired
    val patientService: PatientService? = null

    @PostConstruct
    fun init() {
        patientService?.add(Patient(name = "Pacjent Tomasz"))
        patientService?.add(Patient(name = "Pacjent Irek"))
        patientService?.add(Patient(name = "Pacjent Sara"))
        patientService?.add(Patient(name = "Pacjent Dominika"))
        patientService?.add(Patient(name = "Pacjent Magda"))
        patientService?.add(Patient(name = "Pacjent Monika"))
    }
}