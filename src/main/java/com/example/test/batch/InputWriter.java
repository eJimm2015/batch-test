package com.example.test.batch;

import com.example.test.model.Input;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class InputWriter implements ItemWriter<Input> {
    private StepExecution stepExecution;

    @BeforeStep
    public void setStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    public StepExecution getStepExecution() {
        return stepExecution;
    }

    @Override
    public void write(List<? extends Input> list) throws Exception {}
}
