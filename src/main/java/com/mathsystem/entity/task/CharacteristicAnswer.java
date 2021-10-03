package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("characteristic")
@JsonRootName("characteristic")
@Data
public class CharacteristicAnswer extends AlgAnswer {
    private Integer answer;
}
