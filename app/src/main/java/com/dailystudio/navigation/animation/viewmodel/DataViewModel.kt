package com.dailystudio.navigation.animation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ItemLayout
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class DataViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val DEFAULT_LAYOUT = "simple"

        private const val TAG = "DataViewModel"
    }

    private val _settings = PreferenceManager.getDefaultSharedPreferences(application)

    private val _useSelectableItemBackground: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    private val _layout: MutableStateFlow<String> =
        MutableStateFlow(DEFAULT_LAYOUT)

    private val itemLayout: StateFlow<ItemLayout> = _layout.combine(_useSelectableItemBackground) { layout, useItemBackground ->
        Log.d(TAG, "[AR] layout: $layout, useItemBackground: $useItemBackground")
        val layoutId = when(layout) {
            "simple" -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_sel
                } else {
                    R.layout.layout_list_item
                }
            }
            "iv_tv" -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_iv_tv_sel
                } else {
                    R.layout.layout_list_item_iv_tv
                }
            }
            "card" -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_iv_tv_card_sel
                } else {
                    R.layout.layout_list_item_iv_tv_card
                }
            }
            else -> R.layout.layout_list_item
        }

        val selectableId = when(layout) {
            "simple" -> {
                R.id.text
            }
            else -> R.id.selectable
        }

        ItemLayout(layoutId, selectableId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ItemLayout())

    val primaryList: Flow<ListData> = flow {
        emit(('A'..'Z').map {
            Item(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        })
    }.combine(itemLayout) { list, layout ->
        ListData(list, layout)
    }

    val secondaryList: Flow<ListData> = flow {
        emit((1..50).map {
            Item(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        })
    }.combine(itemLayout) { list, layout ->
        ListData(list, layout)
    }

    init {
        _settings.registerOnSharedPreferenceChangeListener { prefs, key ->
            Log.d("ViewModel", "[AR] Pref[$key] changed")
            when(key) {
                "selectable_background" -> {
                    _useSelectableItemBackground.value =
                        prefs.getBoolean(key, false)
                }
                "item_layout" -> {
                    _layout.value =
                        prefs.getString(key, DEFAULT_LAYOUT) ?: DEFAULT_LAYOUT
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

//        _settings.unregisterOnSharedPreferenceChangeListener()
    }
}