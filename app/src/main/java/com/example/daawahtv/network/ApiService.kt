package com.example.daawahtv.network

import retrofit2.Response // تأكد من استيراد Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import com.example.daawahtv.network.EpisodeItem

interface ApiService {
    // اجعل الدالة suspend
    @GET("dashboard/home")
    suspend fun getHomeContent(): Response<HomeResponse> // تعديل لتعيد Response

    // مثال لدالة أخرى، اجعلها suspend أيضاً
    @GET("episodes")
    suspend fun getEpisodesByProgram(@Query("program_id") programId: Long): Response<List<EpisodeItem>>
}
