package pl.inz.ctscan.core.utils.db;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.inz.ctscan.model.QueryOptions;

public class DatabaseHelper {

    public static PageRequest preparePageRequest(QueryOptions queryOptions) {
        PageRequest pageRequest = null;

        int page = queryOptions.getPage();
        int limit = queryOptions.getLimit();

        Sort.Direction direction = queryOptions.getSortDirection();
        if(direction != null) {
            pageRequest = new PageRequest(page, limit, new Sort(direction, "id"));
        } else {
            pageRequest = new PageRequest(page, limit);
        }

        return pageRequest;
    }

}
