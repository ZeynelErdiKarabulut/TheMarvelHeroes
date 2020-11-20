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

package com.zeynelerdi.marvelheroes.view.ui.details

import com.zeynelerdi.marvelheroes.base.LiveCoroutinesViewModel
import com.zeynelerdi.marvelheroes.repository.DetailRepository

class PosterDetailViewModel(
  private val repository: DetailRepository
) : LiveCoroutinesViewModel() {

  fun getPoster(id: Long) = repository.getPosterById(id)
}
