package com.yogesh.simple_di;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.yogesh.simple_di.factory.BeanFactory;
import com.yogesh.simple_di.factory.BruteBeanFactory;
import com.yogesh.simple_di.factory.testclasses.Car;
import com.yogesh.simple_di.factory.testclasses.Driver;

@RunWith(JUnit4.class)
public class AppTest 
{
	
    private static Object bean;
    private static BeanFactory bruteBeanFactory;
    @BeforeClass
    public static void init() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        bruteBeanFactory = new BruteBeanFactory();
    	bean = bruteBeanFactory.createBean(Car.class);
    }
	@Test
    public void createCar()
    {
    	System.out.println("carrrrrrr"+bean);
        assertEquals(Car.class, bean.getClass());
   }
	
	@Test
    public void getDriver()
    {
        assertEquals(Driver.class, bruteBeanFactory.getBean(Driver.class).getClass());
    }
	
}
