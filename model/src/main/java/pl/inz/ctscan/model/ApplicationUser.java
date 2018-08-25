package pl.inz.ctscan.model;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.AutoEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class ApplicationUser extends AutoEntity {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
