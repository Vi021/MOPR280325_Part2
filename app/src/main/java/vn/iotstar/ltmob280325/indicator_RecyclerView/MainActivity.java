package vn.iotstar.ltmob280325.indicator_RecyclerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import vn.iotstar.ltmob280325.R;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rclrIcon;
    private ArrayList<IconModel> icons;
    private IconAdapter iconAdapter;
    private SearchView srch;

    public MainActivity() {
        icons = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            icons.add(new IconModel(R.drawable.ic_launcher_foreground, "Icon " + i));
        }

        iconAdapter = new IconAdapter(this, icons);

        rclrIcon = findViewById(R.id.rclr_icon);
        rclrIcon.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false));
        rclrIcon.setAdapter(iconAdapter);
        rclrIcon.addItemDecoration(new LinePagerIndicatorDecoration());

        srch = findViewById(R.id.srch);
        srch.clearFocus();
        srch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<IconModel> ics = new ArrayList<>();
                for (IconModel icon:icons) {
                    if (icon.getDesc().toLowerCase().contains(newText.toLowerCase())) {
                        ics.add(icon);
                    }
                }
                if (ics.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    iconAdapter.setListenerList(ics);
                }

                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    // ChatGPT
    public static class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
        private final int colorActive = 0xFFFFFFFF; // White for active indicator
        private final int colorInactive = 0x66FFFFFF; // Transparent white for inactive indicator

        //private final float DP = Resources.getSystem().getDisplayMetrics().density;
        private MainActivity mainActivity;
        private final float DP = mainActivity.getResources().getDisplayMetrics().density;
        private final int indicatorHeight = (int) (16 * DP);
        private final float indicatorItemLength = 16 * DP;
        private final float indicatorItemPadding = 8 * DP;

        private final Paint paint = new Paint();

        public LinePagerIndicatorDecoration() {
            float indicatorStrokeWidth = 4 * DP;
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(indicatorStrokeWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
            if (itemCount <= 1) return; // No indicator needed for single item

            float totalLength = indicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;
            float indicatorPosY = parent.getHeight() - indicatorHeight;

            drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount);
            drawActiveIndicator(canvas, indicatorStartX, indicatorPosY, parent);
        }

        private void drawInactiveIndicators(Canvas canvas, float startX, float posY, int itemCount) {
            paint.setColor(colorInactive);
            float itemWidth = indicatorItemLength + indicatorItemPadding;
            float x = startX;
            for (int i = 0; i < itemCount; i++) {
                canvas.drawLine(x, posY, x + indicatorItemLength, posY, paint);
                x += itemWidth;
            }
        }

        private void drawActiveIndicator(Canvas canvas, float startX, float posY, RecyclerView recyclerView) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null) return;

            int activePosition = ((RecyclerView.LayoutParams) layoutManager.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
            if (activePosition == RecyclerView.NO_POSITION) return;

            paint.setColor(colorActive);
            float itemWidth = indicatorItemLength + indicatorItemPadding;
            float highlightStart = startX + itemWidth * activePosition;

            canvas.drawLine(highlightStart, posY, highlightStart + indicatorItemLength, posY, paint);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = indicatorHeight; // Adds spacing for the indicator
        }
    }
}