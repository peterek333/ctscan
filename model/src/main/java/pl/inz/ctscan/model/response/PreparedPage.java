package pl.inz.ctscan.model.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
@Builder
public class PreparedPage<T> {

    long totalElements;
    int totalPages;
    boolean last;
    boolean first;
    int numberOfElements;
    Sort sort;
    int size;
    int number;

    List<T> content;
}
