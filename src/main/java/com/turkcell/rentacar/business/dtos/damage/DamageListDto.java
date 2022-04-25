package com.turkcell.rentacar.business.dtos.damage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageListDto {

    private int damageId;
    private String description;
    private int carCarId;
}
