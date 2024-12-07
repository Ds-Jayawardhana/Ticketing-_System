package com.example.Backend.repository;

import com.example.Backend.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConfigRepo extends JpaRepository<Config, Integer> {


    Optional<Config> findFirstByOrderIdDsec();
    Optional<Config>  findByTotalTickets(Integer integer);

   

}
