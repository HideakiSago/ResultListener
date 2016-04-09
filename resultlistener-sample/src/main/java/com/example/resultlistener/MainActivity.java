package com.example.resultlistener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import jp.hideakisago.resultlistener.OnResultListener;

public class MainActivity extends AbsActivity {

    private final OnResultListener<Bundle> mOnSettingsActivity = new OnResultListener<Bundle>() {
        @Override
        public void onResult(int resultCode, Bundle bundle) {
            Toast.makeText(MainActivity.this, "onResult", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.launchSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                        mOnSettingsActivity);
            }
        });
    }
}
