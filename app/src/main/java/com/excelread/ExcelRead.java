package com.excelread;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import login.lawersinc.com.lawersinc.R;

public class ExcelRead {

    String dbStr = Environment.getExternalStorageDirectory() + "/ngo/exam.xls";
    String strHyouji = "";
    String[][] arrays;
    private static String QID = "QID";
    private static final String QText = "QText";
    private static final String CorAns = "Cur_Ans";

    public ArrayList<String> qiDs, qTexts, corAns, op1, op2, op3, op4;

    public ExcelRead(String fileName) {
        read(fileName);
    }

    protected void onCreate() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        arrays = read("/ngo/exam.xls");

        if (arrays == null) {
//            File file = new File(dbStr);
//            if (file.exists())
//                strHyouji = "file exists";
//            else
//                strHyouji = "no such file";
//            try {
//                Workbook wb = Workbook.getWorkbook(file);
//                if (wb != null)
//                    strHyouji = "workbook exists";
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.i("IOException", "workbook null");
//            } catch (BiffException e) {
//                e.printStackTrace();
//                Log.i("BiffException", "workbook null");
//            }

            strHyouji = "no such file";
        } else {

            for (String[] array : arrays) {
                for (String v : array) {
                    strHyouji = strHyouji + v + ",";
                }
                strHyouji = strHyouji + "\n";
            }
        }

        Log.i("ExcelRead", strHyouji);
//        Toast.makeText(getApplicationContext(), dbStr, Toast.LENGTH_SHORT).show();
//
//        TextView textSetting = (TextView) findViewById(R.id.textView3);
//        textSetting.setText(strHyouji);
        try {
            write(qiDs, corAns, "/ngo/results.xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }


    public String[][] read(String path) {
        Workbook workbook = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            ws.setGCDisabled(true);


            workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory()+path), ws);
            Sheet sheet = workbook.getSheet(0);
            qiDs = new ArrayList<>();
            qTexts = new ArrayList<>();
            corAns = new ArrayList<>();
            op1 = new ArrayList<>();
            op2 = new ArrayList<>();
            op3 = new ArrayList<>();
            op4 = new ArrayList<>();
            int rowCount = sheet.getRows();
            int columnCount = sheet.getColumns();

            String[][] columnData = new String[columnCount][];
            Log.i("WorkBookColumnCount", columnCount + "");
            for (int column = 0; column < columnCount; column++) {
                Cell[] currColumn = sheet.getColumn(column);
                columnData[column] = new String[currColumn.length];

                Log.i("WorkColumnData", currColumn.length + "");
                for (int colData = 0; colData < currColumn.length; colData++) {

                    columnData[column][colData] = currColumn[colData].getContents();
                    String colHeader = currColumn[0].getContents();
                    if (colData > 0)
                        switch (colHeader) {
                            case "QID":
                                qiDs.add(columnData[column][colData]);
                                Log.i("WorkQIDData", columnData[column][colData]);
                                break;
                            case "Qtext":
                                qTexts.add(columnData[column][colData]);
                                Log.i("WorkQTextData", columnData[column][colData]);
                                break;
                            case "Cur_Ans":
                                corAns.add(columnData[column][colData]);
                                Log.i("WorkCorData", columnData[column][colData]);
                                break;
                            case "Op1":
                                op1.add(columnData[column][colData]);
                                break;
                            case "Op2":
                                op2.add(columnData[column][colData]);
                                break;
                            case "Op3":
                                op3.add(columnData[column][colData]);
                                break;
                            case "Op4":
                                op4.add(columnData[column][colData]);
                                break;
                        }
                    Log.i("WorkColHeader", currColumn[0].getContents());
//                    Log.i("WorkCurrColumnData", columnData[column][colData]);
                }
//                Log.i("WorkBookColumn", columnData[column][0]);
            }
//            String[][] result = new String[rowCount][];
//            for (int i = 0; i < rowCount; i++) {
//                Cell[] row = sheet.getRow(i);
//
//                result[i] = new String[row.length];
//                for (int j = 0; j < row.length; j++) {
//                    result[i][j] = row[j].getContents();
//                    Log.i("WorkBookRow", row[j].toString());
//                    Log.i("WorkBookRead", result[i][j]);
//                }
//            }
            return columnData;


        } catch (BiffException e) {
            strHyouji = strHyouji + e.toString();

        } catch (IOException e) {
            strHyouji = strHyouji + e.toString();
        } catch (Exception e) {
            strHyouji = strHyouji + e.toString();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        workbook.close();
        return null;
    }


    public void write(ArrayList qiDs, ArrayList answers, String fileName) throws IOException, WriteException {
        WritableWorkbook workbook = null;
        Log.i("WorkbookWrite", "inside");
        strHyouji = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            ws.setGCDisabled(true);


            File file = new File(Environment.getExternalStorageDirectory()+fileName);
            workbook = Workbook.createWorkbook(file);
            // this will create new new sheet in workbook
            WritableSheet sheet = workbook.createSheet("results", 0);

            // this will add label in excel sheet
            Label label = new Label(0, 0, "QID");
            sheet.addCell(label);
            for (int id = 1; id <= qiDs.size(); id ++) {
                sheet.addCell(new Label(0, id, (String) qiDs.get(id - 1)));
            }

            for (int id = 1; id <= answers.size(); id ++) {
                sheet.addCell(new Label(1, id, (String) answers.get(id - 1)));
            }

            Label label2 = new Label(1, 0, "Answer");
            sheet.addCell(label2);

            strHyouji = "adding sheet";
            workbook.write();

        } catch (IOException e) {
            strHyouji = strHyouji + e.toString();
        } catch (Exception e) {
            strHyouji = strHyouji + e.toString();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        Log.i("WorkbookWrite", strHyouji);

    }

}
