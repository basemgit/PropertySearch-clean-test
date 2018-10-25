package basem.com.propertysearch.screens.data.repository.searchResults


import dagger.Module
import dagger.Provides

@Module
class SearchResultsRepositoryModule {
    @Provides
    fun provideSearchResultsRepository(): SearchResultsRepository {
        return SearchResultsRepository()
    }
}
