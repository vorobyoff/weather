package com.vorobyoff.weather.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vorobyoff.weather.databinding.ItemDailyWeatherBinding
import com.vorobyoff.weather.presentation.adapters.DailyForecastsListAdapter.DayForecastViewHolder
import com.vorobyoff.weather.presentation.models.OneDayWeatherForecastVO
import android.view.LayoutInflater.from as inflaterFrom
import com.vorobyoff.weather.databinding.ItemDailyWeatherBinding.inflate as inflateBinding

class DailyForecastsListAdapter : ListAdapter<OneDayWeatherForecastVO, DayForecastViewHolder>(callback) {

    companion object {
        val callback = object : ItemCallback<OneDayWeatherForecastVO>() {
            override fun areItemsTheSame(old: OneDayWeatherForecastVO, new: OneDayWeatherForecastVO) =
                old.date == new.date

            override fun areContentsTheSame(old: OneDayWeatherForecastVO, new: OneDayWeatherForecastVO) =
                old == new
        }
    }

    private lateinit var binding: ItemDailyWeatherBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayForecastViewHolder {
        binding = inflateBinding(inflaterFrom(parent.context), parent, false)
        return DayForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayForecastViewHolder, position: Int): Unit =
        holder.bind(getItem(position))

    class DayForecastViewHolder(private val binding: ItemDailyWeatherBinding) : ViewHolder(binding.root) {

        fun bind(forecast: OneDayWeatherForecastVO): Unit = with(binding) {
            nightTempTxt.text = "${forecast.temperature.minimum}"
            dayTempTxt.text = "${forecast.temperature.maximum}"
            descriptionTxt.text = forecast.description
            dateTxt.text = forecast.date
        }
    }
}