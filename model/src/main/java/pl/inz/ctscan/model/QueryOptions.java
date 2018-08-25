package pl.inz.ctscan.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class QueryOptions {

    private int page;
    private int limit;

    private Sort.Direction sortDirection = Sort.Direction.ASC;

    public QueryOptions() {

    }
}
