package com.iia.fabien.agregateur.services.groupe

import android.os.AsyncTask
import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.ConnecteurService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by fabien on 19/03/18.
 */
class GroupeService : ConnecteurService(TypeEnum.HOTEL) {
    companion object {
        private val dateParser = SimpleDateFormat("yyyy-MM-dd")
        private val authService = GroupeAuthService()
    }

    var connecteur = Retrofit.Builder()
            .baseUrl(GROUPE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupeConnecteur::class.java)

    override fun listReservations(): List<Reservation> {
        val reservations = ArrayList<Reservation>()
        object: AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg p0: Void?) {
                val response = connecteur.listHotels().execute()
                response.body()?.forEach {
                    h -> h.bookingList.forEach {
                    r -> reservations.add(Reservation(Reservable(h.id, h.name, TypeEnum.HOTEL, uuid),
                        dateParser.parse(r.startDate),
                        dateParser.parse(r.endDate)))
                }
                }
            }
        }.execute().get()
        return reservations
    }

    override fun listObjetsDispos(dateDebut: Date, dateFin: Date): List<Reservable> {
        val reservables = ArrayList<Reservable>()
        val dateDebutStr = dateParser.format(dateDebut)
        val dateFinStr = dateParser.format(dateFin)
        object: AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg p0: Void?) {
                val response = connecteur.listHotels(dateDebutStr, dateFinStr).execute()
                response.body()?.forEach {
                    h -> reservables.add(Reservable(h.id, h.name, TypeEnum.HOTEL, uuid))
                }
            }

        }.execute().get()
        return reservables
    }

    override fun createReservation(reservation: Reservation) {
        val auth = authService.authenticate()
        if (auth != null) {
            val dateDebutStr = dateParser.format(reservation.dateDebut)
            val dateFinStr = dateParser.format(reservation.dateFin)
            object: AsyncTask<Void, Void, Unit>() {
                override fun doInBackground(vararg p0: Void?) {
                    val response = connecteur.createReservation(auth.accessToken, reservation.reservable.id, dateDebutStr, dateFinStr).execute()
                }

            }.execute().get()
        }
    }
}