package com.turkcell.rentacar.dataaccess.abstracts;

import com.turkcell.rentacar.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {

    boolean existsBrandByBrandNameIgnoreCase(String name);

}
