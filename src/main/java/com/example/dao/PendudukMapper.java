package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper
{
    @Select("select * from penduduk where nik = #{nik}")
    @Results(value = {
    	@Result(property="nik", column="nik"),
    	@Result(property="nama", column="nama"),
    	@Result(property="tempatLahir", column="tempat_lahir"),
    	@Result(property="tanggalLahir", column="tanggal_lahir"),
    	@Result(property="jenisKelamin", column="jenis_kelamin"),
    	@Result(property="kewarganegaraan", column="is_wni"),
    	@Result(property="idKeluarga", column="id_keluarga"),
    	@Result(property="agama", column="agama"),
    	@Result(property="pekerjaan", column="pekerjaan"),
    	@Result(property="statusKawin", column="status_perkawinan"),
    	@Result(property="statusDalamKeluarga", column="status_dalam_keluarga"),
    	@Result(property="goldar", column="golongan_darah"),
    	@Result(property="statusKematian", column="is_wafat")
    })
    PendudukModel selectPenduduk (@Param("nik") String nik);
    
//	@Select("SELECT * " 
//			+ "FROM penduduk " 
//			+ "WHERE penduduk.nik = #{nik}")
//	PendudukModel filterbyNIK(@Param("nik") String nik);


}
