package com.denisolek.visit

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.denisolek"])
class VisitApplication

fun main(args: Array<String>) {
    runApplication<VisitApplication>(*args)
}

@Component
class Init {
    @Autowired
    val visitService: VisitService? = null

    @PostConstruct
    fun init() {
        visitService?.add(Visit(date = ldt(10, 0), doctorId = 1, patientId = 1))
        visitService?.add(Visit(date = ldt(12, 0), doctorId = 1, patientId = 2))
        visitService?.add(Visit(date = ldt(14, 0), doctorId = 1, patientId = 3))
        visitService?.add(Visit(date = ldt(13, 0), doctorId = 2, patientId = 4))
    }

    fun ldt(hour: Int, minute: Int) = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(hour, minute))
}
