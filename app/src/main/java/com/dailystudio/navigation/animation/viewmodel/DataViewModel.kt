package com.dailystudio.navigation.animation.viewmodel

import android.app.Application
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ItemLayout
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val DEFAULT_LAYOUT = "simple"

        private const val TAG = "DataViewModel"

        private const val PREF_SELECTABLE_BACKGROUND = "selectable_background"
        private const val PREF_ITEM_LAYOUT = "item_layout"

        const val LAYOUT_SIMPLE = "simple"
        const val LAYOUT_IV_TV = "iv_tv"
        const val LAYOUT_CARD = "card"
    }

    private val _settingsPrefs = PreferenceManager.getDefaultSharedPreferences(application)

    private fun settingOfUseSelectableItemBackground(): Boolean {
        return _settingsPrefs.getBoolean(PREF_SELECTABLE_BACKGROUND, false)
    }

    private fun settingOfItemLayout(): String {
        return _settingsPrefs.getString(PREF_ITEM_LAYOUT, DEFAULT_LAYOUT) ?: DEFAULT_LAYOUT
    }

    private val _settingsPrefsChangedListener = OnSharedPreferenceChangeListener { _, key ->
        Log.d("ViewModel", "[AR] Pref[$key] changed")
        when(key) {
            "selectable_background" -> {
                _useSelectableItemBackground.value =
                    settingOfUseSelectableItemBackground()
            }

            "item_layout" -> {
                _layout.value =
                    settingOfItemLayout()
            }
        }
    }

    init {
        _settingsPrefs.registerOnSharedPreferenceChangeListener(_settingsPrefsChangedListener)
    }

    private val _useSelectableItemBackground: MutableStateFlow<Boolean> =
        MutableStateFlow(settingOfUseSelectableItemBackground())
    private val _layout: MutableStateFlow<String> =
        MutableStateFlow(settingOfItemLayout())

    private val itemLayout: StateFlow<ItemLayout> = _layout.combine(_useSelectableItemBackground) { layout, useItemBackground ->
        Log.d(TAG, "[AR] layout: $layout, useItemBackground: $useItemBackground")

        ItemLayout(
            itemLayoutId(layout, useItemBackground),
            selectableId(layout),
            rippleEnabled(useItemBackground),
            useCard(layout)
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ItemLayout(
            itemLayoutId(settingOfItemLayout(), settingOfUseSelectableItemBackground()),
            selectableId(settingOfItemLayout()),
            rippleEnabled(settingOfUseSelectableItemBackground()),
            useCard(settingOfItemLayout())
        )
    )

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


    private fun selectableId(layout: String): Int {
        return when(layout) {
            LAYOUT_SIMPLE -> {
                R.id.text
            }
            else -> R.id.selectable
        }
    }

    private fun itemLayoutId(layout: String, useItemBackground: Boolean): Int {
        return when(layout) {
            LAYOUT_SIMPLE -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_sel
                } else {
                    R.layout.layout_list_item
                }
            }
            LAYOUT_IV_TV -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_iv_tv_sel
                } else {
                    R.layout.layout_list_item_iv_tv
                }
            }
            LAYOUT_CARD -> {
                if (useItemBackground) {
                    R.layout.layout_list_item_iv_tv_card_sel
                } else {
                    R.layout.layout_list_item_iv_tv_card
                }
            }
            else -> R.layout.layout_list_item
        }
    }

    private fun rippleEnabled(useItemBackground: Boolean): Boolean {
        return useItemBackground
    }

    private fun useCard(layout: String): Boolean {
        return when(layout) {
            LAYOUT_CARD -> true
            else -> false
        }
    }

    fun updateItemLayout(layout: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _settingsPrefs.edit().putString(
                    PREF_ITEM_LAYOUT,
                    layout
                ).apply()
            }
        }
    }

    fun updateRippleEnabled(rippleEnabled: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _settingsPrefs.edit().putBoolean(
                    PREF_SELECTABLE_BACKGROUND,
                    rippleEnabled
                ).apply()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        _settingsPrefs.unregisterOnSharedPreferenceChangeListener(
            _settingsPrefsChangedListener)
    }
}