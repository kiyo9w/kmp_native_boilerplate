package dev.kiyo9w.kmpboilerplate.feature.catalog

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import dev.kiyo9w.kmpboilerplate.core.AppResult
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogItem
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import dev.kiyo9w.kmpboilerplate.domain.session.SessionStore
import dev.kiyo9w.kmpboilerplate.platform.AppVersion
import dev.kiyo9w.kmpboilerplate.platform.AppVersionReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogListViewModel(
    private val catalogRepository: CatalogRepository,
    private val sessionStore: SessionStore,
    appVersionReader: AppVersionReader,
) : ViewModel() {
    val appVersion: AppVersion = appVersionReader.current()

    @NativeCoroutinesState
    val items: StateFlow<List<CatalogItem>> =
        catalogRepository.observeItems()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _isRefreshing = MutableStateFlow(false)
    @NativeCoroutinesState
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    @NativeCoroutinesState
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        sessionStore.setLastRoute(ROUTE_CATALOG)
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            when (val result = catalogRepository.refresh()) {
                is AppResult.Ok -> _errorMessage.value = null
                is AppResult.Err -> _errorMessage.value = result.message
            }
            _isRefreshing.value = false
        }
    }

    private companion object {
        const val ROUTE_CATALOG = "catalog"
    }
}
