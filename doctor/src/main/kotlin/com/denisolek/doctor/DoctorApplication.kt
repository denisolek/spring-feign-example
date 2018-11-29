package com.denisolek.doctor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@SpringBootApplication
class DoctorApplication

fun main(args: Array<String>) {
    runApplication<DoctorApplication>(*args)
}

@Component
class Init {
    @Autowired
    val doctorService: DoctorService? = null

    @PostConstruct
    fun init() {
        doctorService?.add(Doctor(name = "Doktor Mateusz"))
        doctorService?.add(Doctor(name = "Doktor Bożena"))
    }
}