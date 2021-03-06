package com.cniao5.cniao5okhttpdemo.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Ivan on 16/10/6.
 */

public class ProgressResponseBody extends ResponseBody {



    private ResponseBody mResponseBody;
    private ProgressListener mProgressListener;

    private  BufferedSource mBufferedSource;


    public ProgressResponseBody(ResponseBody responseBody,ProgressListener listener){
        this.mResponseBody = responseBody;
        this.mProgressListener = listener;
    }


    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if(mBufferedSource == null)

            mBufferedSource = Okio.buffer(getsourse(mResponseBody.source()));

        return mBufferedSource;
    }



    private Source getsourse(Source source){



        return new ForwardingSource(source) {



            long totalSize = 0l;
            long sum = 0l;


            @Override
            public long read(Buffer sink, long byteCount) throws IOException {


                if(totalSize==0){
                    totalSize = contentLength();
                }


                long  len =  super.read(sink, byteCount);

                sum +=(len ==-1?0:len);
                int progress = (int) ((sum * 1.0f / totalSize) * 100);

                if(len==-1){
                    mProgressListener.onDone(totalSize);
                }
                else
                    mProgressListener.onProgress(progress);



                return len;
            }
        };


    }

}
