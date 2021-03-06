package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;
import com.example.service.PendudukService;

@Controller
public class SidukController
{
    @Autowired
    PendudukService pendudukDAO;
    
    @Autowired 
    KeluargaService keluargaDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping(value="/penduduk")
	public String viewPenduduk(Model model, @RequestParam(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
						
		if (penduduk != null) {
			KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getIdKeluarga());
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			return "view-penduduk";
		} 
		else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}
    
    @RequestMapping(value="penduduk/mati", method = RequestMethod.POST)
    public String ubahStatusKematian(Model model, @RequestParam(value="nik", required=true) String nik) {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
    	pendudukDAO.updateStatusKematian(penduduk);
    	
    	model.addAttribute("nik_kematian", nik);
    	return "success";
    }
    
    @RequestMapping(value="/keluarga")
    public String viewKeluarga(Model model, @RequestParam(value="nkk") String nkk) {
    	KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
    	
    	if(keluarga != null) {
    		List<PendudukModel> anggota_keluarga = pendudukDAO.selectAnggotaKeluarga(keluarga.getIdKeluarga());
    		model.addAttribute("keluarga", keluarga);
    		model.addAttribute("anggota", anggota_keluarga);
    		return "view-keluarga";
    	}
    	else {
    		model.addAttribute("nkk", nkk);
    		return "not-found";
    	}
    }
    
    @RequestMapping("/penduduk/tambah")
    public String addPenduduk(Model model) {
    	
    	return "tambah-penduduk";
    }
    
    @RequestMapping(value="/penduduk/tambah", method = RequestMethod.POST)
    public String addPenduduk(Model model, @ModelAttribute PendudukModel penduduk) {
    	pendudukDAO.addPenduduk(penduduk);
    	model.addAttribute("nik_tambah", penduduk.getNik());
    	return "success";    	
    }
    
    @RequestMapping("penduduk/ubah/{nik}")
    public String updatePenduduk(Model model, @PathVariable(value="nik") String nik) {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
    	
    	if(penduduk != null) {
    		model.addAttribute("penduduk", penduduk);
    		return "update-penduduk";
    	}
    	else {
    		model.addAttribute("nik", nik);
    		return "not-found";
    	}
    }
    
    @RequestMapping(value = "penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updatePendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk, @PathVariable(value="nik") String nik) {
    	pendudukDAO.selectPenduduk(nik);
    	pendudukDAO.updatePenduduk(penduduk);
    	model.addAttribute("nik_lama", nik);
    	return "success";
    }
    
    @RequestMapping("/keluarga/tambah")
    public String addKeluarga(Model model) {
    	return "tambah-keluarga";
    }
    
    @RequestMapping(value="/keluarga/tambah", method = RequestMethod.POST)
    public String addKeluarga(Model model, @ModelAttribute KeluargaModel keluarga) {
    	keluargaDAO.addKeluarga(keluarga);
    	model.addAttribute("nkk_tambah", keluarga.getNkk());
    	return "success";  
    }
    
    @RequestMapping("keluarga/ubah/{nkk}")
    public String updateKeluarga(Model model, @PathVariable(value="nkk") String nkk) {
    	KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
    	
    	if(keluarga != null) {
    		model.addAttribute("keluarga", keluarga);
    		return "update-keluarga";
    	}
    	else {
    		model.addAttribute("nkk", nkk);
    		return "not-found";
    	}
    }
    
    @RequestMapping(value = "keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updateKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga, @PathVariable(value="nkk") String nkk) {
    	keluargaDAO.selectKeluargabyNKK(nkk);
    	keluargaDAO.updateKeluarga(keluarga);
    	model.addAttribute("nkk_lama", nkk);
    	return "success";
    }
    
}
