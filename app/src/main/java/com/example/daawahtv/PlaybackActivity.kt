package com.example.daawahtv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.example.daawahtv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.daawahtv.network.EpisodeDetailsResponse

@UnstableApi
class PlaybackActivity : Activity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private var episodeIds: LongArray? = null
    private var currentPosition: Int = 0
    private var currentEpisodeId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        playerView = PlayerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        setContentView(playerView)

        episodeIds = intent.getLongArrayExtra("EPISODE_IDS")
        currentPosition = intent.getIntExtra("CURRENT_POSITION", 0)
        currentEpisodeId = intent.getLongExtra("EPISODE_ID", -1L)

        val streamUrl = intent.getStringExtra("url")
        if (currentEpisodeId == -1L && episodeIds != null && currentPosition < episodeIds!!.size) {
            currentEpisodeId = episodeIds!![currentPosition]
        }

        if (streamUrl.isNullOrEmpty()) {
            Toast.makeText(this, "رابط الفيديو غير موجود", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initPlayer()
        val resumePosition = PlaybackPositionManager.getPosition(this, currentEpisodeId)
        playUrl(streamUrl, resumePosition)
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this).build()
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    playNextEpisode()
                }
            }
        })
        playerView.player = player
    }

    private fun playUrl(url: String, startPosition: Long = 0L) {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")

        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMimeType(if (url.endsWith(".m3u8")) MimeTypes.APPLICATION_M3U8 else MimeTypes.APPLICATION_MP4)
            .build()

        val mediaSource: MediaSource = if (url.endsWith(".m3u8")) {
            HlsMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
        } else {
            ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
        }

        player.setMediaSource(mediaSource)
        player.prepare()
        if (startPosition > 0) {
            player.seekTo(startPosition)
        }
        player.playWhenReady = true
    }

    private fun playNextEpisode() {
        val ids = episodeIds ?: return
        if (currentPosition + 1 >= ids.size) {
            finish()
            return
        }
        currentPosition++
        val nextId = ids[currentPosition]
        PlaybackPositionManager.savePosition(this, currentEpisodeId, 0)
        currentEpisodeId = nextId

        Toast.makeText(this, "تشغيل الحلقة التالية...", Toast.LENGTH_SHORT).show()

        ApiClient.getTvShowApiService().getEpisodeDetails(nextId.toInt())
            .enqueue(object : Callback<EpisodeDetailsResponse> {
                override fun onResponse(
                    call: Call<EpisodeDetailsResponse>,
                    response: Response<EpisodeDetailsResponse>
                ) {
                    val episode = response.body()?.data
                    var url = episode?.url_link
                    if (url.isNullOrEmpty()) {
                        url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                    }

                    if (url.contains("youtube.com") || url.contains("youtu.be")) {
                        val intent = Intent(this@PlaybackActivity, YouTubePlayerActivity::class.java).apply {
                            putExtra("VIDEO_URL", url)
                            putExtra("EPISODE_IDS", ids)
                            putExtra("CURRENT_POSITION", currentPosition)
                            putExtra("EPISODE_ID", nextId)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        val startPos = PlaybackPositionManager.getPosition(this@PlaybackActivity, currentEpisodeId)
                        playUrl(url, startPos)
                    }
                }

                override fun onFailure(call: Call<EpisodeDetailsResponse>, t: Throwable) {
                    Toast.makeText(this@PlaybackActivity, "فشل تحميل الحلقة التالية", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
    }

    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) {
            PlaybackPositionManager.savePosition(this, currentEpisodeId, player.currentPosition)
            player.release()
        }
    }

    override fun onBackPressed() {
        if (::player.isInitialized) {
            PlaybackPositionManager.savePosition(this, currentEpisodeId, player.currentPosition)
            player.release()
        }
        super.onBackPressed()
    }
}
