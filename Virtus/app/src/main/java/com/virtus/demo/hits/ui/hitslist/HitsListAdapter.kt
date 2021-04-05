package com.virtus.demo.hits.ui.hitslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virtus.demo.hits.data.entities.Hits
import com.virtus.demo.hits.databinding.ItemHitsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HitsListAdapter(private val listener: HitsItemListener) :
    RecyclerView.Adapter<HitsViewHolder>() {

    interface HitsItemListener {
        fun onClickedHits(url: String?)
    }

    private val items = ArrayList<Hits>()

    fun setItems(items: ArrayList<Hits>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HitsViewHolder {
        val binding: ItemHitsBinding =
            ItemHitsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HitsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HitsViewHolder, position: Int) =
        holder.bind(items[position])

    fun getHitsAt(position: Int):Hits{
        return items.get(position)
    }

    fun removeAt(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }
}

class HitsViewHolder(
    private val itemBinding: ItemHitsBinding,
    private val listener: HitsListAdapter.HitsItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var hits: Hits

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Hits) {
        this.hits = item
        itemBinding.name.text = if (item.title.isNullOrEmpty()) item.story_title else item.title
        itemBinding.speciesAndStatus.text = """${item.author}    -   ${durationFromNow(item.created_at) +" ago"}"""
    }

    fun durationFromNow(startDate: Date): String? {
        var different = System.currentTimeMillis() - startDate.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        var output = ""
        if (elapsedDays > 0) output += elapsedDays.toString() + "days "
        if (elapsedDays > 0 || elapsedHours > 0) output += "$elapsedHours hours "
        if (elapsedHours > 0 || elapsedMinutes > 0) output += "$elapsedMinutes minutes "
        if (elapsedMinutes > 0 || elapsedSeconds > 0) output += "$elapsedSeconds seconds"
        return output
    }

    override fun onClick(v: View?) {
        listener.onClickedHits(if (hits.url.isNullOrEmpty()) hits.story_url else hits.url)
    }
}


