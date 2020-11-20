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
import com.zeynelerdi.marvelheroes.databinding.ItemPosterBinding
import com.zeynelerdi.marvelheroes.model.Poster
import com.zeynelerdi.marvelheroes.view.ui.details.PosterDetailActivity

class PosterAdapter : RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {

  private val items = mutableListOf<Poster>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemPosterBinding>(
      inflater, R.layout.item_poster, parent, false)
    return PosterViewHolder(binding)
  }

  fun updatePosterList(posters: List<Poster>) {
    items.clear()
    items.addAll(posters)
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
    val item = items[position]
    holder.binding.apply {
      poster = item
      executePendingBindings()
      root.setOnClickListener {
        PosterDetailActivity.startActivity(it.context, transformationLayout, item)
      }
    }
  }

  fun getPoster(index: Int): Poster = items[index]

  class PosterViewHolder(val binding: ItemPosterBinding) :
    RecyclerView.ViewHolder(binding.root)
}
