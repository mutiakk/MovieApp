package com.example.movieappbisa.entity

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

object PagedUtil {
    fun <T> mockPagedList(list: List<T>): PagedList<*> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<*>
        Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}