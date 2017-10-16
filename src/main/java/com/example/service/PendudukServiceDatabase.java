package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
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
    
//    @Override
//    public PendudukModel addPenduduk(PendudukModel penduduk) {
//    	log.info("add penduduk");
//    }

    
}
