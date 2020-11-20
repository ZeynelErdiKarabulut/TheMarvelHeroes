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

package com.zeynelerdi.marvelheroes.di

import com.zeynelerdi.marvelheroes.model.Poster
import com.zeynelerdi.marvelheroes.network.MarvelClient
import com.zeynelerdi.marvelheroes.network.MarvelService
import com.zeynelerdi.marvelheroes.network.RequestInterceptor
import com.skydoves.sandwich.ResponseDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

  single {
    OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl("https://gist.githubusercontent.com/zeynelerdi/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single { get<Retrofit>().create(MarvelService::class.java) }

  single { MarvelClient(get()) }

  single { ResponseDataSource<List<Poster>>() }
}
