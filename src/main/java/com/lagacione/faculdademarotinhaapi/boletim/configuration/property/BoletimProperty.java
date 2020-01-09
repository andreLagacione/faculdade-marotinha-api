package com.lagacione.faculdademarotinhaapi.boletim.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lagacione.faculdademarotinhapi.boletim")
public class BoletimProperty {

    private String jasper;

    public String getJasper() {
        return jasper;
    }

    public void setJasper(String jasper) {
        this.jasper = jasper;
    }
}
