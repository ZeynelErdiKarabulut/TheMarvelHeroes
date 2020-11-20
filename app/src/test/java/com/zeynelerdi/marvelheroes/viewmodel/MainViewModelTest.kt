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

package com.zeynelerdi.marvelheroes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.zeynelerdi.marvelheroes.MainCoroutinesRule
import com.zeynelerdi.marvelheroes.model.Poster
import com.zeynelerdi.marvelheroes.network.MarvelClient
import com.zeynelerdi.marvelheroes.network.MarvelService
import com.zeynelerdi.marvelheroes.persistence.PosterDao
import com.zeynelerdi.marvelheroes.repository.MainRepository
import com.zeynelerdi.marvelheroes.utils.MockTestUtil
import com.zeynelerdi.marvelheroes.view.ui.main.MainViewModel
import com.skydoves.sandwich.ResponseDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

  private lateinit var viewModel: MainViewModel
  private lateinit var mainRepository: MainRepository
  private val marvelService: MarvelService = mock()
  private val marvelClient: MarvelClient = MarvelClient(marvelService)
  private val posterDao: PosterDao = mock()
  private val dataSource: ResponseDataSource<List<Poster>> = mock()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    mainRepository = MainRepository(marvelClient, dataSource, posterDao)
    viewModel = MainViewModel(mainRepository)
  }

  @Test
  fun fetchMarvelPosterListTest() = runBlocking {
    val mockData = MockTestUtil.mockPosterList()
    whenever(posterDao.getPosterList()).thenReturn(mockData)

    val fetchedData = mainRepository.loadMarvelPosters { }
    val observer: Observer<List<Poster>> = mock()
    fetchedData.observeForever(observer)

    viewModel.fetchMarvelPosterList()

    verify(posterDao, atLeastOnce()).getPosterList()
    verify(observer).onChanged(mockData)
    fetchedData.removeObserver(observer)
  }
}
