package com.vorobyoff.weather.presentation.adapter

import android.view.LayoutInflater.from as inflateFrom
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vorobyoff.weather.databinding.ItemCityBinding
import com.vorobyoff.weather.databinding.ItemCityBinding.inflate
import com.vorobyoff.weather.presentation.adapter.CityListAdapter.CityViewHolder
import com.vorobyoff.weather.presentation.model.CityItem

class CityListAdapter(private val onItemClickListener: ((CityItem) -> Unit)? = null) :
    ListAdapter<CityItem, CityViewHolder>(itemCallback) {

    companion object {
        val itemCallback = object : ItemCallback<CityItem>() {
            override fun areItemsTheSame(old: CityItem, new: CityItem): Boolean =
                old.locationKey == new.locationKey

            override fun areContentsTheSame(old: CityItem, new: CityItem): Boolean = old == new
        }
    }

    private lateinit var binding: ItemCityBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        binding = inflate(inflateFrom(parent.context), parent, false)
        return CityViewHolder(binding = binding, onItemClickListener = onItemClickListener)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int): Unit =
        holder.bind(getItem(position))

    class CityViewHolder(
        private val binding: ItemCityBinding,
        private inline val onItemClickListener: ((CityItem) -> Unit)?
    ) : ViewHolder(binding.root) {

        fun bind(item: CityItem): Unit = with(binding) {
            root.setOnClickListener { onItemClickListener?.invoke(item) }
            countryTxt.text = item.country
            cityTxt.text = item.name
        }
    }
}