package dev.kiyo9w.kmpboilerplate.feature.catalog

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogItem
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import dev.kiyo9w.kmpboilerplate.domain.session.SessionStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CatalogDetailViewModel(
    private val catalogRepository: CatalogRepository,
    private val sessionStore: SessionStore,
) : ViewModel() {
    private val itemId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val item: StateFlow<CatalogItem?> = itemId
        .flatMapLatest { id ->
            if (id == null) flowOf(null) else catalogRepository.observeItem(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun setId(id: Long) {
        itemId.value = id
        sessionStore.setLastRoute("catalog/$id")
    }
}
