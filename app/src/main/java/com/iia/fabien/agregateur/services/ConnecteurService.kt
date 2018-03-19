package com.iia.fabien.agregateur.services

import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.models.TypeEnum
import java.util.*

/**
 * Created by fabien on 18/03/18.
 */
abstract class ConnecteurService(type: TypeEnum) {

    val uuid = UUID.randomUUID()
    val type = type

    abstract fun listReservations() : List<Reservation>

    abstract fun listObjetsDispos(dateDebut: Date, dateFin: Date) : List<Reservable>

    abstract fun createReservation(reservation: Reservation)

}