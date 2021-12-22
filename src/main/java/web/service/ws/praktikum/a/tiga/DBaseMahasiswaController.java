/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.service.ws.praktikum.a.tiga;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller;

/**
 *
 * @author harry
 */

@Controller
public class DBaseMahasiswaController {
    
    @RequestMapping("/datamahasiswa")
    @ResponseBody
    public List<Datamahasiswa> getDataMahasiswa(){
        List<Datamahasiswa> datamahasiswa = new ArrayList<>();
        
        DatamahasiswaJpaController controller = new DatamahasiswaJpaController() ;
        
        try {
            datamahasiswa = controller.findDatamahasiswaEntities();
        }catch (Exception e){}
        return datamahasiswa;
    }
}
