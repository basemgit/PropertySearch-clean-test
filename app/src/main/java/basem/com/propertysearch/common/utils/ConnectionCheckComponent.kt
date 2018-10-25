package basem.com.propertysearch.common.utils

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[ConnectionCheckModule::class])
interface ConnectionCheckComponent
{


    var connection:ConnectionCheck
}