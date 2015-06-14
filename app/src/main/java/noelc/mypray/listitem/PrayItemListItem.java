package noelc.mypray.listitem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import noelc.mypray.R;
import noelc.mypray.common.Util;
import noelc.mypray.customclass.PrayItem;
import noelc.mypray.events.TextEditedListener;

/**
 * Created by noelchew on 6/13/15.
 */
public class PrayItemListItem extends LinearLayout {
    private CircleImageView ivPic;
    private TextView tvName, tvPrice; //, tvQuantity;
    //    private NumberPicker numberPicker;
    private EditText etQuantity;

    //    private NumberPicker.OnValueChangeListener listener;
    private TextEditedListener listener;

    public PrayItemListItem(Context context, TextEditedListener listener) {
        super(context);
        this.listener = listener;
        init();
    }

    public PrayItemListItem(Context context, AttributeSet attrs, TextEditedListener listener) {
        super(context, attrs);
        this.listener = listener;
        init();
    }

    public PrayItemListItem(Context context, AttributeSet attrs, int defStyleAttr, TextEditedListener listener) {
        super(context, attrs, defStyleAttr);
        this.listener = listener;
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.list_item_pray_item, this);

        ivPic = (CircleImageView) findViewById(R.id.image_view_pic);
        tvName = (TextView) findViewById(R.id.text_view_name);
        etQuantity = (EditText) findViewById(R.id.edit_text_quantity);
        tvPrice = (TextView) findViewById(R.id.text_view_price);
    }

    public void update(PrayItem prayItem) {
        ivPic.setImageResource(prayItem.getImageResource());
        tvName.setText(prayItem.getName());
//        numberPicker.setValue(prayItem.getQuantity());
//        numberPicker.setMinValue(0);
//        numberPicker.setMaxValue(1000);
//        numberPicker.setWrapSelectorWheel(false);
//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                listener.onValueChange(picker, oldVal, newVal);
//            }
//        });

        etQuantity.setText(String.valueOf(prayItem.getQuantity()));
        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onTextEdited(s.toString());
            }
        });


//        tvQuantity.setText("x " + String.valueOf(prayItem.getQuantity()));

        tvPrice.setText("RM " + Util.doubleToTwoDecimalString(prayItem.getPriceInDouble()));
    }
}
