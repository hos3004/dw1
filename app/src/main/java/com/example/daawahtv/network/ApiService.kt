package com.example.daawahtv.network

import retrofit2.Response // تأكد من استيراد Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import com.example.daawahtv.network.EpisodesResponse // تم إضافة هذا الاستيراد

interface ApiService {
    // اجعل الدالة suspend
    @GET("dashboard/home")
    suspend fun getHomeContent(): HomeResponse // لم تعد تُرجع Call، بل HomeResponse مباشرة

    // مثال لدالة أخرى، اجعلها suspend أيضاً
    @GET("episodes")
    suspend fun getEpisodesByProgram(@Query("program_id") programId: Long): EpisodesResponse
}
