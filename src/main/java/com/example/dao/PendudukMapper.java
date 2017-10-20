package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper
{
    @Select("select * from penduduk where nik = #{nik} ")
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
    
    @Select("select * from keluarga, penduduk where penduduk.id_keluarga = keluarga.id and #{idKeluarga} = penduduk.id_keluarga")
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
    List<PendudukModel> selectAnggotaKeluarga(int idKeluarga);
    
   @Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
   void updateStatusKematian(@Param("nik") String nik);
   
   @Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
   		+ "values ('${nik}', '${nama}', '${tempatLahir}', '${tanggalLahir}', '${jenisKelamin}', '${kewarganegaraan}', '${idKeluarga}', '${agama}', '${pekerjaan}', '${statusKawin}', '${statusDalamKeluarga}', '${goldar}', '${statusKematian}') ")
   void addPenduduk(PendudukModel penduduk);

   @Select("select * from penduduk where nik LIKE CONCAT(#{nik},'%') order by nik desc limit 1")
   PendudukModel getNIKBefore(String nik);
   
   @Update("update penduduk set nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempatLahir}, tanggal_lahir = #{tanggalLahir}, "
			+ "jenis_kelamin = #{jenisKelamin}, is_wni = #{kewarganegaraan}, golongan_darah = #{goldar}, is_wafat = #{statusKematian} "
			+ "where id = #{id}")
   void updatePenduduk (@Param("penduduk") PendudukModel penduduk);
}
