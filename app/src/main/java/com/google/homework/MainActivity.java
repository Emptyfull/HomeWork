package com.google.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean FirstTime=false;
    TextView[][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDisplay();


    }

    public void createDisplay() {

        LinearLayout display = new LinearLayout(this);
        String title = "MATRIX";
        String[] buttonNames = {"Upper Part", "Diagonals", "Down Part", "Border"};

        setDisplayParams(display);
        createTitle(display, title, 4);
        createButtonsInterface(display, 2, 2, 5, buttonNames);
        matrix=createMatrix(display, 3, 3, 16);
    }


    //================================Initial Display Methods=====================================//

    private void setDisplayParams(LinearLayout display) {

        LinearLayout.LayoutParams DisplayParms =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        display.setOrientation(LinearLayout.VERTICAL);

        setContentView(display, DisplayParms);
    }

    private void createTitle(LinearLayout display, String name, int weight) {

        TextView title = new TextView(this);
        LinearLayout.LayoutParams textViewParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        title.setLayoutParams(textViewParams);
        title.setText(name);
        title.setTextSize(pxToDp(getResources().getDimension(R.dimen.titleSize)));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        display.addView(title);

    }

    private void createTableView(LinearLayout display, int rows, int collums, int weight, View[] view, TableRow.LayoutParams viewRowParams) {

        TableLayout tableView = new TableLayout(this);
        TableLayout.LayoutParams tableViewParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, weight);

        tableView.setLayoutParams(tableViewParams);



        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);

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
        int padding=(int)pxToDp(getResources().getDimension(R.dimen.padding));
        ButtonParam.setMargins(padding,padding,padding,padding);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(this);
            buttons[i].setOnClickListener(this);
            buttons[i].setBackground(getDrawable(R.drawable.button_state));

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

        int cubeSize=(int)pxToDp(getResources().getDimension(R.dimen.cubeSize));
        TableRow.LayoutParams textViewParam = new TableRow.LayoutParams( cubeSize,  cubeSize);


        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TextView(this);
            arr[i].setBackground(getDrawable(R.drawable.matrix_default));

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
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return px/dm.density;
    }





    // ==================== Action Methods=======================================================//


    private void resetMatrix(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                matrix[i][j].setBackground(getDrawable(R.drawable.matrix_default));
            }
        }
    }

    private void paintMatrixUpperPart(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j >= i)
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));


            }
        }
    }

    private void paintMatrixlowerPart(TextView[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j <= i)
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));


            }
        }
    }

    private void paintMatrixDiagonals(TextView[][] matrix) {


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if(matrix.length==matrix[0].length) {
                    if (j==i)
                        matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));

                    if(j+i==matrix.length-1)
                        matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));
                }

            }
        }
    }

    private void paintMatrixBorder(TextView[][] matrix) {



        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if(i==0){
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));
                    continue;
                }
                if(j==0){
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));
                    continue;
                }

                if(i==matrix.length-1){
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));
                    continue;

                }
                if(j==matrix.length-1){
                    matrix[i][j].setBackground(getDrawable(R.drawable.matrix_paint));
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

