package com.wenting.blog.utils;

import com.wenting.blog.bean.Blog;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtil {
    public static String[] getNullPropertiesNames(Object source) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullProperties = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (beanWrapper.getPropertyValue(name) == null){
                nullProperties.add(name);
            }
        }
        return nullProperties.toArray(new String[nullProperties.size()]);
    }
}
