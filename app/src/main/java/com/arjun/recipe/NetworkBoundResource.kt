package com.arjun.recipe

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * @param RequestType - from network
 * @param ResultType - from db
 */
@FlowPreview
@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable.localizedMessage, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}