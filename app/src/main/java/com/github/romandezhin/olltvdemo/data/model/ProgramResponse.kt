package com.github.romandezhin.olltvdemo.data.model

import com.google.gson.annotations.SerializedName

data class ProgramResponse(
    @SerializedName("items") val items: List<Item>,
    @SerializedName("items_number") val itemsCount: Int,
    @SerializedName("total") val totalCount: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("hasMore") val hasMore: Int,
    )

data class Item(
    @SerializedName("id") val id: Int,
    @SerializedName("icon") val icon: String,
    @SerializedName("name") val name: String,
    )
