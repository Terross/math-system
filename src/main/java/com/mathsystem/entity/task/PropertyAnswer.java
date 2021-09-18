package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("property")
@JsonRootName("property")
public class PropertyAnswer extends AlgAnswer {

    private boolean answer;

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }


}
