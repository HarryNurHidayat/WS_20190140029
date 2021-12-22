/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.service.ws.praktikum.a.tiga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author harry
 */
@Controller
public class ViewController {
    
    @RequestMapping("/table")
    public String getTabel (Model model, HttpServletRequest cari){
        
        String nama_barang_dicari = cari.getParameter("barang");
        String nama_ketemu="";
        List<String> buffer = new ArrayList<>();
        
        ArrayList<List<String>> tabel_harga = new ArrayList<>();
        ArrayList<List<String>> tabel_baru = new ArrayList<>();
        
        tabel_harga.add(Arrays.asList("Telur","1000"));
        tabel_harga.add(Arrays.asList("Mie","2500"));
        tabel_harga.add(Arrays.asList("Pensil","2000"));
        tabel_harga.add(Arrays.asList("Pengaris","3000"));
        tabel_harga.add(Arrays.asList("Tipex","4000"));
        
        for (int brs=0;brs<tabel_harga.size();brs++){
            buffer = tabel_harga.get(brs);
            if (buffer.get(1).equals(nama_barang_dicari)){
                tabel_baru.add(tabel_harga.get(brs));
            } else {nama_ketemu="Tidak ditemukan";}
        }
        
       model.addAttribute("tabel_baru", tabel_baru);
       model.addAttribute("kosong",nama_ketemu);
       
       return "viewTabel";
    }
    @RequestMapping("/data/burung")
    @ResponseBody
    public ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        
        data.add("Kakatua");
        data.add("Mepati");
        data.add("Tokek");
        
        return data;
    }
    
    //materi 25/11/2021
    @CrossOrigin
    @GetMapping(value="/data/pribadi", produces = {
        MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public HashMap<String, String> getMinuman(){
        
        HashMap<String, String> map = new HashMap<>();
        map.put("nama", "Harry Nur");
        map.put("alamat", "Jogokaryan Di Yogyakarta");
        map.put("noktp", "0000000000018");
        return map;
    }
    
    @RequestMapping("/data/cuaca")
    @ResponseBody
    public HashMap<String, Integer> getRamcu(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("suhu", 32);
        map.put("kelembaban", 30);
        map.put("tekanan", 18);
        return map;
    }
    
    @CrossOrigin
    @GetMapping(value="/data/xml", produces = {
        MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseBody
    public ArrayList<String> getXML (Model model){
        ArrayList<String> data = new ArrayList<>();
        data.add("kakatua");
        data.add("Gagak");
        data.add("komodo");
        
        model.addAttribute("data", data);
        
        return data;
    }
}
