package com.davant.cefiremybookshelf.domain.repository

import com.davant.cefiremybookshelf.domain.model.Cover

interface CoversRepository {
    suspend fun getCoverByIsbn(isbn:String):Cover
}