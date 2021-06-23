package payments.validator;

import payments.entity.CategoryEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaskValidBeanConstraintValidator implements ConstraintValidator<TaskValidBeanConstraint, CategoryEntity> {

  @Override
  public void initialize(TaskValidBeanConstraint constraintAnnotation) { }

  @Override
  public boolean isValid(CategoryEntity value, ConstraintValidatorContext context) {
    return !value.getName().equals("Forbidden");
  }
}
