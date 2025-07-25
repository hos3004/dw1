package com.example.daawahtv.repository

import com.example.daawahtv.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val apiService: ApiService,
    private val tvShowApiService: TvShowApiService
) {

    // ✅ جلب المحتوى الرئيسي (سلايدر، بانر)
    suspend fun getHomeContent(): Response<HomeResponse> = withContext(Dispatchers.IO) {
        apiService.getHomeContent()
    }

    // ✅ جلب تفاصيل برنامج تلفزيوني
    suspend fun getTvShowDetails(id: Long): TvShowDetailsResponse = withContext(Dispatchers.IO) {
        tvShowApiService.getTvShowDetails(id).execute().body()
            ?: throw Exception("TV Show details are null")
    }

    // ✅ جلب الحلقات لموسم معين
    suspend fun getSeasonEpisodes(
        tvShowId: Long,
        seasonId: Int,
        limit: Int = 100,
        offset: Int = 0
    ): SeasonResponse = withContext(Dispatchers.IO) {
        tvShowApiService.getSeasonEpisodes(tvShowId, seasonId, limit, offset).execute().body()
            ?: throw Exception("Season episodes are null")
    }

    // ✅ جلب الحلقات المرتبطة ببرنامج معيّن (حسب البرنامج وليس الموسم)
    suspend fun getEpisodesByProgram(programId: Long): List<EpisodeItem> = withContext(Dispatchers.IO) {
        apiService.getEpisodesByProgram(programId).execute().body()
            ?: emptyList()
    }
}
