package com.example.daawahtv.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daawahtv.R
import com.example.daawahtv.network.EpisodeItem

class EpisodesAdapter(
    private val context: Context,
    private val episodes: List<EpisodeItem>,
    private val onEpisodeClick: (EpisodeItem) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_episode_card, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]
        holder.bind(episode)
        holder.itemView.setOnClickListener {
            onEpisodeClick(episode)
        }
    }

    override fun getItemCount(): Int = episodes.size

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val episodeTitleTextView: TextView = itemView.findViewById(R.id.episodeTitleTextView)

        fun bind(episode: EpisodeItem) {
            episodeTitleTextView.text = episode.title
        }
    }
}
