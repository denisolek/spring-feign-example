package com.denisolek.visit

import com.denisolek.visit.client.DoctorClient
import com.denisolek.visit.client.PatientClient
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

class Visit(
    var id: Int? = null,
    val date: LocalDateTime,
    val doctorId: Int,
    val patientId: Int
)

class VisitDetailsDTO(
    var id: Int,
    val date: LocalDateTime,
    val doctorDTO: DoctorDTO,
    val patientDTO: PatientDTO
)

class DoctorDTO(var id: Int, val name: String)
class PatientDTO(var id: Int, val name: String)

@RestController
class VisitController(val visitService: VisitService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/visits")
    fun addVisit(@RequestBody visit: Visit) = visitService.add(visit)

    @GetMapping("/visits")
    fun getVisits(): List<Visit> = visitService.getAll()

    @GetMapping("/visits/{id}")
    fun getVisit(@PathVariable id: Int): Visit = visitService.get(id)

    @GetMapping("/visits/details")
    fun getVisitsDetails(): List<VisitDetailsDTO> = visitService.getAllDetails()

    @GetMapping("/visits/{id}/details")
    fun getVisitDetails(@PathVariable id: Int): VisitDetailsDTO = visitService.getDetails(id)
}

@Service
class VisitService(
    private val patientClient: PatientClient,
    private val doctorClient: DoctorClient
) {
    val map: MutableMap<Int, Visit> = mutableMapOf()
    var maxId: Int = 0

    fun add(visit: Visit) {
        // check if doctor exists - in real example it should be just endpoint returning true/false, not entire doctor
        if (doctorClient.get(visit.doctorId))

        ++maxId
        visit.id = maxId
        map[maxId] = visit
    }

    fun getAll(): List<Visit> {
        return ArrayList(map.values)
    }

    fun get(id: Int): Visit {
        return map[id] ?: throw VisitNotFound()
    }

    fun getAllDetails(): List<VisitDetailsDTO> {
        val visits = ArrayList(map.values)
        val doctorIds = visits.map { it.doctorId }
        val patientIds = visits.map { it.patientId }

        val patients = patientClient.getMany(patientIds.map { it.toString() }).map { Pair(it.id, it) }.toMap()
        val doctors = doctorClient.getMany(doctorIds.map { it.toString() }).map { Pair(it.id, it) }.toMap()

        return visits.map {
            VisitDetailsDTO(
                id = it.id!!,
                date = it.date,
                doctorDTO = doctors[it.doctorId]!!,
                patientDTO = patients[it.patientId]!!
            )
        }
    }

    fun getDetails(id: Int): VisitDetailsDTO {
        val visit = map[id] ?: throw VisitNotFound()
        val patientDTO = try {
            patientClient.get(visit.patientId)
        } catch (ex: FeignException) {
            throw ServiceException(HttpStatus.resolve(ex.status())!!, "")
        }

        val doctorDTO = try {
            doctorClient.get(visit.doctorId)
        } catch (ex: FeignException) {
            throw ServiceException(HttpStatus.resolve(ex.status())!!, "")
        }

        return VisitDetailsDTO(
            id = visit.id!!,
            date = visit.date,
            doctorDTO = doctorDTO,
            patientDTO = patientDTO
        )
    }
}