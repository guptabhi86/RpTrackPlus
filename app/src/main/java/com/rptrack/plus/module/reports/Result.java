package com.rptrack.plus.module.reports;

import android.content.Context;

public abstract class Result {
    public Context _context;
    public Object _object;

    public Result(Context _context, Object _object) {
        this._context = _context;
        this._object = _object;
    }

    public abstract void Process();
}
