package com.google.homework;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Android on 3/6/2018.
 */

public class MatrixUiDisplay implements View.OnClickListener{

      private Boolean FirstTime=false;
      private TextView[][] matrix;
      private int Rank;
      private  Context Context;


      public MatrixUiDisplay(Context Context, int Rank){

          this.Context=Context;
          this.Rank=Rank;
          createDisplay();
      }



      //================================Initial Display Methods=====================================//



    public void createDisplay() {

        LinearLayout display = new LinearLayout(Context);
        String title = "MATRIX";
        String[] buttonNames = {"Upper Part", "Diagonals", "Down Part", "Border"};

        setDisplayParams(display);
        createTitle(display, title, 4);
        createButtonsInterface(display, 2, 2, 5, buttonNames);

        matrix=createMatrix(display, Rank, Rank, 16);
    }

    private void setDisplayParams(LinearLayout display) {

        LinearLayout.LayoutParams DisplayParms =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        display.setOrientation(LinearLayout.VERTICAL);

        if (Context instanceof  Activity)
        ((Activity)Context).setContentView(display, DisplayParms);
        else Toast.makeText(Context, "Eror The Context ain't an Activity", Toast.LENGTH_SHORT).show();

    }

    private void createTitle(LinearLayout display, String name, int weight) {



        TextView title = new TextView(Context);
        LinearLayout.LayoutParams textViewParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        title.setLayoutParams(textViewParams);
        title.setText(name);
        title.setTextSize(pxToDp(Context.getResources().getDimension(R.dimen.titleSize)));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        display.addView(title);

    }




    private void createTableView(LinearLayout display, int rows, int collums, int weight, View[] view, TableRow.LayoutParams viewRowParams) {

        TableLayout tableView = new TableLayout(Context);
        TableLayout.LayoutParams tableViewParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, weight);

        tableView.setLayoutParams(tableViewParams);



        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(Context);
            TableLayout.LayoutParams rowParams =
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1);
            row.setLayoutParams(rowParams);
            row.setGravity(Gravity.CENTER_HORIZONTAL);


            for (int j = 0; j < collums; j++) {

                view[i * rows + j].setLayoutParams(viewRowParams);
                row.addView(view[i * rows + j]);
            }

            tableView.addView(row);

        }

        display.addView(tableView);

    }

    private void createButtonsInterface(LinearLayout display, int rows, int collums, int weight, String[] names) {

        Button[] buttons = new Button[rows * collums];

        TableRow.LayoutParams ButtonParam = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1);
        int padding=(int)pxToDp(Context.getResources().getDimension(R.dimen.padding));
        ButtonParam.setMargins(padding,padding,padding,padding);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(Context);
            buttons[i].setOnClickListener(this);
            buttons[i].setBackground(Context.getDrawable(R.drawable.button_state));

        }


        buttonSetText(buttons, names);
        createTableView(display, rows, collums, weight, buttons, ButtonParam);

    }

    private void buttonSetText(Button[] buttons, String[] names) {

        for (int i = 0; i < buttons.length; i++) {

            buttons[i].setText(names[i]);
        }


    }

    private TextView[][] createMatrix(LinearLayout display, int rows, int collums, int weight) {

        TextView[] arr = new TextView[rows * collums];


        TableRow.LayoutParams textViewParam =
                new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT,  TableRow.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TextView(Context);
            arr[i].setBackground(Context.getDrawable(R.drawable.matrix_default));

        }

        createTableView(display, rows, collums, weight, arr, textViewParam);


        return  convertTextViewArrayToMatrix(arr, rows, collums);
    }

    private TextView[][] convertTextViewArrayToMatrix(TextView[] arr, int row, int coll) {

        TextView[][] matrix = new TextView[row][coll];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = arr[i * row + j];
            }

        }

        return matrix;

    }

    private float pxToDp(float px){
        DisplayMetrics dm=Context.getResources().getDisplayMetrics();
        return px/dm.density;
    }





    // ==================== Action Methods=======================================================//


    private void resetMatrix(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_default));
            }
        }
    }

    private void paintMatrixUpperPart(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j >= i)
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));


            }
        }
    }

    private void paintMatrixlowerPart(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j <= i)
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));


            }
        }
    }

    private void paintMatrixDiagonals(TextView[][] matrix) {


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if(matrix.length==matrix[0].length) {
                    if (j==i)
                        matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));

                    if(j+i==matrix.length-1)
                        matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));
                }

            }
        }
    }

    private void paintMatrixBorder(TextView[][] matrix) {



        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if(i==0){
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));
                    continue;
                }
                if(j==0){
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));
                    continue;
                }

                if(i==matrix.length-1){
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));
                    continue;

                }
                if(j==matrix.length-1){
                    matrix[i][j].setBackground(Context.getDrawable(R.drawable.matrix_paint));
                    continue;
                }
            }

        }
    }


    @Override
    public void onClick(View view) {
        String ButtonAction;
        if(view instanceof Button) ButtonAction=((Button)view).getText().toString();
        else return;

        if(FirstTime)resetMatrix(matrix);
        FirstTime=true;

        switch(ButtonAction.toLowerCase()) {
            case "upper part": paintMatrixUpperPart(matrix);
                break;
            case "down part": paintMatrixlowerPart(matrix);break;
            case "diagonals": paintMatrixDiagonals(matrix);break;
            case "border":paintMatrixBorder(matrix);break;

        }


    }
}
























