package com.example.sinemaprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sinemaprojesi.Adapters.BudgetItemAdapter;
import com.example.sinemaprojesi.Adapters.FilmsAdapter;
import com.example.sinemaprojesi.Models.BudgetItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.sinemaprojesi.GetBudgetInfoFromDatabase.incomes_list;
import static com.example.sinemaprojesi.GetBudgetInfoFromDatabase.outcomes_list;
import static com.example.sinemaprojesi.GetInfoFromDatabaseActivity.action_movies;

public class BudgetActivity extends AppCompatActivity {

    RecyclerView income_listview, outcome_listview;
    int total_income_amount, total_outcome_amount;
    Button add_income_button, add_outcome_button;
    TextView total_income_tv, total_outcome_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        income_listview = findViewById(R.id.income_listview);
        outcome_listview = findViewById(R.id.outcome_listview);
        add_income_button = findViewById(R.id.add_income_button);
        add_outcome_button = findViewById(R.id.add_outcome_button);
        total_income_tv = findViewById(R.id.total_income_tv);
        total_outcome_tv = findViewById(R.id.total_outcome_tv);

        for(BudgetItem item : incomes_list)
            total_income_amount += item.getAmount();

        total_income_tv.setText(total_income_tv.getText() + String.valueOf(total_income_amount));

        BudgetItemAdapter budgetItemAdapter1 = new BudgetItemAdapter(getApplicationContext(), incomes_list);
        income_listview.setAdapter(budgetItemAdapter1);
        income_listview.setLayoutManager(new LinearLayoutManager(this));

        for(BudgetItem item : outcomes_list)
            total_outcome_amount += item.getAmount();

        total_outcome_tv.setText(total_outcome_tv.getText() + String.valueOf(total_outcome_amount));

        BudgetItemAdapter budgetItemAdapter2 = new BudgetItemAdapter(getApplicationContext(), outcomes_list);
        outcome_listview.setAdapter(budgetItemAdapter2);
        outcome_listview.setLayoutManager(new LinearLayoutManager(this));

        add_income_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBudget alertDialogBudget = new AlertDialogBudget(BudgetActivity.this, 1);
            }
        });

        add_outcome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBudget alertDialogBudget = new AlertDialogBudget(BudgetActivity.this, 0);
            }
        });

    }

}
