package br.com.hiokdev.picpaysimplificado.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

  private List<String> acceptedValues = null;

  @Override
  public void initialize(EnumValid constraintAnnotation) {
    this.acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
      .map(Enum::name)
      .toList();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return this.acceptedValues.contains(value);
  }

}
