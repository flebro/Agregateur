package com.iia.fabien.agregateur.services.groupd

import com.iia.fabien.agregateur.services.groupd.models.GroupdFlight
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by fabien on 19/03/18.
 */
val GROUPD_API_BASE_URL = "http://192.168.43.226:8082/"

interface GroupdConnecteur {

    @GET("/api/flight")
    fun listReservation(@Query("allFlights") allFlights: Boolean): Call<List<GroupdFlight>>

}