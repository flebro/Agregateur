package com.iia.fabien.agregateur.services.groupb

import android.os.AsyncTask
import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.ConnecteurService
import com.iia.fabien.agregateur.services.groupb.models.GroupbCredentials
import com.iia.fabien.agregateur.services.groupb.models.GroupbHotel
import com.iia.fabien.agregateur.services.groupb.models.GroupbReservation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by fabien on 18/03/18.
 */


class GroupbService : ConnecteurService(TypeEnum.HOTEL) {

    companion object {
        private val AUTHENTICATION_SCHEME = "Bearer"
        private val dateParser = SimpleDateFormat("yyyy-MM-dd")
    }

    var token: String? = null
    var credentials = GroupbCredentials("groupb", "1234")
    var connecteur = Retrofit.Builder()
            .baseUrl(GROUPB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupbConnecteur::class.java)

    override fun listReservations(): List<Reservation> {
        assureToken()
        return object: AsyncTask<Void, Void, List<Reservation>>() {
            override fun doInBackground(vararg p0: Void?): List<Reservation> {
                val response = connecteur.listReservation(token).execute()
                val resas = ArrayList<Reservation>()
                response.body()?.forEach { r-> resas.add(Reservation(Reservable(r.hotel.id, r.hotel.nom, type, uuid), dateParser.parse(r.dateDebut), dateParser.parse(r.dateFin))) }
                return resas
            }

        }.execute().get()
    }

    override fun listObjetsDispos(dateDebut: Date, dateFin: Date): List<Reservable> {
        val dateDebutStr = dateParser.format(dateDebut)
        val dateFinStr = dateParser.format(dateFin)
        assureToken()
        return object: AsyncTask<Void, Void, List<Reservable>>() {
            override fun doInBackground(vararg p0: Void?): List<Reservable> {
                val response = connecteur.listHotelsDispos(token, dateDebutStr, dateFinStr).execute()
                val resas = ArrayList<Reservable>()
                response.body()?.forEach { r-> resas.add(Reservable(r.id, r.nom, TypeEnum.HOTEL, uuid)) }
                return resas
            }

        }.execute().get()
    }

    override fun createReservation(reservation: Reservation) {
        assureToken()
        val reservationAdapted = GroupbReservation(dateParser.format(reservation.dateDebut), dateParser.format(reservation.dateFin),
                GroupbHotel(reservation.reservable.id, reservation.reservable.description))

        object: AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg p0: Void?) : Unit {
                val response = connecteur.createReservation(token, reservationAdapted).execute()
            }
        }.execute()
    }

    private fun assureToken() {
        if (token == null) {
            token = AUTHENTICATION_SCHEME + " " + object: AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg p0: Void?): String {
                    val response = connecteur.login(credentials).execute()
                    return response.body()?.string().orEmpty()
                }

            }.execute().get()
        }
    }

}