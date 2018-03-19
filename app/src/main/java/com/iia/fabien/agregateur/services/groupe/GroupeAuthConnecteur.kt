package com.iia.fabien.agregateur.services.groupe

import com.iia.fabien.agregateur.services.groupe.models.Auth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by fabien on 19/03/18.
 */
val GROUPE_AUTH_API_BASE_URL = "http://192.168.100.72:3000/"

interface GroupeAuthConnecteur  {

    @Headers("Authorization: Basic YXBwbGljYXRpb246c2VjcmV0")
    @FormUrlEncoded
    @POST("/oauth/token")
    fun getToken(@Field("grant_type") grantType: String, @Field("username") username: String, @Field("password") password: String) : Call<Auth>
}