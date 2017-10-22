package com.example.service;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	@Autowired
    private KeluargaMapper keluargaMapper;

    @Override
    public KeluargaModel selectKeluargabyNKK (String nkk){
        log.info ("select keluarga with nkk {}", nkk);
        log.info("is_tidak_berlaku = {}", keluargaMapper.selectKeluargabyNKK(nkk).getIsTidakBerlaku());
        return keluargaMapper.selectKeluargabyNKK(nkk);
    }
    
    @Override
    public KeluargaModel selectKeluargabyID(String idKeluarga) {
    	log.info("select keluarga with id {}", idKeluarga);
    	return keluargaMapper.selectKeluargabyID(idKeluarga);
    }
    
    @Override
    public void updateStatusBerlaku(int id) {
    	log.info("keluarga with id {} is no longer active", id);
    	keluargaMapper.updateStatusBerlaku(id);
    }
    
    @Override
    public KeluargaModel addKeluarga(KeluargaModel keluarga) {
    	log.info("nama kecamatan {}", keluarga.getKecamatan());
    	
    	keluarga.setNkk(generateNKK(keluarga, keluarga.getKecamatan()));
    	log.info("keluarga with nkk {} is added", keluarga.getNkk());
    	
    	keluarga.setIsTidakBerlaku(0);
    	keluargaMapper.addKeluarga(keluarga);
    	
    	return keluarga;
    }
    
    private String generateNKK(KeluargaModel keluarga, String namaKecamatan) {
    	String tanggalRilis = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	String[] tanggalSplit = tanggalRilis.split("-");
    	
    	String tahun = tanggalSplit[0].substring(2);
    	String bulan = tanggalSplit[1];
    	String tgl = tanggalSplit[2];
    	
    	String nkk="";
    	
    	//set id kelurahan
    	String id_kelurahan = keluargaMapper.selectIDKelurahan(keluarga.getKelurahan());
    	keluarga.setIdKelurahan(id_kelurahan);
    	
    	//cari kode kecamatan
    	String id_kecamatan = keluargaMapper.selectIDKecamatan(keluarga.getKecamatan());
    	String kode_kecamatan = keluargaMapper.selectKodeKecamatan(id_kecamatan);
    	
    	//concat nkk
    	nkk += kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tgl + bulan + tahun;
    	
    	log.info("12 digit nkk = {}", nkk);
    	log.info("id kecamatan {}", id_kecamatan);
    	log.info("kode_kecamatan {}", kode_kecamatan);
    	
    	
    	//check duplicate
    	KeluargaModel keluargaDouble = keluargaMapper.getNKKBefore(nkk);
    	
    	if(keluargaDouble == null) {
    		nkk += "0001";
    		log.info("new nkk {} is generated", nkk);
    	}
    	else if(!keluarga.getNkk().isEmpty()) {
    		nkk = keluarga.getNkk();
    		log.info("no changes in nkk {}", nkk);
    	}
    	else {
    		log.info("nkk yang mirip {}", keluargaDouble.getNkk());
    		long nkkDouble = Long.parseLong(keluargaDouble.getNkk()) + 1;
    		nkk = String.valueOf(nkkDouble);
    		log.info("incremental nkk {} is generated", nkk);
    	}
    	
    	return nkk;	
    }
    
    @Override
    public void updateKeluarga(KeluargaModel keluarga) {
    	log.info("keluarga with nkk {} is updated", keluarga.getNkk());
    	
    	KeluargaModel keluargaLama = keluargaMapper.selectKeluargabyNKK(keluarga.getNkk());
    	
    	//get id kelurahan
    	String idKelurahan = keluargaMapper.selectIDKelurahan(keluarga.getKelurahan());
    	
    	log.info("ini id kelurahan {}", idKelurahan);
    	
    	String nkkbaru = generateNKK(keluargaLama, keluargaLama.getKecamatan());
    	keluarga.setNkk(nkkbaru);
    	keluarga.setIdKelurahan(idKelurahan);
    	
    	log.info("id kel baru = {}", keluarga.getIdKeluarga());
    	log.info("nkk baru = {}", keluarga.getNkk());
    	
    	keluargaMapper.updateKeluarga(keluarga);
    }
}
