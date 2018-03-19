package com.iia.fabien.agregateur.services.groupe

import com.iia.fabien.agregateur.services.groupe.models.GroupeHotel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by fabien on 19/03/18.
 */
val GROUPE_API_BASE_URL = "http://192.168.100.72:5000/"

interface GroupeConnecteur {

    @GET("/api/hotels")
    fun listHotels(): Call<List<GroupeHotel>>

    @GET("/api/hotels")
    fun listHotels(@Query("start") start: String, @Query("end") end: String): Call<List<GroupeHotel>>

    @POST("/api/hotel/{id}/book")
    fun createReservation(@Header("Authorization") token: String, @Path("id") idHotel: Int, @Query("start") start: String, @Query("end") end: String) : Call<ResponseBody>

}