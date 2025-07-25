package com.example.daawahtv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daawahtv.network.BannerItem

/**
 * محول (Adapter) لـ ViewPager2 لعرض عناصر البانر في صفحة الأطفال.
 *
 * @param items قائمة عناصر البانر (BannerItem) للعرض.
 * @param onClick دالة lambda يتم استدعاؤها عند النقر على عنصر بانر.
 */
class KidsBannerAdapter(
    private val items: List<BannerItem>,
    private val onClick: (BannerItem) -> Unit
) : RecyclerView.Adapter<KidsBannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // إنشاء ViewHolder باستخدام تخطيط item_kids_banner
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kids_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // ربط البيانات بالعنصر في الموضع المحدد
        val item = items[position]
        holder.bind(item)
        // تعيين مستمع النقر للعنصر
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size // إرجاع عدد العناصر في القائمة

    /**
     * ViewHolder لعرض عنصر بانر فردي.
     * @param view طريقة العرض (View) للعنصر.
     */
    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.bannerImageView)
        private val title: TextView = view.findViewById(R.id.bannerTitleTextView)

        /**
         * ربط بيانات BannerItem بطرق العرض.
         * @param item كائن BannerItem لربطه.
         */
        fun bind(item: BannerItem) {
            title.text = item.title // تعيين عنوان البانر
            // تحميل صورة البانر باستخدام Glide
            Glide.with(itemView).load(item.image).into(image)
        }
    }
}
