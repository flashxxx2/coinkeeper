package payments.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FileCriteria extends RestCriteria {

    @Override
    protected List<Sort.Order> getDefaultOrders() {
        return Collections.singletonList(Sort.Order.asc("name"));
    }
}