package com.turkcell.rentacar.business.requests.damage;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDamageRequest {

    @NotNull
    @Min(value = 1)
    private int damageId;

    @NotNull
    @Size(min = 4)
    private String description;


}
