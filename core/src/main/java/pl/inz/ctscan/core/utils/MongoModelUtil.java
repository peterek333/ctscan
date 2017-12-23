package pl.inz.ctscan.core.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.inz.ctscan.model.base.ManualEntity;

public class MongoModelUtil {

    public static void setCreatedByIfNull(ManualEntity mongoObject) {
        if(mongoObject.getCreatedBy() == null) {
            mongoObject.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}
