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

package com.zeynelerdi.marvelheroes.view.ui.main

import android.os.Bundle
import com.zeynelerdi.marvelheroes.R
import com.zeynelerdi.marvelheroes.base.DatabindingActivity
import com.zeynelerdi.marvelheroes.databinding.ActivityMainBinding
import com.zeynelerdi.marvelheroes.view.adapter.PosterAdapter
import com.skydoves.transformationlayout.onTransformationStartContainer
import org.koin.android.viewmodel.ext.android.getViewModel

class MainActivity : DatabindingActivity() {

  private val binding: ActivityMainBinding by binding(R.layout.activity_main)

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationStartContainer()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@MainActivity
      vm = getViewModel<MainViewModel>().apply { fetchMarvelPosterList() }
      adapter = PosterAdapter()
    }
  }
}
