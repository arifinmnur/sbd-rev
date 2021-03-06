package com.sbd12.sewamobil.Pkg_ProdusenMobil;



import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ArieDZ
 */
public class ProdusenTableModel extends AbstractTableModel{
    private List<ProdusenMobil> data;
    private String[] nameField={"ID","Nama Produsen"};
    
    public void setData(List<ProdusenMobil> data)
    {
        this.data=data;
    }
    
    @Override
    public int getRowCount() {
      return data.size();
    }

    @Override
    public int getColumnCount() {
      return nameField.length; }

    @Override
    public Object getValueAt(int baris, int kolom) {
        
        ProdusenMobil kst=data.get(baris);
        switch(kolom)
        {
            case 0: return kst.getId_produsen();
            case 1: return kst.getNama_produsen();
            default : return null;
        }
    }
    @Override
    public String getColumnName(int column)
    {
        return nameField[column];
    }
    
    
}
