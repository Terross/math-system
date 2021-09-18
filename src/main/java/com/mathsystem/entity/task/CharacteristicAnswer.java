package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("characteristic")
@JsonRootName("characteristic")
public class CharacteristicAnswer extends AlgAnswer {

    private Double answer;

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }
}
