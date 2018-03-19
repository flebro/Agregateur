package com.iia.fabien.agregateur.services.groupd

import android.os.AsyncTask
import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.ConnecteurService
import com.iia.fabien.agregateur.services.groupb.GroupbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by fabien on 19/03/18.
 */
class GroupdService : ConnecteurService(TypeEnum.AVION) {

    companion object {
        private val dateParser = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ")
    }

    var connecteur = Retrofit.Builder()
            .baseUrl(GROUPD_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupdConnecteur::class.java)

    override fun createReservation(reservation: Reservation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listObjetsDispos(dateDebut: Date, dateFin: Date): List<Reservable> {
        val reservations = ArrayList<Reservable>()

        return reservations
    }

    override fun listReservations(): List<Reservation> {
        val reservations = ArrayList<Reservation>()
        return object: AsyncTask<Void, Void, List<Reservation>>() {
            override fun doInBackground(vararg p0: Void?): List<Reservation> {
                val response = connecteur.listReservation(true).execute()
                response.body()?.forEach { f-> reservations.add(Reservation(Reservable(f.id, f.name, type, uuid),
                        dateParser.parse(f.start),
                        dateParser.parse(f.end))) }
                return reservations
            }

        }.execute().get()
        return reservations
    }

}