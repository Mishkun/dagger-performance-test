package di

import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton

/**
 * @author themishkun on 15/09/2018.
 */
@Module
class MainModule {
    @Provides
    @Singleton
    fun api(): Api = Api
}

@Scope
annotation class PerFeature

object Api
class Repository(val api: Api)
class Interactor(val repo: Repository)
class Presenter(val interactor: Interactor)