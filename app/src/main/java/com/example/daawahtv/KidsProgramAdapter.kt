package com.example.daawahtv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daawahtv.network.ProgramItem

/**
 * محول (Adapter) لـ RecyclerView لعرض برامج الأطفال.
 *
 * @param items قائمة عناصر البرنامج (ProgramItem) للعرض.
 * @param onClick دالة lambda يتم استدعاؤها عند النقر على عنصر برنامج.
 */
class KidsProgramAdapter(
    private val items: List<ProgramItem>,
    private val onClick: (ProgramItem) -> Unit
) : RecyclerView.Adapter<KidsProgramAdapter.ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        // إنشاء ViewHolder باستخدام تخطيط item_kids_program
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kids_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        // ربط البيانات بالعنصر في الموضع المحدد
        val item = items[position]
        holder.bind(item)
        // تعيين مستمع النقر للعنصر
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size // إرجاع عدد العناصر في القائمة

    /**
     * ViewHolder لعرض عنصر برنامج فردي.
     * @param view طريقة العرض (View) للعنصر.
     */
    inner class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.programImageView)
        private val title: TextView = view.findViewById(R.id.programTitleTextView)

        /**
         * ربط بيانات ProgramItem بطرق العرض.
         * @param item كائن ProgramItem لربطه.
         */
        fun bind(item: ProgramItem) {
            title.text = item.title // تعيين عنوان البرنامج
            // تحميل صورة البرنامج باستخدام Glide
            Glide.with(itemView).load(item.image).into(image)
        }
    }
}
