package basem.com.propertysearch.common.utils.ui_operaitons


import dagger.Module
import dagger.Provides

@Module
class UiOperationsModule {
    @Provides
    fun provideUiOperations(): UiOperations {
        return UiOperations()
    }
}
