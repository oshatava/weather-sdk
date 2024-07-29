package com.osh.weather_sdk.android.fragments.weather_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osh.weather_sdk.android.fragments.R
import com.osh.weather_sdk.android.fragments.databinding.ItemWeatherHourlyBinding
import com.osh.weather_sdk.android.fragments.utils.formatAsTime
import com.osh.weather_sdk.android.fragments.utils.toLocalDateTime
import com.osh.weather_sdk.common.model.ForecastItem

internal class WeatherScreenAdapter :
    ListAdapter<ForecastItem, WeatherScreenAdapter.ForecastItemViewHolder>(DiffUtilImpl) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        return ForecastItemViewHolder(
            ItemWeatherHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForecastItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal inner class ForecastItemViewHolder(
        private val binding: ItemWeatherHourlyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastItem){
            val resources = binding.root.resources
            binding.time.text = item.timestampLocal.toLocalDateTime().formatAsTime()
            binding.temperature.text = resources.getString(R.string.weather_screen_city_temperature_template, item.temperature)
            binding.description.text = item.description
        }
    }

    companion object DiffUtilImpl : DiffUtil.ItemCallback<ForecastItem>() {
        override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return oldItem.timestampLocal == newItem.timestampLocal
        }

        override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return oldItem == newItem
        }
    }
}
