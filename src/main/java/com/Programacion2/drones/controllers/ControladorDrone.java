package com.Programacion2.drones.controllers;

import com.Programacion2.drones.entities.Drone;
import com.Programacion2.drones.services.ServicioCategoria;
import com.Programacion2.drones.services.ServicioDrone;
import com.Programacion2.drones.services.ServicioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
public class ControladorDrone {

    @Autowired
    private ServicioDrone svcDrone;
    @Autowired
    private ServicioCategoria svcCategoria;
    @Autowired
    private ServicioEmpresa svcEmpresa;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            List<Drone> drones = this.svcDrone.findAllByActivo();
            model.addAttribute("drone", drones);

            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/detalle/{id}")
    public String detalleDrone(Model model, @PathVariable("id") long id) {
        try {
            Drone drone = this.svcDrone.findByIdAndActivo(id);
            model.addAttribute("drone",drone);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/busqueda")
    public String busquedaDrone(Model model, @RequestParam(value ="query",required = false)String q){
        try {
            List<Drone> drone = this.svcDrone.findByTitle(q);
            model.addAttribute("drone", drone);
            model.addAttribute("resultado",q);
            return "views/busqueda";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crud")
    public String crudDrone(Model model){
        try {
            List<Drone> drone = this.svcDrone.findAll();
            model.addAttribute("drone",drone);
            return "views/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/drone/{id}")
    public String formularioDrone(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("categorias",this.svcCategoria.findAll());
            model.addAttribute("empresas",this.svcEmpresa.findAll());
            if(id==0){
                model.addAttribute("drone",new Drone());
            }else{
                model.addAttribute("drone",this.svcDrone.findById(id));
            }
            return "views/formulario/drone";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/drone/{id}")
    public String guardarDrone(
            @RequestParam("archivo") MultipartFile archivo,
            @Valid @ModelAttribute("drone") Drone drone,
            BindingResult result,
            Model model,@PathVariable("id")long id
    ) {

        try {
            model.addAttribute("categorias",this.svcCategoria.findAll());
            model.addAttribute("empresas",this.svcEmpresa.findAll());
            if(result.hasErrors()){
                return "views/formulario/drone";
            }
            String ruta = "C://ImgDrone/imagenes";
            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "";
            extension = "."+archivo.getOriginalFilename().substring(index+1);
            String nombreFoto = Calendar.getInstance().getTimeInMillis()+extension;
            Path rutaAbsoluta = id != 0 ? Paths.get(ruta + "//"+drone.getImagen()) :
                    Paths.get(ruta+"//"+nombreFoto);
            if(id==0){
                if(archivo.isEmpty()){
                    model.addAttribute("errorImagenMsg","La imagen es requerida");
                    return "views/formulario/drone";
                }
                if(!this.validarExtension(archivo)){
                    model.addAttribute("errorImagenMsg","La extension no es valida");
                    return "views/formulario/drone";
                }
                if(archivo.getSize() >= 15000000){
                    model.addAttribute("errorImagenMsg","El peso excede 15MB");
                    return "views/formulario/drone";
                }
                Files.write(rutaAbsoluta,archivo.getBytes());
                drone.setImagen(nombreFoto);
                this.svcDrone.saveOne(drone);
            }else{
                if(!archivo.isEmpty()){
                    if(!this.validarExtension(archivo)){
                        model.addAttribute("errorImagenMsg","La extension no es valida");
                        return "views/formulario/drone";
                    }
                    if(archivo.getSize() >= 15000000){
                        model.addAttribute("errorImagenMsg","El peso excede 15MB");
                        return "views/formulario/drone";
                    }
                    Files.write(rutaAbsoluta,archivo.getBytes());
                }
                this.svcDrone.updateOne(drone,id);
            }
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/eliminar/drone/{id}")
    public String eliminarDrone(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("drone",this.svcDrone.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/drone/{id}")
    public String desactivarDrone(Model model,@PathVariable("id")long id){
        try {
            this.svcDrone.deleteById(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo){
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}

