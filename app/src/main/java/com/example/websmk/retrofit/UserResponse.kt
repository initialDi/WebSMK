package com.example.websmk.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("data")
    @Expose
    var data: petugas? = null

    class petugas {
        @SerializedName("username")
        @Expose
        var username :String? = null

        @SerializedName("nama")
        @Expose
        var nama :String? = null

        @SerializedName("level")
        @Expose
        var level :String? = null

    }
}