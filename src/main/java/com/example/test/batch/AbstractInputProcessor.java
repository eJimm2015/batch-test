package com.example.test.batch;

import com.example.test.model.Input;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;

public abstract class AbstractInputProcessor<I> implements ItemProcessor<I, I> {
    private StepExecution stepExecution;

    public StepExecution getStepExecution() {
        return stepExecution;
    }

    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
