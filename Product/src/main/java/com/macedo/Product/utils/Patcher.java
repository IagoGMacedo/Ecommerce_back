package com.macedo.Product.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.stereotype.Component;

@Component
public class Patcher {
    public void patchPropertiesNotNull(Object objetoA, Object objetoB) {
        Class<?> classeA = objetoA.getClass();
        Class<?> classeB = objetoB.getClass();

        try {
            for (Field campoA : classeA.getDeclaredFields()) {
                if (!campoA.isSynthetic() && !Modifier.isStatic(campoA.getModifiers())) {
                    String nomeCampo = campoA.getName();
                    Field campoB = classeB.getDeclaredField(nomeCampo);

                    if (campoB.getGenericType().equals(campoA.getGenericType())) {
                        campoA.setAccessible(true);
                        campoB.setAccessible(true);

                        Object valorCampoA = campoA.get(objetoA);
                        if (valorCampoA != null) {
                            campoB.set(objetoB, valorCampoA);
                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
    }
}
