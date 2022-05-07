package com.turkcell.rentacar.dataaccess.abstracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Damage;

@Repository
public interface DamageDao extends JpaRepository<Damage, Integer> {

    List<Damage> findByCarId(int carId);
}