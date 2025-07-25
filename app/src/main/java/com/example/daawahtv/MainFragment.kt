package com.example.daawahtv

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.daawahtv.network.ApiClient
import com.example.daawahtv.network.ProgramItem
import com.example.daawahtv.repository.NetworkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class MainFragment : BrowseSupportFragment() {

    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private val TAG = "MainFragment"

    // ✅ إعداد الـ Repository بشكل صحيح
    private val repository = NetworkRepository(
        apiService = ApiClient.apiService,
        tvShowApiService = ApiClient.getTvShowApiService()
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareBackgroundManager()
        setupUIElements()
        loadContentFromApi()
        setupEventListeners()
    }

    override fun onResume() {
        super.onResume()
        updateBackground("https://daawah.tv/app1.jpg")
    }

    private fun loadContentFromApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = repository.getHomeContent()
                val homeResponse = response.body() ?: throw Exception("لا توجد بيانات في الاستجابة")

                val sliders = homeResponse.data.sliders
                val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
                val cardPresenter = CardPresenter()

                val staticItemsAdapter = ArrayObjectAdapter(cardPresenter)
                staticItemsAdapter.add(programItemToMovie(ProgramItem(-1, "البث المباشر", "https://daawah.tv/live.png", "live_stream"), "القائمة الرئيسية"))
                staticItemsAdapter.add(programItemToMovie(ProgramItem(-2, "خريطة البرامج", "https://daawah.tv/privacy.png", "programs_list"), "القائمة الرئيسية"))
                staticItemsAdapter.add(programItemToMovie(ProgramItem(-3, "سياسة الخصوصية", "https://daawah.tv/privacy.png", "privacy_policy"), "القائمة الرئيسية"))
                staticItemsAdapter.add(programItemToMovie(ProgramItem(-4, "برامج الأطفال", "https://daawah.tv/kids_icon.png", "kids_programs"), "القائمة الرئيسية"))

                rowsAdapter.add(ListRow(HeaderItem(0, "القائمة الرئيسية"), staticItemsAdapter))

                sliders?.forEachIndexed { index, slider ->
                    val rowAdapter = ArrayObjectAdapter(cardPresenter)
                    slider.data.forEach { item ->
                        rowAdapter.add(programItemToMovie(item, slider.title))
                    }
                    rowsAdapter.add(ListRow(HeaderItem(index + 1L, slider.title), rowAdapter))
                }

                adapter = rowsAdapter

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "فشل تحميل المحتوى: ${e.message}", e)
                Toast.makeText(requireContext(), "فشل تحميل المحتوى: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun programItemToMovie(item: ProgramItem, sliderTitle: String): Movie {
        return Movie().apply {
            id = item.id.toLong()
            title = item.title
            description = sliderTitle
            cardImageUrl = item.image ?: ""
            type = item.post_type
        }
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(requireContext(), "لم يتم تفعيل البحث بعد", Toast.LENGTH_LONG).show()
        }
        onItemViewClickedListener = ItemViewClickedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is Movie) {
                when (item.type) {
                    "live_stream" -> {
                        startActivity(Intent(context, PlaybackActivity::class.java).apply {
                            putExtra("url", "http://161.97.100.71/hls/stream.m3u8")
                        })
                    }
                    "programs_list" -> {
                        startActivity(Intent(context, ProgramsWebViewActivity::class.java))
                    }
                    "tv_show" -> {
                        startActivity(Intent(context, TvShowDetailsActivity::class.java).apply {
                            putExtra("tvShowId", item.id)
                        })
                    }
                    "privacy_policy" -> {
                        startActivity(Intent(context, PrivacyPolicyActivity::class.java))
                    }
                    "kids_programs" -> {
                        startActivity(Intent(context, KidsActivity::class.java))
                    }
                    else -> {
                        Toast.makeText(context, "نوع غير مدعوم: ${item.type}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(requireContext())
            .load(uri)
            .centerCrop()
            .error(mDefaultBackground)
            .into(object : SimpleTarget<Drawable>(width, height) {
                override fun onResourceReady(drawable: Drawable, transition: Transition<in Drawable>?) {
                    mBackgroundManager.drawable = drawable
                }
            })
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(requireActivity())
        mBackgroundManager.attach(requireActivity().window)
        mDefaultBackground = ContextCompat.getDrawable(requireContext(), R.drawable.default_background)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.search_opaque)
    }
}
