package com.remydupont.spotifyrd.models

/**
 * CategoriesModels
 *
 * Created by remydupont on 09/06/2018.
 */
data class CategoriesResponse(
        var categories: Categories? = null
)

data class Categories(
        var href: String? = null,
        var items: List<Category>? = null,
        var limit: Int? = null,
        var next: String? = null,
        var offset: Int? = null,
        var previous: String? = null,
        var total: Int? = null
)

data class Category(
        var href: String? = null,
        var icons: List<Image>? = null,
        var id: String? = null,
        var name: String? = null
)