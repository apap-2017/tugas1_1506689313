package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Service
public interface KeluargaService
{
    KeluargaModel selectKeluargabyNKK(String nkk);
    KeluargaModel selectKeluargabyID(String idKeluarga);
}