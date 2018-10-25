package basem.com.propertysearch.common.utils


import dagger.Module
import dagger.Provides

@Module
class ConnectionCheckModule {

    @Provides
    fun provideConnection(): ConnectionCheck {
        return ConnectionCheck()
    }
}
