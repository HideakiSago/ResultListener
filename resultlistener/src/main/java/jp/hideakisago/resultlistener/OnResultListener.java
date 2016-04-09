package jp.hideakisago.resultlistener;

import android.support.annotation.Nullable;

public interface OnResultListener<Result> {
    void onResult(int resultCode, @Nullable Result result);
}
