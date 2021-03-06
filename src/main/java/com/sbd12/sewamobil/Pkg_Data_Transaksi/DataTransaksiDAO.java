package com.sbd12.sewamobil.Pkg_Data_Transaksi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ArieDZ_2
 */
import com.sbd12.sewamobil.Pkg_Data_Mobil.DataMobil;
import com.sbd12.sewamobil.Pkg_Data_Pegawai.Pegawai;
import com.sbd12.sewamobil.Pkg_Data_Pembayaran.DataPembayaran;
import com.sbd12.sewamobil.Pkg_Data_Pengembalian.DataPengembalian;
import com.sbd12.sewamobil.Pkg_Jenis_Mobil.JenisMobil;
import com.sbd12.sewamobil.Pkg_Merk_Mobil.MerkMobil;
import com.sbd12.sewamobil.Pkg_ProdusenMobil.ProdusenMobil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JComboBox;
import org.springframework.stereotype.Repository;

public interface DataTransaksiDAO {

    /**
     * This is the method to be used to initialize database resources ie.
     * connection.
     *
     * @param ds
     */
    public void setDataSource(DataSource ds);

    public void edit(String no_transaksi, String id_kostumer, String no_pol, String id_pegawai, Date tglpinjam, Date tglkembali, double hargatotal);

    public void delete(String id);

    public void create(String no_transaksi, String id_kostumer, String id_pegawai, Date tglpinjam, int lamaPinjam, double harga, double harga_diskon, String status);

    public DataTransaksi getId(Integer id);

    /**
     * This is the method to be used to list down all the records from the
     * Student table.
     *
     * @return
     */
    public void create_pengembalian(String no_transaksi, String id_pegawai, double denda);
    public List<DataTransaksi> listSemua();
    public void update_transaksi_selesai(String no_transaksi);
    public List<Pegawai> combo_box_pegawai(JComboBox Combo);
    public void update_detail_transaksi(String no_transaksi, String id_petugas, double denda, String catatan);
    public List<ProdusenMobil> combo_box_produsen_mobil(JComboBox Combo);
    public List<DataTransaksi> listSemua_belum_selesai();
    public List<MerkMobil> combo_box_merk_mobil(JComboBox Combo);
    public List<DataPengembalian> listSemua_pengembalian();
    public List<DataMobil> combo_box_data_mobil(JComboBox Combo);
    public DataTransaksi pilih_data(String kode);
    public List<DataPembayaran> listSemua_pembayaran();
    public List<MerkMobil> combo_box_merk_mobil_plus(JComboBox Combo, String kode);
    public DataPengembalian pilih_data_pengembalian(String kode);
    public List<DataMobil> combo_box_data_mobil_plus(JComboBox Combo, String kode);
    public List<DataTransaksi> pilih_data_like(String kode);
    public void tambah_detail_transaksi(String noTransaksi, ArrayList<String> list);

    public void tambah_pembayaran(String kode_transaksi, String jenis_pembayaran, double nominal, String keterangan);

    public String generate_id_tranksaksi();

    public double ambil_selected_harga(ArrayList<String> selectedItems) throws ClassNotFoundException;
}
