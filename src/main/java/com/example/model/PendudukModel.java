package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel 
{
    private String nik;
    private String nama;
    private String idKeluarga;
    private String jenisKelamin;
    private String statusDalamKeluarga;
    private String tempatLahir;
    private String tanggalLahir;
    private String alamat;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String kota;
    private String goldar;
    private String agama;
    private String statusKawin;
    private String pekerjaan;
    private String kewarganegaraan;
    private String statusKematian;
    
//    private List<CourseModel> courses;
}