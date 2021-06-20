package com.rptrack.plus.module.device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;

public class DeviceViewModel extends ViewModel {
    private MutableLiveData<DashboardResponse> mText;

    public void init() {
        mText = new MutableLiveData<>();
    }

    public LiveData<DashboardResponse> getData() {
        return mText;
    }

    public  void setData(DashboardResponse response){
        mText.setValue(response);
    }
}