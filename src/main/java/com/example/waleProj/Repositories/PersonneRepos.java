package com.example.waleProj.Repositories;

import com.example.waleProj.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepos extends JpaRepository<Personne, Long> {


}
