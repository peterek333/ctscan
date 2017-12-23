package pl.inz.ctscan.model.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class ManualEntity extends AutoEntity {

    @CreatedBy
    private String createdBy;
}
