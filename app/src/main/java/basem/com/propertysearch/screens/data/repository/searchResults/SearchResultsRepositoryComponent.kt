package basem.com.propertysearch.screens.data.repository.searchResults

import basem.com.propertysearch.common.api.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[SearchResultsRepositoryModule::class,ApiModule::class])
interface SearchResultsRepositoryComponent
{
    var searchResultsRepository: SearchResultsRepository
}