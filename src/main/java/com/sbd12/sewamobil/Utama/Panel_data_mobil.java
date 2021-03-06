/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbd12.sewamobil.Utama;

import java.util.logging.Level;
import java.util.logging.Logger;
/*import tab_barang.FTambahBarang;
import tab_barang.FUpdateBarang;
import tab_barang.crud_Barang;
import tab_barang.dt_Barang;
import tab_barang.barangTableModel;*/

import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.sbd12.sewamobil.Pkg_Merk_Mobil.*;
import com.sbd12.sewamobil.Pkg_ProdusenMobil.*;
import com.sbd12.sewamobil.Pkg_Data_Mobil.*;
import com.sbd12.sewamobil.Pkg_Jenis_Mobil.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ArieDZ 2
 */
public class Panel_data_mobil extends javax.swing.JPanel {

    /**
     * Creates new form panel_data_barang
     */
    public DataMobilTableModel tableMobil;
    public frm_Utama_metro form_parent;

    public List<DataMobil> dataMobils;
    public DataMobilJDBCTemplate db;
    private boolean mouseDisable = false;

    private DecimalFormat kursIndonesia;
    private DecimalFormatSymbols formatRp;
    private String no_pol, id_merk, nama_merk, nama_prod, nama_jenis, id_ow, nama_ow, no_telepon_ow;
    private String old_no_pol;
    private double hargaPerhari = 0;

