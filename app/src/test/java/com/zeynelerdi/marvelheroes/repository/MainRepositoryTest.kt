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

@file:Suppress("SpellCheckingInspection")

package com.zeynelerdi.marvelheroes.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.zeynelerdi.marvelheroes.MainCoroutinesRule
import com.zeynelerdi.marvelheroes.model.Poster
import com.zeynelerdi.marvelheroes.network.ApiUtil.getCall
import com.zeynelerdi.marvelheroes.network.MarvelClient
import com.zeynelerdi.marvelheroes.network.MarvelService
import com.zeynelerdi.marvelheroes.persistence.PosterDao
import com.zeynelerdi.marvelheroes.utils.MockTestUtil.mockPosterList
import com.skydoves.sandwich.ResponseDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainRepositoryTest {

  private lateinit var repository: MainRepository
  private lateinit var client: MarvelClient
  private val service: MarvelService = mock()
  private val posterDao: PosterDao = mock()
  private val dataSource: ResponseDataSource<List<Poster>> = ResponseDataSource()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    client = MarvelClient(service)
    repository = MainRepository(client, dataSource, posterDao)
  }

  @Test
  fun loadMarvelPostersTest() = runBlocking {
    val mockData = mockPosterList()
    whenever(posterDao.getPosterList()).thenReturn(emptyList())
    whenever(service.fetchMarvelPosterList()).thenReturn(getCall(mockData))

    val loadData = repository.loadMarvelPosters { }
    verify(posterDao, atLeastOnce()).getPosterList()
    verify(service, atLeastOnce()).fetchMarvelPosterList()

    val observer: Observer<List<Poster>> = mock()
    loadData.observeForever(observer)

    val updatedData = mockPosterList()
    whenever(posterDao.getPosterList()).thenReturn(updatedData)

    loadData.postValue(updatedData)
    verify(observer).onChanged(updatedData)
    loadData.removeObserver(observer)
  }
}
