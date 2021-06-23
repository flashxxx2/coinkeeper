package payments.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = TaskValidBeanConstraintValidator.class)
public @interface TaskValidBeanConstraint {
  String message() default "payments.validator.TaskValidBeanConstraint";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
}
