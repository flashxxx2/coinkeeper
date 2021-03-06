package payments.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import payments.entity.CategoryEntity;

@Component
public class SpringValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CategoryEntity.class.equals(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ValidationUtils.rejectIfEmpty(
        errors,
        "name",
        "name.empty"
    );

    CategoryEntity entity = (CategoryEntity) o;
    if (entity.getName().length() < 5) {
      errors.rejectValue("category", "category.too.short");
    }
  }
}
