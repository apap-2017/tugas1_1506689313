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
public class PendudukController
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
		KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getIdKeluarga());
				
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			return "view-penduduk";
		} 
		else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}
    
    @RequestMapping(value="/keluarga")
    public String viewKeluarga(Model model, @RequestParam(value="nkk") String nkk) {
    	KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
    	
    	if(keluarga != null) {
    		model.addAttribute("keluarga", keluarga);
    		return "view-keluarga";
    	}
    	else {
    		model.addAttribute("nkk", nkk);
    		return "not-found";
    	}
    }

}
