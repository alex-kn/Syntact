package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.domain.common.TemplateType;

import java.util.Locale;
import java.util.Objects;

@Entity
public class Template implements Identifiable {

    @PrimaryKey
    private Long id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private TemplateType templateType;

    @NonNull private Locale language;

    @NonNull
    public Long getId() {

        return id;
    }

    public void setId(@NonNull Long id) {

        this.id = id;
    }

    @NonNull
    public String getName() {

        return name;
    }

    public void setName(@NonNull String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @NonNull
    public TemplateType getTemplateType() {

        return templateType;
    }

    public void setTemplateType(@NonNull TemplateType templateType) {

        this.templateType = templateType;
    }

    @NonNull
    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(@NonNull Locale language) {

        this.language = language;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id) && name.equals(template.name) &&
                Objects.equals(description, template.description) &&
                templateType == template.templateType && language.equals(template.language);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, templateType, language);
    }
}
