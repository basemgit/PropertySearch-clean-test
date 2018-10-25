package basem.com.propertysearch.common.api

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[ApiModule::class])
interface ApiComponent
{
    var apiService:ApiService
}