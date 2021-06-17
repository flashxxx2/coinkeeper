package payments.criteria;

import lombok.*;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCriteria extends RestCriteria {

    @QueryParam("id")
    private Long id;

    @QueryParam("createdTimeFrom")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTimeFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @QueryParam("createdTimeTo")
    private LocalDateTime createdTimeTo;

    @QueryParam("categoryId")
    private Long categoryId;

    @QueryParam("userId")
    private Long userId;

    @Override
    protected List<Sort.Order> getDefaultOrders() {
        return Arrays.asList(Sort.Order.desc("createdTime"), Sort.Order.asc("category.name"));
    }
}
