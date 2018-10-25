package basem.com.propertysearch.screens.data.repository.home

import basem.com.propertysearch.common.api.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[HomeRepositoryModule::class,ApiModule::class])
interface HomeRepositoryComponent
{
    var homeRepository: HomeRepository
}