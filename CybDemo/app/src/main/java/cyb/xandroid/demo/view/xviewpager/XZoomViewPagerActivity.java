package cyb.xandroid.demo.view.xviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.demo.Data.ImageDatas;
import cyb.xandroid.demo.R;
import cyb.xandroid.view.xviewpager.XZoomPagerAdapter;
import cyb.xandroid.view.xviewpager.XZoomViewPager;
import cyb.xandroid.view.xviewpager.transformer.ShadowTransformer;
import cyb.xandroid.view.ximage.CircleImageView;

public class XZoomViewPagerActivity extends AppCompatActivity {
    private List<Item> mlist = new ArrayList<>();


    private SeekBar alplaSeekBar;
    private SeekBar scaleSeekBar;
    private RadioGroup radioGroup;
    private XZoomViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private float mAlplaNum;
    private float mScaleNum;
    private CheckBox isAlpla;
    private CheckBox isScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xzoom_view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        initView();
        initLintener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < ImageDatas.getImageRes().length; i++) {
            Item item = new Item();
            item.setImg(ImageDatas.getImageRes().length);
            item.setName(i + "km");
            mlist.add(item);
        }

    }

    private void initView() {
        alplaSeekBar = (SeekBar) findViewById(R.id.alpla_num);
        scaleSeekBar = (SeekBar) findViewById(R.id.scale_num);
        radioGroup = (RadioGroup) findViewById(R.id.orientation);
        mViewPager = (XZoomViewPager) findViewById(R.id.viewPager);
        isAlpla = (CheckBox) findViewById(R.id.is_alpla);
        isScale = (CheckBox) findViewById(R.id.is_scale);
        mCardAdapter = new CardPagerAdapter(this, mlist);
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(4);
    }


    private void initLintener() {
        isAlpla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCardShadowTransformer.setCanAlpha(b);
            }
        });
        isScale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCardShadowTransformer.setCanScale(b);
            }
        });
        mCardAdapter.setOnItemClickListener(new CardPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.horizontal:
//                        if (!mViewPager.isOrientationHorizontal()) {
//                            mViewPager.setOrientation(XZoomViewPager.HORIZONTAL);
//                        }
//                        break;
//                    case R.id.vertical:
//                        if (!mViewPager.isOrientationVertical()) {
//                            mViewPager.setOrientation(XZoomViewPager.VERTICAL);
//                        }
//                        break;
//                }
//            }
//        });

        alplaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mAlplaNum = 1 - (float) ((i / 100f) * 0.6 + 0.1f);
                mCardShadowTransformer.setAlpha(mAlplaNum, isAlpla.isChecked());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mScaleNum = (float) ((i / 100f) * 0.5);
                mCardShadowTransformer.setScale(mScaleNum, isScale.isChecked());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    static public class CardPagerAdapter extends XZoomPagerAdapter<Item> implements ShadowTransformer.ICardAdapter {

        public interface OnItemClickListener {
            void onClick(int position);
        }

        private List<CardView> mViews;
        private float mBaseElevation;
        OnItemClickListener mOnItemClickListener;

        public CardPagerAdapter(Context context, List<Item> list) {
            super(context);
            mViews = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                mViews.add(null);
            }
            setList(list);
        }

        public float getBaseElevation() {
            return mBaseElevation;
        }

        @Override
        public CardView getCardViewAt(int position) {
            return mViews.get(position);
        }

        @Override
        public View getView(ViewGroup container, View convertView, final int position) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_xzoomviewpager, container, false);
            container.addView(view);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            CircleImageView mIcon = (CircleImageView) view.findViewById(R.id.profile_image);
            TextView mName = (TextView) view.findViewById(R.id.name);
            if (mBaseElevation == 0) {
                mBaseElevation = cardView.getCardElevation();
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) mOnItemClickListener.onClick(position);
                }
            });
            cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
            mViews.set(position, cardView);

            final Item item = getItem(position);
            mIcon.setImageResource(item.getImg());
            mName.setText(item.getName());
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mViews.set(position, null);
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

    }

    public class Item implements Serializable {
        private int position;
        private int img;
        private String name;
        private boolean isSelected;

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
