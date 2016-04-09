package com.example.resultlistener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.hideakisago.resultlistener.OnResultListener;
import jp.hideakisago.resultlistener.ResultListeners;

public abstract class AbsActivity extends AppCompatActivity {

    private final ResultListeners mListeners = new ResultListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListeners.registerAll(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mListeners.notifyResult(requestCode, resultCode, data.getExtras());
    }

    public void startActivityForResult(Intent intent, OnResultListener<?> onResult) {
        super.startActivityForResult(intent, mListeners.requestCodeOf(onResult));
    }
}
