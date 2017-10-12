package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private String nkk;
	private String alamat;
	private String rt;
	private String rw;
	private String idKelurahan;
	private String isTidakBerlaku;
	private String idKecamatan;
	private String kelurahan;
	private String kecamatan;
	private String kota;
	List<PendudukModel> anggota;
}
