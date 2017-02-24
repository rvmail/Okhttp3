package com.cniao5.cniao5okhttpdemo.okhttp;

/**
 * Created by Ivan on 16/10/6.
 */

public interface ProgressListener {


    public void onProgress(int progress);


    public void onDone(long totalSize);


}
