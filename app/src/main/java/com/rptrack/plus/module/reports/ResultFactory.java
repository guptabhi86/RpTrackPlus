package com.rptrack.plus.module.reports;

import android.content.Context;


public class ResultFactory {
    public Result CreateInstance(int id, Object data, Context context) {
        if (id == 0) {
            return new ResultHistroy(context, data);
        } else if (id == 2) {
            return new ResultTrip(context, data);
        }else if (id == 3) {
            return new ResultDistance(context, data);
        }else if (id==5){
            return new ResultStoppage(context,data);
        }else if (id==6){
            return new ResultIdle(context,data);
        }
        return null;
    }
}
