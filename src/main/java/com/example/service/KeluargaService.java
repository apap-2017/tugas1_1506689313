package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.KeluargaModel;

@Service
public interface KeluargaService
{
    KeluargaModel selectKeluargabyNKK(String nkk);
    KeluargaModel selectKeluargabyID(String idKeluarga);
    void updateStatusBerlaku(int id);
    KeluargaModel addKeluarga(KeluargaModel keluarga);
    void updateKeluarga(KeluargaModel keluarga);
}