package com.lagacione.faculdademarotinhaapi.commons.validations.cpfCnpj;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {
    private Boolean obrigatorio;

    @Override
    public void initialize(CpfCnpj cpfCnpj) {
        this.obrigatorio = cpfCnpj.obrigatorio();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }

        if (StringUtils.length(value) == 11) {
            return CpfCnpjVerify.isValidCPF(value);
        }

        return CpfCnpjVerify.isValidCNPJ(value);
    }
}
