package pl.inz.ctscan.model;

import lombok.Getter;
import lombok.Setter;
import pl.inz.ctscan.model.base.AutoEntity;
import pl.inz.ctscan.model.base.ManualEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