    public Panel_data_mobil() throws ClassNotFoundException {
        initComponents();
        ApplicationContext context = new ClassPathXmlApplicationContext("Config-Spring.xml");
        db = (DataMobilJDBCTemplate) context.getBean("dataMobilJDBCTemplate");

        dataMobils = db.listSemua();
        formatCurrency();
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!mouseDisable) {
                    if (e.getClickCount() >= 1) {
                        int baris = jTable1.getSelectedRow();
                        String id_mobil = (String) tableMobil.getValueAt(baris, 0);
                        System.out.println("id =" + id_mobil);
                        // MerkMobil mm =  db.pilih_data(id_mobil);
                        DataMobil mm = db.pilih_data(id_mobil);
                        tf_no_pol.setText(mm.getNo_pol());
                        tf_id_merk.setText(mm.getMerkMobil().getId_merk_mobil());
                        tf_nama_merk.setText(mm.getMerkMobil().getNama_Merk_Mobil());
                        tf_nama_produsen.setText(mm.getMerkMobil().getNama_produsen_mobil());
                        tf_jenis_kendaraan.setText(mm.getMerkMobil().getNama_jenis());
                        tf_id_owner.setText(mm.getOwnerMobil().getId_owner());
                        tf_nama_owner.setText(mm.getOwnerMobil().getNama_ow());
                        tf_no_telepon_owner.setText(mm.getOwnerMobil().getNo_telepon_ow());
                        tf_harga_Sewa.setText(kursIndonesia.format(mm.getHarga_perhari()));
                    }
                }
            }
        });
        tampilData_mobil();

        refreshData();

        enableInput(false);
        resetInput();
    }

    public void enableInput(boolean enable) {
        tf_no_pol.setEnabled(enable);

        tf_id_merk.setEnabled(enable);
        tf_nama_merk.setEnabled(enable);
        tf_nama_produsen.setEnabled(enable);
        tf_jenis_kendaraan.setEnabled(enable);
        tf_id_owner.setEnabled(enable);
        tf_nama_owner.setEnabled(enable);
        tf_no_telepon_owner.setEnabled(enable);
        tf_nama_merk.setEnabled(enable);
        tf_nama_produsen.setEnabled(enable);
        tf_harga_Sewa.setEnabled(enable);

        if (enable == false) {
        }
    }

    public void resetInput() {
        tf_no_pol.setText("");
        tf_id_merk.setText("");
        tf_nama_merk.setText("");
        tf_nama_produsen.setText("");
        tf_jenis_kendaraan.setText("");
        tf_id_owner.setText("");
        tf_nama_owner.setText("");
        tf_no_telepon_owner.setText("");
        tf_nama_merk.setText("");
        tf_nama_produsen.setText("");
        tf_harga_Sewa.setText("");
    }

    public void formatCurrency() {
        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
    }

    public void tampilData_mobil() throws ClassNotFoundException {

        tableMobil = new DataMobilTableModel();
        tableMobil.setData(dataMobils);
        jTable1.setModel(tableMobil);
        jTable1.setSelectionModel(new ForcedListSelectionModel());

    }

    private void setTambah(Boolean tampil, Boolean tambah) {

        if (tambah) {
            if (tampil) {
                bt_simpan_tambah.setVisible(false);
                bt_batal.setVisible(false);
            }
            if (!tampil) {
                bt_simpan_tambah.setVisible(true);
                bt_batal.setVisible(true);
            }
        } else if (!tambah) {

            if (tampil) {
                bt_simpan_edit.setVisible(false);
                bt_batal.setVisible(false);
            }
            if (!tampil) {
                bt_simpan_edit.setVisible(true);
                bt_batal.setVisible(true);
            }
        }

        bt_pilih_id.setVisible(!tampil);
        bt_pilih_owner.setVisible(!tampil);
        BtEdit.setEnabled(tampil);
        BtHapus.setEnabled(tampil);
        BtCari.setEnabled(tampil);
        BtTambah.setEnabled(tampil);
        BtRefresh.setEnabled(tampil);
        mouseDisable = !tampil;
        jTable1.setEnabled(tampil);
    }

    public void refreshData() throws ClassNotFoundException {
        dataMobils = db.listSemua();
        tableMobil.setData(dataMobils);
        tableMobil.fireTableDataChanged();
        jTable1.changeSelection(0, 0, false, false);
    }
    
     public class ForcedListSelectionModel extends DefaultListSelectionModel {

        public ForcedListSelectionModel() {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BtTambah = new javax.swing.JButton();
        BtEdit = new javax.swing.JButton();
        BtHapus = new javax.swing.JButton();
        BtRefresh = new javax.swing.JButton();
        BtCari = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_no_pol = new javax.swing.JTextField();
        tf_harga_Sewa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_nama_owner = new javax.swing.JTextField();
        tf_nama_merk = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_id_merk = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tf_nama_produsen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tf_jenis_kendaraan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tf_id_owner = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tf_no_telepon_owner = new javax.swing.JTextField();
        bt_simpan_tambah = new javax.swing.JButton();
        bt_batal = new javax.swing.JButton();
        bt_simpan_edit = new javax.swing.JButton();
        bt_pilih_owner = new javax.swing.JButton();
        bt_pilih_id = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(960, 720));
        setPreferredSize(new java.awt.Dimension(960, 720));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(960, 510));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(768, 331));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BtTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Plus_24px_3.png"))); // NOI18N
        BtTambah.setText("Tambah");
        BtTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtTambahActionPerformed(evt);
            }
        });

        BtEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Edit_File_24px.png"))); // NOI18N
        BtEdit.setText("     Edit");
        BtEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEditActionPerformed(evt);
            }
        });

        BtHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Waste_24px.png"))); // NOI18N
        BtHapus.setText("Hapus");
        BtHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtHapusActionPerformed(evt);
            }
        });

        BtRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Reset_24px.png"))); // NOI18N
        BtRefresh.setText("Refresh");
        BtRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRefreshActionPerformed(evt);
            }
        });

        BtCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Search_24px.png"))); // NOI18N
        BtCari.setText("Cari");
        BtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(64, 64, 64))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(BtTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtCari))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 138, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("No Polisi");

        tf_no_pol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_no_polActionPerformed(evt);
            }
        });

        tf_harga_Sewa.setEditable(false);

        jLabel3.setText("Harga Sewa");

        jLabel4.setText("Nama Merk");

        jLabel5.setText("Owner");

        tf_nama_owner.setEditable(false);
        tf_nama_owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nama_ownerActionPerformed(evt);
            }
        });

        tf_nama_merk.setEditable(false);
        tf_nama_merk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nama_merkActionPerformed(evt);
            }
        });

        jLabel6.setText("Id Merk");

        tf_id_merk.setEditable(false);
        tf_id_merk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_id_merkActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama Produsen");

        tf_nama_produsen.setEditable(false);
        tf_nama_produsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nama_produsenActionPerformed(evt);
            }
        });

        jLabel8.setText("Jenis Kendaraan");

        tf_jenis_kendaraan.setEditable(false);
        tf_jenis_kendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_jenis_kendaraanActionPerformed(evt);
            }
        });

        jLabel9.setText("Id Owner");

        tf_id_owner.setEditable(false);
        tf_id_owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_id_ownerActionPerformed(evt);
            }
        });

        jLabel10.setText("No Telepon");

        tf_no_telepon_owner.setEditable(false);
        tf_no_telepon_owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_no_telepon_ownerActionPerformed(evt);
            }
        });

        bt_simpan_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Save_24px.png"))); // NOI18N
        bt_simpan_tambah.setText("Simpan");
        bt_simpan_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpan_tambahActionPerformed(evt);
            }
        });

        bt_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Cancel_24px.png"))); // NOI18N
        bt_batal.setText("Batal");
        bt_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_batalActionPerformed(evt);
            }
        });

        bt_simpan_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Save_as_24px_2.png"))); // NOI18N
        bt_simpan_edit.setText("Simpan");
        bt_simpan_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpan_editActionPerformed(evt);
            }
        });

        bt_pilih_owner.setText("Pilih");
        bt_pilih_owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pilih_ownerActionPerformed(evt);
            }
        });

        bt_pilih_id.setText("Pilih");
        bt_pilih_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pilih_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_jenis_kendaraan)
                            .addComponent(tf_no_pol, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(tf_nama_merk)
                            .addComponent(tf_id_merk, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tf_nama_produsen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_pilih_id, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel3))
                                .addGap(82, 82, 82)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_nama_owner, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                    .addComponent(tf_id_owner, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                    .addComponent(tf_harga_Sewa)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(88, 88, 88)
                                .addComponent(tf_no_telepon_owner)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_pilih_owner, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(bt_simpan_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_simpan_edit)
                        .addGap(0, 92, Short.MAX_VALUE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_no_pol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(tf_id_owner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tf_id_merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tf_nama_owner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_pilih_owner)
                    .addComponent(bt_pilih_id))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tf_nama_merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(tf_no_telepon_owner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_nama_produsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_jenis_kendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(tf_harga_Sewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_simpan_tambah)
                    .addComponent(bt_batal)
                    .addComponent(bt_simpan_edit)))
        );

        bt_simpan_tambah.setVisible(false);
        bt_batal.setVisible(false);
        bt_simpan_edit.setVisible(false);
        bt_pilih_owner.setVisible(false);
        bt_pilih_id.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bg, 948, 948, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtTambahActionPerformed
        // TODO add your handling code here:
        System.out.println("Menekan Tombol Tambah");
        enableInput(true);
        setTambah(false, true);
        resetInput();
    }//GEN-LAST:event_BtTambahActionPerformed

    private void BtEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEditActionPerformed
      System.out.println("Menekan tombol edit");
        if (tf_no_pol.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih Baris yg akan di Edit");
        } else {
            old_no_pol=tf_no_pol.getText();
            enableInput(true);
            setTambah(false, false);
        }
    }//GEN-LAST:event_BtEditActionPerformed

    private void BtHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtHapusActionPerformed
      int baris = jTable1.getSelectedRow();
        String id = (String) tableMobil.getValueAt(baris, 0);
        String nama = (String) tableMobil.getValueAt(baris, 1);
        String owner = (String) tableMobil.getValueAt(baris, 1);
        System.out.println("Menekan tombol hapus");
        System.out.println(id);
        Object[] pilihan = {"Ya", "Tidak"};
        int jawaban = JOptionPane.showOptionDialog(null, "Anda yakin Data "
                + "Mobil dengan No Pol='" + id
                + "', Merk='"+nama+"', Owner='"+owner+"' akan dihapus? ","Peringatan",JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,null,pilihan,pilihan[0]
        );
            if (jawaban == 0) {
            db.delete(id);
        }
        try {
            refreshData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Panel_pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_BtHapusActionPerformed

    private void BtRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRefreshActionPerformed
        try {
            // TODO add your handling code here:
            refreshData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Panel_merk_mobil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtRefreshActionPerformed

    private void BtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCariActionPerformed
        // TODO add your handling code here:
        String katakunci;
        katakunci=JOptionPane.showInputDialog(null,"Masukan kata kunci yang di cari?","Filter/Pencarian",JOptionPane.QUESTION_MESSAGE);
        if(katakunci!=null)
        {
            tableMobil.setData(db.pilih_data_like(katakunci));
            tableMobil.fireTableDataChanged();
        }
    }//GEN-LAST:event_BtCariActionPerformed


    private void tf_no_polActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_no_polActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_no_polActionPerformed

    private void tf_nama_ownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nama_ownerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nama_ownerActionPerformed

    private void tf_nama_merkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nama_merkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nama_merkActionPerformed

    private void tf_id_merkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_id_merkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_id_merkActionPerformed

    private void tf_nama_produsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nama_produsenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nama_produsenActionPerformed

    private void tf_jenis_kendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_jenis_kendaraanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_jenis_kendaraanActionPerformed

    private void tf_id_ownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_id_ownerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_id_ownerActionPerformed

    private void tf_no_telepon_ownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_no_telepon_ownerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_no_telepon_ownerActionPerformed

    private void bt_simpan_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpan_tambahActionPerformed
        System.out.println("Menekan tombol simpan tambah");

        if ((!tf_no_pol.getText().isEmpty())
                && (!tf_id_merk.getText().isEmpty())
                && (!tf_id_owner.getText().isEmpty())) {

            no_pol = tf_no_pol.getText();
            id_merk = tf_id_merk.getText();
            id_ow = tf_id_owner.getText();
            Object[] pilihan = {"Ya", "Tidak"};
            int jawaban = JOptionPane.showOptionDialog(null, "Anda yakin akan menambah Data "
                    + "Mobil dengan No Pol='" + no_pol + "' dan Merk='" + nama_merk + "'"
                    + "?", "Peringatan", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, pilihan, pilihan[0]
            );
            if (jawaban == 0) {
                db.create(no_pol, id_merk, id_ow);
            }
            try {
                refreshData();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Panel_data_mobil.class.getName()).log(Level.SEVERE, null, ex);
            }
            setTambah(true, true);
            enableInput(false);
            resetInput();
            jTable1.setRowSelectionInterval(0, 0);
        } else {
            JOptionPane.showMessageDialog(form_parent, "Data Belum Lengkap, Mohon diisi Semua");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_bt_simpan_tambahActionPerformed

    private void bt_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_batalActionPerformed
        resetInput();
        setTambah(true, true);
        setTambah(true, false);
        enableInput(false);          // TODO add your handling code here:
    }//GEN-LAST:event_bt_batalActionPerformed

    private void bt_simpan_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpan_editActionPerformed
   
        System.out.println("Menekan tombol Simpan Edit");

        if ((!tf_no_pol.getText().isEmpty())
                && (!tf_id_merk.getText().isEmpty())
                && (!tf_id_owner.getText().isEmpty())) {

            no_pol = tf_no_pol.getText();
            id_merk = tf_id_merk.getText();
            id_ow = tf_id_owner.getText();
            nama_merk=tf_nama_merk.getText();
            Object[] pilihan = {"Ya", "Tidak"};
            int jawaban = JOptionPane.showOptionDialog(null, "Anda yakin akan Edit Data "
                    + "Mobil dengan No Pol='" + no_pol + "' dan Merk='" + nama_merk + "'"
                    + "?", "Peringatan", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, pilihan, pilihan[0]
            );
            if (jawaban == 0) {
                db.edit(no_pol, id_merk, id_ow,old_no_pol);
            }
            try {
                refreshData();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Panel_data_mobil.class.getName()).log(Level.SEVERE, null, ex);
            }
            setTambah(true, false);
            enableInput(false);
            resetInput();

            jTable1.setRowSelectionInterval(0, 0);
        } else {
            JOptionPane.showMessageDialog(form_parent, "Data Belum Lengkap, Mohon diisi Semua");
        }
    }//GEN-LAST:event_bt_simpan_editActionPerformed

    private void bt_pilih_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pilih_idActionPerformed
        try {
            // TODO add your handling code here:

            FD_Merk_Mobil fD_Merk_Mobil = new FD_Merk_Mobil(form_parent, true);
            fD_Merk_Mobil.setTitle("Pilih Kostumer");
            fD_Merk_Mobil.setVisible(true);
            id_merk = fD_Merk_Mobil.getId_merk();
            nama_merk = fD_Merk_Mobil.getNama_merk();
            nama_prod = fD_Merk_Mobil.getNama_prod();
            nama_jenis = fD_Merk_Mobil.getNama_jenis();
            hargaPerhari = fD_Merk_Mobil.getHargaPerhhari();

            tf_id_merk.setText(id_merk);
            tf_nama_merk.setText(nama_merk);
            tf_nama_produsen.setText(nama_prod);
            tf_jenis_kendaraan.setText(nama_jenis);
            tf_harga_Sewa.setText(kursIndonesia.format(hargaPerhari));
            fD_Merk_Mobil.dispose();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Panel_data_transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_bt_pilih_idActionPerformed

    private void bt_pilih_ownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pilih_ownerActionPerformed
        try {
            // TODO add your handling code here:

            FD_Owner_Mobil fD_Owner_Mobil = new FD_Owner_Mobil(form_parent, true);
            fD_Owner_Mobil.setTitle("Pilih Owner");
            fD_Owner_Mobil.setVisible(true);
            id_ow = fD_Owner_Mobil.getId_owner();
            nama_ow = fD_Owner_Mobil.getNama_owner();
            no_telepon_ow = fD_Owner_Mobil.getNo_telepon_owner();

            tf_id_owner.setText(id_ow);
            tf_nama_owner.setText(nama_ow);
            tf_no_telepon_owner.setText(no_telepon_ow);
            fD_Owner_Mobil.dispose();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Panel_data_transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }   // TODO add your handling code here:
    }//GEN-LAST:event_bt_pilih_ownerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCari;
    private javax.swing.JButton BtEdit;
    private javax.swing.JButton BtHapus;
    private javax.swing.JButton BtRefresh;
    private javax.swing.JButton BtTambah;
    private javax.swing.JPanel bg;
    private javax.swing.JButton bt_batal;
    private javax.swing.JButton bt_pilih_id;
    private javax.swing.JButton bt_pilih_owner;
    private javax.swing.JButton bt_simpan_edit;
    private javax.swing.JButton bt_simpan_tambah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tf_harga_Sewa;
    private javax.swing.JTextField tf_id_merk;
    private javax.swing.JTextField tf_id_owner;
    private javax.swing.JTextField tf_jenis_kendaraan;
    private javax.swing.JTextField tf_nama_merk;
    private javax.swing.JTextField tf_nama_owner;
    private javax.swing.JTextField tf_nama_produsen;
    private javax.swing.JTextField tf_no_pol;
    private javax.swing.JTextField tf_no_telepon_owner;
    // End of variables declaration//GEN-END:variables
}
