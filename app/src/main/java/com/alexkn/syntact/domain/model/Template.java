package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.domain.common.TemplateType;

import java.util.Objects;

@Entity
public class Template implements Identifiable {

    @PrimaryKey
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private TemplateType templateType;

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

    @NonNull
    public TemplateType getTemplateType() {

        return templateType;
    }

    public void setTemplateType(@NonNull TemplateType templateType) {

        this.templateType = templateType;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return id.equals(template.id) && name.equals(template.name) &&
                templateType == template.templateType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, templateType);
    }
}
