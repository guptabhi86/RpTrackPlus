package com.rptrack.plus.module.reports;

public interface IResult<T> {
    void Response(T data, int id);
}