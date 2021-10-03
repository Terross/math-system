package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("property")
@JsonRootName("property")
@Data
public class PropertyAnswer extends AlgAnswer {
    private boolean answer;
}
