package com.example.sherif.androiddrinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.sherif.androiddrinkshop.Model.Drink;
import com.example.sherif.androiddrinkshop.R;
import com.example.sherif.androiddrinkshop.Utils.Common;

import java.util.List;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceHolder> {

    Context context;
    List<Drink> optionList;

    public MultiChoiceAdapter(Context context, List<Drink> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public MultiChoiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.multi_check_layout, null);

        return new MultiChoiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceHolder multiChoiceHolder, final int position) {
        multiChoiceHolder.checkBox.setText(optionList.get(position).Name);
        multiChoiceHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Common.toppingAdded.add(compoundButton.getText().toString());
                    Common.toppingPrice += Double.parseDouble(optionList.get(position).Price);
                } else {
                    Common.toppingAdded.remove(compoundButton.getText().toString());
                    Common.toppingPrice -= Double.parseDouble(optionList.get(position).Price);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class MultiChoiceHolder extends RecyclerView.ViewHolder {

        final CheckBox checkBox;

        public MultiChoiceHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.ckb_topping);
        }
    }
}
