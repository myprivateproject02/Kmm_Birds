package di

import common.Constant.BASE_URL
import data.repository.RemoteDataRepositoryImpl
import domain.repository.RemoteDataRepository
import domain.use_case.GetBirdsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.ui.home.HomeViewModel

fun initKoin() = run {
    startKoin {
        modules(
            ktorModule,
            repositoryModule,
            useCasesModule,
            viewmodelModule,
        )
    }
}


val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { BASE_URL }
}

val repositoryModule = module {
    single<RemoteDataRepository> { RemoteDataRepositoryImpl(get(), get()) }
}

val viewmodelModule = module {
    factory { HomeViewModel(get()) }
}

val useCasesModule: Module = module {
    factory { GetBirdsUseCase(get()) }
}

object ViewModelProvider : KoinComponent {
    fun getBirdViewModel() = get<HomeViewModel>()
}
class KoinHelper: KoinComponent {
    fun getAppViewModel() = get<HomeViewModel>()
}