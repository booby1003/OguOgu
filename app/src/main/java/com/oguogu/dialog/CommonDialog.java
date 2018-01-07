package com.oguogu.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.oguogu.R;
import com.oguogu.util.StringUtil;

/**
 * Created by 김민정 on 2017-11-17.
 */

public class CommonDialog extends Dialog {

    private Builder builder;

    public interface DialogButtonClick {
        public static final int CANCEL_BUTTON = 1;
        public static final int CONFIRM_BUTTON = 2;

        void onClick(Dialog dialog, int pos);
    }

    public CommonDialog(Builder builder) {
        super(builder.context);
        this.builder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_common);

        setCancelable(builder.isCancelable);
        setCanceledOnTouchOutside(builder.isCancelableTouchOutside);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(builder.content);
        setButtons();
    }

    private void setButtons() {

        Button[] buttons = new Button[2];

        buttons[0] = (Button) findViewById(R.id.btn_cancel);
        buttons[1] = (Button) findViewById(R.id.btn_confirm);
        buttons[0].setTag(DialogButtonClick.CANCEL_BUTTON);
        buttons[1].setTag(DialogButtonClick.CONFIRM_BUTTON);

        for(int i = 0; i < builder.buttonText.length; i ++) {
            String butText = builder.buttonText[i];
            buttons[i].setText(butText);
            buttons[i].setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            builder.listener.onClick(CommonDialog.this, (int) v.getTag());
        }
    };

    public static class Builder {
        private Context context;
        private boolean isCancelable = true;
        private boolean isCancelableTouchOutside = true;

        private String content;
        private String[] buttonText;

        private DialogButtonClick listener = new DialogButtonClick() {
            @Override
            public void onClick(Dialog dialog, int pos) {}
        };

        public Builder(Context context) { this.context = context; }

        public CommonDialog build() { return new CommonDialog(this); }

        public Builder setTitle(String content) {
            this.content = content;
            return this;
        }

        public Builder setButtonText(String ...text) {
            this.buttonText = text;
            return this;
        }

        public Builder setCancelable(boolean flag) {
            isCancelable = flag;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag) {
            isCancelableTouchOutside = flag;
            return this;
        }

        public Builder setListener(DialogButtonClick listener) {
            this.listener = listener;
            return this;
        }
    }

}
