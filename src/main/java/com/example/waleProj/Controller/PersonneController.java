package com.example.waleProj.Controller;

import com.example.waleProj.Repositories.PersonneRepos;
import com.example.waleProj.entities.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/personne")
public class PersonneController {

    @Autowired
    private PersonneRepos personneRepository;

    public static String UPLOAD_DIRECTORY = "/Users/medyassinemessaoud/IdeaProjects/frontWale/waleFront/src/assets/uploads/";

    @PostMapping("/add")
    public Personne addPersonne(@RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("photo") MultipartFile photo) throws IOException {
        Personne personne = new Personne();
        personne.setName(name);
        personne.setDescription(description);
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String fileName = currentDate + photo.getOriginalFilename();
        personne.setPhoto(fileName);
        personneRepository.save(personne);
        byte[] bytes = photo.getBytes();
        Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
        Files.write(path, bytes);
        return personne;
    }
    @PutMapping("/update/{id}")
    public Personne updatePersonne(@PathVariable("id") Long id, @RequestParam("name") String name,
                                   @RequestParam("description") String description,
                                   @RequestParam("photo") MultipartFile photo)throws IOException {
        Personne personne = personneRepository.findById(id).get();
        personne.setName(name);
        personne.setDescription(description);
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String fileName = currentDate + photo.getOriginalFilename();
        if(!photo.isEmpty()){
            File file = new File(UPLOAD_DIRECTORY + personne.getPhoto());
            file.delete();
            personne.setPhoto(fileName);
            byte[] bytes = photo.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
            Files.write(path, bytes);
        }else{

            personne.setPhoto(personne.getPhoto());
        }

        return personneRepository.save(personne);
    }
    @GetMapping("/all")
    public Iterable<Personne> getAllPersonne() {
        return personneRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Personne getPersonne(@PathVariable("id") Long id) {
        return personneRepository.findById(id).get();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePersonne(@PathVariable("id") Long id) {
        //delete photo from directory
        Personne personne = personneRepository.findById(id).get();
        File file = new File(UPLOAD_DIRECTORY + personne.getPhoto());
        file.delete();

        personneRepository.deleteById(id);
    }





}
