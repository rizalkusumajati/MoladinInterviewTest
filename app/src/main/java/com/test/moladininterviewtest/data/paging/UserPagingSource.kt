package com.test.moladininterviewtest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.moladininterviewtest.data.model.UserEntity
import com.test.moladininterviewtest.data.remote.Api
import com.test.moladininterviewtest.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val INITAL_PAGE = 1
const val NETWORK_PAGE_SIZE = 2

class UserPagingSource @Inject constructor (
    private val api: Api,
    @ApplicationModule.DispatcherIO private val dispatcher: CoroutineDispatcher
): PagingSource<Int, UserEntity.UserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, UserEntity.UserResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity.UserResponse> {
        val pageIndex = params.key ?: INITAL_PAGE
        return try {
            withContext(dispatcher){
                val response = api.getUserList(
                    page = pageIndex
                )
                val result = response.data
                val nextKey = if(result.isEmpty()){
                    null
                }else{
                    pageIndex + 1
                }

                LoadResult.Page(
                    data = result,
                    prevKey = if (pageIndex == INITAL_PAGE) null else pageIndex - 1,
                    nextKey = nextKey
                )
            }
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

}