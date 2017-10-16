package com.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
    @Select("select * from keluarga, kelurahan, kecamatan, kota where nomor_kk = #{nkk} and id_kelurahan = kelurahan.id and id_kecamatan = kecamatan.id and id_kota = kota.id")
    @Results(value = {
    		@Result(property="nkk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="RT"),
    		@Result(property="rw", column="RW"),
    		@Result(property="idKelurahan", column="id_kelurahan"),
    		@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
    		@Result(property="kelurahan", column="nama_kelurahan"),
    		@Result(property="kecamatan", column="nama_kecamatan"),
    		@Result(property="kota", column="nama_kota"),
    		@Result(property="idKeluarga", column="id")
    })
    KeluargaModel selectKeluargabyNKK(@Param("nkk") String nkk);

    @Select("select * from keluarga, kelurahan, kecamatan, kota where keluarga.id = #{idKeluarga} and id_kelurahan = kelurahan.id and id_kecamatan = kecamatan.id and id_kota = kota.id")
    @Results(value = {
    		@Result(property="nkk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="RT"),
    		@Result(property="rw", column="RW"),
    		@Result(property="idKelurahan", column="id_kelurahan"),
    		@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
    		@Result(property="kelurahan", column="nama_kelurahan"),
    		@Result(property="kecamatan", column="nama_kecamatan"),
    		@Result(property="kota", column="nama_kota")
    })
    KeluargaModel selectKeluargabyID(@Param("idKeluarga") String idKeluarga);
    
    @Update("UPDATE keluarga SET is_tidak_berlaku = '1' WHERE id = #{id}")
    void updateStatusBerlaku(@Param("id") int id);
}
