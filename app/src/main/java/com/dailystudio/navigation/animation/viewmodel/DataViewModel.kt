package com.dailystudio.navigation.animation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dailystudio.navigation.animation.data.ListData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataViewModel(application: Application): AndroidViewModel(application) {

    val primaryList: Flow<List<ListData>> = flow {
       emit(('A'..'Z').map {
            ListData(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        })
    }

    val secondaryList: Flow<List<ListData>> = flow {
       emit((1..50).map {
            ListData(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        })
    }
}