package com.winter.factory.annotation.resolver;

import com.winter.autoconfig.testclasses.Car;
import com.winter.autoconfig.testclasses.Driver;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Set;

public class ComponentResolverTest {

    ComponentResolver componentResolver;

    @Before
    public void setUp()  {
        componentResolver= new ComponentResolver();
    }

    @Test
    public void resolveComponent() {
        Set<Object> resolveComponent = componentResolver.resolveComponent(Car.class);
        Assertions.assertThat(resolveComponent).hasAtLeastOneElementOfType(Car.class);
//        Assertions.assertThat(resolveComponent).hasFieldOrProperty("driver");
//        Assertions.assertThat(car.getDriver().getName()).isEqualTo("Yogesh");
    }
}
