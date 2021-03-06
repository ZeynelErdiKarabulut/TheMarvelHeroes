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

package com.zeynelerdi.marvelheroes.binding

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.zeynelerdi.marvelheroes.extensions.circularRevealedAtCenter
import com.zeynelerdi.marvelheroes.model.Poster
import com.zeynelerdi.marvelheroes.model.PosterDetails
import com.zeynelerdi.marvelheroes.view.adapter.PosterAdapter
import com.zeynelerdi.marvelheroes.view.adapter.PosterSeriesAdapter
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullOrEmpty
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: RecyclerView.Adapter<*>) {
  view.adapter = baseAdapter
}

@BindingAdapter("toast")
fun bindToast(view: RecyclerView, text: LiveData<String>) {
  text.value.whatIfNotNull {
    Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
  }
}

@BindingAdapter("adapterPosterList")
fun bindAdapterPosterList(view: DiscreteScrollView, posters: List<Poster>?) {
  posters.whatIfNotNullOrEmpty {
    (view.adapter as? PosterAdapter)?.updatePosterList(it)
  }
  view.setItemTransformer(ScaleTransformer.Builder()
    .setMaxScale(1.25f)
    .setMinScale(0.8f)
    .build())
}

@BindingAdapter("bindOnItemChanged", "bindOnItemChangedBackground")
fun bindOnItemChanged(view: DiscreteScrollView, adapter: PosterAdapter, pointView: View) {
  view.addOnItemChangedListener { viewHolder, _ ->
    viewHolder?.adapterPosition.whatIfNotNull {
      pointView.circularRevealedAtCenter(Color.parseColor(adapter.getPoster(it).color))
    }
  }
}

@BindingAdapter("adapterPosterSeries", "adapterPosterDetailsList")
fun bindAdapterPosterDetailsList(
  view: RecyclerView,
  adapter: PosterSeriesAdapter,
  posters: List<PosterDetails>?
) {
  posters.whatIfNotNullOrEmpty {
    view.adapter = adapter.apply { updatePosterDetailsList(it) }
  }
}
