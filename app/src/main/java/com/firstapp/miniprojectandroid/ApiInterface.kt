package com.firstapp.miniprojectandroid

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/todos")
    suspend fun getData(): Response<ArrayList<MyDataItem>>

}
