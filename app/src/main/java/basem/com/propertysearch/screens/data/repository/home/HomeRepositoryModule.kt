package basem.com.propertysearch.screens.data.repository.home


import dagger.Module
import dagger.Provides

@Module
class HomeRepositoryModule {
    @Provides
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }
}
