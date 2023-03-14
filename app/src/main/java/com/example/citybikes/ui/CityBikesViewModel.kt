package com.example.citybikes.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citybikes.model.BikesResponse
import com.example.citybikes.model.Network
import com.example.citybikes.repo.Repository
import com.example.citybikes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CityBikesViewModel @Inject constructor(
    val cityBikesRepo: Repository
) : ViewModel() {
    var bikesList: MutableLiveData<Resource<List<Network>>> = MutableLiveData()

    init {
        getAllBikeCompanies()
    }

    fun getAllBikeCompanies() = viewModelScope.launch {
        bikesList.postValue(Resource.Loading())
        val response = cityBikesRepo.getAllCityBikes()
        bikesList.postValue(handleResponse(response))
    }


    /**
     * This handle response we can choose if we get straight from api or from room Resource.Success(bikesResponse?.results)
     */
    private fun handleResponse(response: Response<BikesResponse>): Resource<List<Network>>? {
        if (response.isSuccessful) {
            response.body().let { companiesResponse ->
                return Resource.Success(companiesResponse?.networks)
            }
        } else if (response.code() == 404) {
            Log.d("viewmodel", "error")
        }
        return Resource.Error(response.message())
    }
}