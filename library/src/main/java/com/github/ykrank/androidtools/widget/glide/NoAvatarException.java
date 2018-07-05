package com.github.ykrank.androidtools.widget.glide;

import java.io.IOException;
import java.net.ProtocolException;

import okhttp3.Request;
import okhttp3.internal.connection.StreamAllocation;

/**
 * Must not extends IOException but ProtocolException because  okhttp3 catch RouteException and IOException to retry.
 * But only some exception
 *
 * @see okhttp3.internal.http.RetryAndFollowUpInterceptor#recover(IOException, StreamAllocation, boolean, Request)
 */
public class NoAvatarException extends ProtocolException {

    public NoAvatarException(String host) {
        super(host);
    }
}
