package basem.com.propertysearch.common.api

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }


        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun<T> error(error: Throwable): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }
    }
}