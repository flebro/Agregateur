package com.iia.fabien.agregateur.services.groupb

import com.iia.fabien.agregateur.services.groupb.models.GroupbCredentials
import com.iia.fabien.agregateur.services.groupb.models.GroupbHotel
import com.iia.fabien.agregateur.services.groupb.models.GroupbReservation
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by fabien on 18/03/18.
 */

val GROUPB_API_BASE_URL = "http://10.0.2.2:8080/"

interface GroupbConnecteur {

    @POST("/hotel/rest/login")
    fun login(@Body credentials: GroupbCredentials): Call<ResponseBody>

    @GET("/hotel/rest/reservations")
    fun listReservation(@Header("Authorization") token: String?): Call<List<GroupbReservation>>

    @GET("/hotel/rest/hotels")
    fun listHotelsDispos(@Header("Authorization") token: String?, @Query("dateDeb") dateDeb: String, @Query("dateFin") dateFin: String): Call<List<GroupbHotel>>

    @POST("/hotel/rest/reservations")
    fun createReservation(@Header("Authorization") token: String?, @Body reservation: GroupbReservation) : Call<ResponseBody>

}