package presentation.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import common.Resource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.BirdImage
import domain.use_case.GetBirdsUseCase
import kotlinx.coroutines.launch

data class BirdsUiState(
    var images: List<BirdImage> = emptyList(),
    var errorMessage: String? = "",
    var isLoading: Boolean = false,
    val selectedCategory: String? = null,
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = images.filter { it.category == selectedCategory }
}


class HomeViewModel(
    private var getBirdsUseCase: GetBirdsUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(BirdsUiState())
    val uiState: State<BirdsUiState> = _uiState

    var imageList: MutableList<BirdImage> = mutableListOf()

    fun getBirds() {
        viewModelScope.launch {
            getBirdsUseCase.getImages().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        println("Resource.Error getImages called")
                        _uiState.value = BirdsUiState(
                            images = emptyList(),
                            isLoading = false,
                            errorMessage = "Something Went Wrong!"
                        )
                    }

                    is Resource.Loading -> {
                        println("Resource.Loading getImages called")
                        _uiState.value = BirdsUiState(
                            images = emptyList(),
                            isLoading = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Success -> {
                        println("Resource.Success getImages called ${result.data}")
                        imageList.clear()
                        imageList.addAll(result.data ?: emptyList())
                        _uiState.value = BirdsUiState(
                            images = imageList,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }


        }
    }

    fun getLoadingState() = _uiState.value.isLoading


    fun selectCategory(category: String) {
        if (_uiState.value.selectedCategory == category) {
            _uiState.value.copy(selectedCategory = null)
        } else {
            _uiState.value = BirdsUiState(
                images = imageList,
                isLoading = false,
                errorMessage = null,
                selectedCategory = category
            )
//            _uiState.value.copy(selectedCategory = category)
        }
    }
}