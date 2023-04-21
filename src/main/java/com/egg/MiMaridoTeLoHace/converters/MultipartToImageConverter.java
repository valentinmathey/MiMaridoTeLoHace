package com.egg.MiMaridoTeLoHace.converters;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.io.IOException;

@Component
public class MultipartToImageConverter implements Converter<MultipartFile, Image>, PropertyEditor {

    @Override
    public Image convert(MultipartFile multipartFile) {
        Image image = new Image();
        image.setName(multipartFile.getOriginalFilename());
        image.setMime(multipartFile.getContentType());

        try {
            image.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean isPaintable() {
        return false;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {

    }

    @Override
    public String getJavaInitializationString() {
        return null;
    }

    @Override
    public String getAsText() {
        return null;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

    @Override
    public String[] getTags() {
        return new String[0];
    }

    @Override
    public java.awt.Component getCustomEditor() {
        return null;
    }

    @Override
    public boolean supportsCustomEditor() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}