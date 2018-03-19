package com.iia.fabien.agregateur.services.groupe

import android.os.AsyncTask
import com.iia.fabien.agregateur.services.groupe.models.Auth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by fabien on 19/03/18.
 */
class GroupeAuthService {

    companion object {
        val grantType = "password"
        val username = "test"
        val password = "test"
    }

    var connecteur = Retrofit.Builder()
            .baseUrl(GROUPE_AUTH_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupeAuthConnecteur::class.java)

    fun authenticate(): Auth? {
        return object: AsyncTask<Void, Void, Auth?>() {
            override fun doInBackground(vararg p0: Void?): Auth? {
                return connecteur.getToken(grantType, username, password).execute().body()
            }

        }.execute().get()
    }

}