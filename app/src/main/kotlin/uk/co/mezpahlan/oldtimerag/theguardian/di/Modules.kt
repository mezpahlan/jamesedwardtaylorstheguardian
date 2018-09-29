package uk.co.mezpahlan.oldtimerag.theguardian.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import uk.co.mezpahlan.oldtimerag.theguardian.data.TheGuardianRepository
import uk.co.mezpahlan.oldtimerag.theguardian.data.network.TheGuardianOpenPlatformServiceGenerator
import uk.co.mezpahlan.oldtimerag.theguardian.viewmodels.SharedViewModel

val theGuardianModule = module {
    single { TheGuardianRepository(get("feed_service"), get("article_service")) }

    viewModel { SharedViewModel(get()) }
}

val networkModule = module {

    factory("feed_service") { TheGuardianOpenPlatformServiceGenerator.createFeedService() }

    factory("article_service") { TheGuardianOpenPlatformServiceGenerator.createArticleService() }
}

val appModules = listOf(theGuardianModule, networkModule)