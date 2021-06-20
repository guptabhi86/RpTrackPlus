package com.rptrack.plus.module.device;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rptrack.plus.R;
import com.rptrack.plus.module.device.adapter.DevicesTypeAdapter;

public class EditDeviceMetadataActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_metadata);
        getSupportActionBar().hide();

        RecyclerView recyclerView = findViewById(R.id.rv_vehicle_type);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_dimen);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        GridLayoutManager linearLayoutManager = new GridLayoutManager(EditDeviceMetadataActivity.this, 5, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DevicesTypeAdapter devicesTypeAdapter = new DevicesTypeAdapter(EditDeviceMetadataActivity.this, 3);
        recyclerView.setAdapter(devicesTypeAdapter);

        findViewById(R.id.et_vehicle_no).setOnFocusChangeListener(this::onFocusChange);
        findViewById(R.id.et_sim_card).setOnFocusChangeListener(this::onFocusChange);
        findViewById(R.id.et_vehicle_plate_no).setOnFocusChangeListener(this::onFocusChange);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_vehicle_no_del ||
                v.getId() == R.id.iv_vehicle_plate_del ||
                v.getId() == R.id.iv_sim_card_del) {
            buttonClick(v);
        }

    }

    private void buttonClick(View v) {
        ViewGroup viewParent = (ViewGroup) v.getParent();
        for (int i = 0; i < viewParent.getChildCount(); i++) {
            View view = viewParent.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
                editTextClick((EditText)view);
                break;
            }
        }
    }

    private void editTextClick(View v) {
        ViewGroup viewParent = (ViewGroup) v.getParent();
        for (int i = 0; i < viewParent.getChildCount(); i++) {
            View view = viewParent.getChildAt(i);
            if (view instanceof ImageView) {
                if (((EditText) v).getText().length() != 0 && v.hasFocus())
                    ((ImageView) view).setVisibility(View.VISIBLE);
                else
                    ((ImageView) view).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        editTextClick(v);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            // outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                // outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
}