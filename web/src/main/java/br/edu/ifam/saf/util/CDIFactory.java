package br.edu.ifam.saf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Stateless
public class CDIFactory {
    @Produces
    @Default
    public Logger producer(InjectionPoint ip) {
        return LoggerFactory.getLogger(
                ip.getMember().getDeclaringClass().getName());
    }

}