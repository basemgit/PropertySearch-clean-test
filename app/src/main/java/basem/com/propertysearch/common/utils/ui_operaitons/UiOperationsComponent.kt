package basem.com.propertysearch.common.utils.ui_operaitons

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[UiOperationsModule::class])
interface UiOperationsComponent
{
    var uiOperations: UiOperations
}