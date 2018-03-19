package com.iia.fabien.agregateur.services

import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.groupb.GroupbService
import java.util.*

/**
 * Created by fabien on 16/03/18.
 */
object DataAggregator {

    private val connecteurs = ArrayList<ConnecteurService>().apply {
        add(GroupbService())
    }

    fun listReservationsByType(type: TypeEnum) : List<Reservation> {
        return connecteurs.filter { c -> c.type == type }.flatMap(ConnecteurService::listReservations)
    }

    fun listObjetsDisponibles(type: TypeEnum, dateDebut: Date, dateFin: Date) : List<Reservable> {
        return connecteurs.filter { c -> c.type == type }.flatMap{ c -> c.listObjetsDispos(dateDebut, dateFin)}
    }

    fun createReservation(reservable: Reservable, dateDebut: Date, dateFin: Date) {
        val reservation = Reservation(reservable, dateDebut, dateFin)
        connecteurs.first { c -> c.uuid == reservable.source }.createReservation(reservation)
    }

}