/*
 * Designed and developed by 2020 zeynelerdi zeynel erdi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zeynelerdi.marvelheroes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zeynelerdi.marvelheroes.R
import com.zeynelerdi.marvelheroes.databinding.ItemPosterSeriesBinding
import com.zeynelerdi.marvelheroes.databinding.LayoutPlotBinding
import com.zeynelerdi.marvelheroes.model.PosterDetails

class PosterSeriesAdapter(
  private val layoutPlotBinding: LayoutPlotBinding
) : RecyclerView.Adapter<PosterSeriesAdapter.PosterSeriesViewHolder>() {

  private val items = mutableListOf<PosterDetails>()
  private var selectable = true

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PosterSeriesViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding =
      DataBindingUtil.inflate<ItemPosterSeriesBinding>(inflater, R.layout.item_poster_series,
        parent, false)
    return PosterSeriesViewHolder(binding)
  }

  fun updatePosterDetailsList(posterDetails: List<PosterDetails>) {
    items.clear()
    items.addAll(posterDetails)
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(
    holder: PosterSeriesViewHolder,
    position: Int
  ) {
    val item = items[position]
    holder.binding.apply {
      details = item
      executePendingBindings()
      root.setOnClickListener {
        if (selectable) {
          selectable = false
          layoutPlotBinding.details = item
          layoutPlotBinding.root.setOnClickListener {
            holder.binding.transformationLayout.finishTransform()
            selectable = true
          }
          layoutPlotBinding.executePendingBindings()
          holder.binding.transformationLayout.bindTargetView(layoutPlotBinding.root)
          holder.binding.transformationLayout.startTransform()
        }
      }
    }
  }

  class PosterSeriesViewHolder(val binding: ItemPosterSeriesBinding) :
    RecyclerView.ViewHolder(binding.root)
}
