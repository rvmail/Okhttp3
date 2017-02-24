package com.cniao5.cniao5okhttpdemo.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by Ivan on 2016/10/6.
 */

public abstract class BaseCallback<T> {


    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
//            throw new RuntimeException("Missing type parameter.");

            return null;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Type mType;


    public BaseCallback(){


        mType = getSuperclassTypeParameter(this.getClass());

    }


    public void onSuccess(T t){}

    public void onError(int code){}

    public void onFailure(Call call, IOException e){}



}
