package com.example.xxovek.salesman_tracker1.admin.salesperson;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xxovek.salesman_tracker1.R;


public class AddSalesmanFragment extends Fragment {

    TextView tv_personaccountnumber,tv_ifsc,tv_branch;
    EditText et_personaccountnumber,et_ifsc,et_branch;
    CheckBox cb_bankdetails;
    public AddSalesmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_salesman, container, false);

        tv_personaccountnumber= view.findViewById(R.id.tv_personaccountnumber);
        tv_ifsc= view.findViewById(R.id.tv_bankifsc);
        tv_branch= view.findViewById(R.id.tv_branch);
        et_personaccountnumber= view.findViewById(R.id.et_personaccountnumber);
        et_ifsc= view.findViewById(R.id.et_bankifsc);
        et_branch= view.findViewById(R.id.et_branch);
        cb_bankdetails=view.findViewById(R.id.cb_bankdetails);

        cb_bankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkBoxState = cb_bankdetails.isChecked();
                if(checkBoxState.equals(true)) {
                    tv_personaccountnumber.setVisibility(View.VISIBLE);
                    tv_ifsc.setVisibility(View.VISIBLE);
                    tv_branch.setVisibility(View.VISIBLE);
                    et_personaccountnumber.setVisibility(View.VISIBLE);
                    et_ifsc.setVisibility(View.VISIBLE);
                    et_branch.setVisibility(View.VISIBLE);
                }else{
                    tv_personaccountnumber.setVisibility(View.GONE);
                    tv_ifsc.setVisibility(View.GONE);
                    tv_branch.setVisibility(View.GONE);
                    et_personaccountnumber.setVisibility(View.GONE);
                    et_ifsc.setVisibility(View.GONE);
                    et_branch.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }
}
