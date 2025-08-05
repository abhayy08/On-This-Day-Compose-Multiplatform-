package com.abhay.onthisday.di

import com.abhay.onthisday.data.network.KtorRemoteDataSource
import com.abhay.onthisday.data.network.RemoteDataSource
import com.abhay.onthisday.data.network.client.HttpClientFactory
import com.abhay.onthisday.data.repository.RemoteDataRepositoryImpl
import com.abhay.onthisday.domain.repository.RemoteRepository
import com.abhay.onthisday.presentation.details.DetailsViewModel
import com.abhay.onthisday.presentation.home_screen.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module


val sharedModule = module {
    single {
        HttpClientFactory.create(get())
    }

    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()

    singleOf(::RemoteDataRepositoryImpl).bind<RemoteRepository>()

    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
}
