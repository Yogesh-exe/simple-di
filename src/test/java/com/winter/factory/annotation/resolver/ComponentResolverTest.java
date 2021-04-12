package com.winter.factory.annotation.resolver;

import com.winter.autoconfig.testclasses.Car;
import com.winter.autoconfig.testclasses.Driver;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentResolverTest {

    ComponentResolver componentResolver;

    @Before
    public void setUp()  {
        componentResolver= new ComponentResolver();
    }

    @Test
    public void resolveComponent() {
        Car car = (Car) componentResolver.resolveComponent(Car.class);
        Assertions.assertThat(car).isInstanceOf(Car.class);
        Assertions.assertThat(car).hasFieldOrProperty("driver");
        Assertions.assertThat(car.getDriver().getName()).isEqualTo("Yogesh");
    }
}
