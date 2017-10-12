package com.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
    @Select("select * from keluarga where nomor_kk = #{nkk}")
    @Results(value = {
    		@Result(property="nkk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="RT"),
    		@Result(property="rw", column="RW"),
    		@Result(property="idKelurahan", column="id_kelurahan"),
    		@Result(property="isTidakBerlaku", column="is_tidak_berlaku")
    })
    KeluargaModel selectKeluargabyNKK(@Param("nkk") String nkk);

    @Select("select * from keluarga where id = #{idKeluarga}")
    @Results(value = {
    		@Result(property="nkk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="RT"),
    		@Result(property="rw", column="RW"),
    		@Result(property="idKelurahan", column="id_kelurahan"),
    		@Result(property="isTidakBerlaku", column="is_tidak_berlaku")
    })
    KeluargaModel selectKeluargabyID(@Param("idKeluarga") String idKeluarga);
}
