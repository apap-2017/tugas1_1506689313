package com.example.service;

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
        return keluargaMapper.selectKeluargabyNKK(nkk);
    }
    
    @Override
    public KeluargaModel selectKeluargabyID(String idKeluarga) {
    	log.info("select keluarga with id {}", idKeluarga);
    	return keluargaMapper.selectKeluargabyID(idKeluarga);
    }

}
