package com.example.sudha.sunshine;

import android.view.View;

/**
 * Created by Sudha on 12/24/2014 at 8:41 AM.
 */
public interface TableAdaptor<T>
{

        int getRowCount();

        int getColumnWeight(int column);

        int getColumnCount();

        T getItem(int row, int column);

        View getView(int row, int column);

}
