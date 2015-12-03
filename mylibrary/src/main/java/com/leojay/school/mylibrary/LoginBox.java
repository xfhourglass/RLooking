package com.leojay.school.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Project:com.leojay.school.mylibrary
 * <p/>
 * Author:Crazy_LeoJay
 * Time:下午5:18
 */
public class LoginBox extends LinearLayout implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Context context;
    private TextView strUserName, strPassword;
    private EditText userNameEdit, passwordEdit;
    private CheckBox saveCheck, autoCheck;
    private Button loginButton;

    private GridLayout putLayout;
    private LinearLayout checkBoxLayout;

    private int textSize;
    private int strWidth;
    private int checkWidth;
    private int betweenMargin;
    private String userName = null;
    private String password = null;

    private OnChangeCheckBoxListener checkBoxListener;
    private OnClickLoginListener loginListener;

    public LoginBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginBox);
        textSize = (int) ta.getDimension(R.styleable.LoginBox_TextSize, 16);
        strWidth = (int) ta.getDimension(R.styleable.LoginBox_InputEditWidth, 200);
        checkWidth = (int) ta.getDimension(R.styleable.LoginBox_labelTextWidth, 400);
        betweenMargin = (int) ta.getDimension(R.styleable.LoginBox_BetweenMargin, 23);

        ta.recycle();
        this.setOrientation(VERTICAL);

        putLayout = viewPutLayout();
        strUserName = viewStrUserName();
        strPassword = viewStrPassword();
        userNameEdit = viewUserName();
        passwordEdit = viewPassword();
        putLayout.addView(strUserName);
        putLayout.addView(strPassword);
        putLayout.addView(userNameEdit);
        putLayout.addView(passwordEdit);

        checkBoxLayout = viewCheckBoxs();
        saveCheck = viewSaveBox();
        saveCheck.setOnCheckedChangeListener(this);
        autoCheck = viewAutoBox();
        autoCheck.setOnCheckedChangeListener(this);
        checkBoxLayout.addView(saveCheck);
        checkBoxLayout.addView(autoCheck);

        loginButton = viewLogin();
        loginButton.setOnClickListener(this);

        addView(putLayout);
        addView(checkBoxLayout);
        addView(loginButton);

    }

    private GridLayout viewPutLayout() {
        final GridLayout Layout = new GridLayout(context);
        Layout.setRowCount(2);
        Layout.setColumnCount(2);
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        Layout.setLayoutParams(params);
        return Layout;
    }

    private TextView viewStrUserName() {
        final TextView tv = new TextView(context);
        tv.setText("UserName：");
        tv.setTextSize(textSize);
        tv.setWidth(strWidth);
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(0));
        tv.setLayoutParams(params);
        return tv;
    }

    private TextView viewStrPassword() {
        final TextView tv = new TextView(context);
        tv.setText("Password ：");
        tv.setTextSize(textSize);
        tv.setWidth(strWidth);
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(0));
        tv.setLayoutParams(params);

        return tv;
    }

    private EditText viewUserName() {
        final EditText edit = new EditText(context);
        edit.setWidth(checkWidth);
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(1));
        edit.setLayoutParams(params);
        return edit;
    }

    private EditText viewPassword() {
        final EditText edit = new EditText(context);
        edit.setWidth(checkWidth);
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(1));
        edit.setLayoutParams(params);
        return edit;
    }

    private LinearLayout viewCheckBoxs() {
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(HORIZONTAL);
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = betweenMargin;
        layout.setLayoutParams(params);
        return layout;
    }

    private CheckBox viewSaveBox() {
        final CheckBox box = new CheckBox(context);
        box.setText("保存密码");
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        box.setLayoutParams(params);
        return box;
    }

    private CheckBox viewAutoBox() {
        final CheckBox box = new CheckBox(context);
        box.setText("自动登录");
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 150;
        box.setLayoutParams(params);
        return box;
    }

    private Button viewLogin() {
        final Button button = new Button(context);
        button.setText("登陆");
        final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = betweenMargin;
        button.setLayoutParams(params);
        return button;
    }

    public void setOnChangeCheckBoxListener(OnChangeCheckBoxListener listener) {
        this.checkBoxListener = listener;
    }

    public void setOnClickLoginListener(OnClickLoginListener listener) {
        this.loginListener = listener;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            if (buttonView == saveCheck) {
                if (isChecked) {
                    checkBoxListener.onSave();
                } else {
                    autoCheck.setChecked(isChecked);
                    checkBoxListener.onNoSave();
                }
            }

            if (buttonView == autoCheck) {
                if (isChecked) {
                    saveCheck.setChecked(isChecked);
                    checkBoxListener.onAutoLogin();
                } else {
                    checkBoxListener.onNoAutoLogin();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            userName = userNameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            loginListener.onLoginClick(v, userName, password);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public interface OnClickLoginListener {
        void onLoginClick(View v, String userName, String password);
    }

    public interface OnChangeCheckBoxListener {
        void onSave();

        void onNoSave();

        void onAutoLogin();

        void onNoAutoLogin();
    }
}
