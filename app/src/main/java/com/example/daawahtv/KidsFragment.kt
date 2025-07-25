package com.example.daawahtv

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.daawahtv.network.BannerItem
import com.example.daawahtv.network.ProgramItem
import com.example.daawahtv.repository.NetworkRepository
import com.example.daawahtv.network.ApiClient
import com.example.daawahtv.network.HomeResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope


class KidsFragment : Fragment() {

    private val repository = NetworkRepository(
        apiService = ApiClient.apiService,
        tvShowApiService = ApiClient.getTvShowApiService()
    )

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var programsRecyclerView: RecyclerView
    private lateinit var backgroundImageView: ImageView

    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = object : Runnable {
        override fun run() {
            val next = (bannerViewPager.currentItem + 1) % (bannerViewPager.adapter?.itemCount ?: 1)
            bannerViewPager.currentItem = next
            sliderHandler.postDelayed(this, 4000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kids, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerViewPager = view.findViewById(R.id.bannerViewPager)
        programsRecyclerView = view.findViewById(R.id.programsRecyclerView)
        backgroundImageView = view.findViewById(R.id.backgroundImageView)

        Glide.with(this)
            .load("https://daawah.tv/app/kids.jpg")
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    backgroundImageView.setImageDrawable(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        bannerViewPager.setPageTransformer(ScalePageTransformer())
        programsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)

        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = repository.getHomeContent()
                if (response.isSuccessful) {
                    val homeResponse = response.body()
                    homeResponse?.let {
                        val data = it.data
                        val bannerItems = data.banner ?: emptyList()
                        val kidsRow = data.sliders?.find { slider -> slider.title == "واحة الأطفال" }
                        val programItems = kidsRow?.data ?: emptyList()

                        setupBanner(bannerItems)
                        setupPrograms(programItems)
                    } ?: run {
                        Log.e("KidsFragment", "HomeResponse body is null")
                        Toast.makeText(requireContext(), "خطأ: استجابة API فارغة.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("KidsFragment", "API call failed: ${response.code()} - ${response.message()}")
                    Toast.makeText(requireContext(), "فشل تحميل محتوى الأطفال: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Log.e("KidsFragment", "Error loading data for Kids section: ${e.message}", e)
                Toast.makeText(requireContext(), "فشل تحميل محتوى الأطفال. يرجى التحقق من اتصال الإنترنت.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupBanner(items: List<BannerItem>) {
        bannerViewPager.adapter = KidsBannerAdapter(items) { banner ->
            val intent = Intent(requireContext(), TvShowDetailsActivity::class.java)
            intent.putExtra("tvShowId", banner.id.toLong())
            startActivity(intent)
        }
        sliderHandler.postDelayed(sliderRunnable, 4000)
    }

    private fun setupPrograms(items: List<ProgramItem>) {
        programsRecyclerView.adapter = KidsProgramAdapter(items) { program ->
            when (program.post_type) {
                "tv_show" -> {
                    val intent = Intent(requireContext(), TvShowDetailsActivity::class.java)
                    intent.putExtra("tvShowId", program.id.toLong())
                    startActivity(intent)
                }
                "video" -> {
                    val intent = Intent(requireContext(), PlaybackActivity::class.java)
                    intent.putExtra("url", "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4")
                    intent.putExtra("EPISODE_ID", program.id.toLong())
                    startActivity(intent)
                }
                "youtube_video" -> {
                    val intent = Intent(requireContext(), YouTubePlayerActivity::class.java)
                    intent.putExtra("VIDEO_URL", "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(context, "نوع محتوى الأطفال غير مدعوم: ${program.post_type}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (bannerViewPager.adapter != null) {
            sliderHandler.postDelayed(sliderRunnable, 4000)
        }
    }

    override fun onPause() {
        sliderHandler.removeCallbacks(sliderRunnable)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}
