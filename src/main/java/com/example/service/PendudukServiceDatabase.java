package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import com.example.dao.KeluargaMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
	@Autowired
    private PendudukMapper pendudukMapper;
	
	@Autowired
	private KeluargaMapper keluargaMapper;

    @Override
    public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk(nik);
    }
    
    @Override 
    public List<PendudukModel> selectAnggotaKeluarga(int idKeluarga){
    	log.info("select anggota keluarga of keluarga with id {}", idKeluarga);
    	return pendudukMapper.selectAnggotaKeluarga(idKeluarga);
    }

    @Override
    public void updateStatusKematian(PendudukModel penduduk) {
    	log.info("penduduk with nik {} is dead", penduduk.getNik());
    	int idKeluarga = Integer.parseInt(penduduk.getIdKeluarga());
    	List<PendudukModel> anggota_keluarga = pendudukMapper.selectAnggotaKeluarga(idKeluarga);
    	
    	int anggota_wafat = 0;
    	for(PendudukModel p : anggota_keluarga) {
    		if(p.getStatusKematian() == 1) {
    			anggota_wafat += 1;
    		}
    	}
    	
    	log.info("anggota_wafat = {}", anggota_wafat);
    	log.info("anggota_keluarga = {}", anggota_keluarga.size());
    	
    	if(anggota_wafat == anggota_keluarga.size()) {
    		keluargaMapper.updateStatusBerlaku(idKeluarga);
    	}
    	pendudukMapper.updateStatusKematian(penduduk.getNik());
    }
    
    @Override
    public PendudukModel addPenduduk(PendudukModel penduduk) {
    	log.info("add penduduk with id keluarga {}", penduduk.getIdKeluarga());
    
    	penduduk.setNik(generateNIK(penduduk));
    	penduduk.setStatusKematian(0);
    	pendudukMapper.addPenduduk(penduduk);
    	
    	return penduduk;
    }

    private String generateNIK(PendudukModel penduduk) {
    	String nik = "";
    	
    	KeluargaModel keluarga = keluargaMapper.selectKeluargabyID(penduduk.getIdKeluarga());
    	
    	log.info("id keluarga {}", String.valueOf(penduduk.getIdKeluarga()));
    	
    	String[] splitTanggal = penduduk.getTanggalLahir().split("-");
    	//6 digit pertama bisa ambil dari nomor kk 
    	nik += keluarga.getNkk().substring(0,6);
    	//tanggal*40 
    	nik += (Integer.parseInt(splitTanggal[2]) + Integer.parseInt(penduduk.getJenisKelamin())*40);
    	//bulan + tahun
    	nik += splitTanggal[1] + splitTanggal[0].substring(2);
    	
    	log.info("digit nik {}",nik);
    	
    	PendudukModel pendudukDouble = pendudukMapper.getNIKBefore(nik);
    	
    	log.info("nik penduduk double ", pendudukDouble.getNik());
    	
    	//no duplicate
    	if(pendudukDouble == null) {
    		nik+= "0001";
    		log.info("NIK {} is generated", nik);
    	}
    	else {
    		log.info("nik yang mirip {}", pendudukDouble.getNik());
    		
    		long nikDouble = Long.parseLong(pendudukDouble.getNik()) + 1;
    		nik = "" + nikDouble;
    		
    		log.info("Incremental NIK {} is generated", nik);
    	}
    	return nik;
    }
    
    @Override
    public void updatePenduduk(PendudukModel penduduk) {
    	log.info("update penduduk with nik {}", penduduk.getNik());
    	
    	PendudukModel pendudukLama = pendudukMapper.selectPenduduk(penduduk.getNik());
    	String id = pendudukLama.getId();
    	
    	penduduk.setNik(generateNIK(pendudukLama));
    	penduduk.setId(id);
    	penduduk.setGoldar(pendudukLama.getGoldar());
    	penduduk.setJenisKelamin(pendudukLama.getJenisKelamin());
    	penduduk.setKewarganegaraan(pendudukLama.getKewarganegaraan());
    	
    	log.info("id penduduk {}", id);
    	log.info("nik penduduk {}", penduduk.getNik());
    	
    	pendudukMapper.updatePenduduk(penduduk);
    }
}
