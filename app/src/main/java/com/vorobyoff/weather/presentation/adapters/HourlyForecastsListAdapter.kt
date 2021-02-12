package com.vorobyoff.weather.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vorobyoff.weather.databinding.ItemHourlyWeatherBinding
import com.vorobyoff.weather.presentation.adapters.HourlyForecastsListAdapter.HourlyForecastViewHolder
import com.vorobyoff.weather.presentation.models.OneHourWeatherForecastVO
import android.view.LayoutInflater.from as inflaterFrom
import com.vorobyoff.weather.databinding.ItemHourlyWeatherBinding.inflate as inflateBinding

class HourlyForecastsListAdapter : ListAdapter<OneHourWeatherForecastVO, HourlyForecastViewHolder>(callback) {

    companion object {
        val callback = object : ItemCallback<OneHourWeatherForecastVO>() {
            override fun areItemsTheSame(old: OneHourWeatherForecastVO, new: OneHourWeatherForecastVO) =
                old.date == new.date

            override fun areContentsTheSame(old: OneHourWeatherForecastVO, new: OneHourWeatherForecastVO) =
                old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val binding = inflateBinding(inflaterFrom(parent.context), parent, false)
        return HourlyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int): Unit =
        holder.bind(getItem(position))

    class HourlyForecastViewHolder(private val binding: ItemHourlyWeatherBinding) : ViewHolder(binding.root) {

        fun bind(forecast: OneHourWeatherForecastVO): Unit = with(binding) {
            temperatureTxt.text = "${forecast.temperature.value}"
            descriptionTxt.text = forecast.description
            timeTxt.text = forecast.date
        }
    }
}