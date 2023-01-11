package com.example.test.batch;

import com.example.test.model.Data;
import com.example.test.model.Footer;
import com.example.test.model.Header;
import com.example.test.model.Input;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.classify.Classifier;
import org.springframework.classify.SubclassClassifier;

import java.util.Map;

public class CompositeInputProcessor extends ClassifierCompositeItemProcessor<Input, Input> {

    private StepExecution stepExecution;
    private final HeaderProcessor headerProcessor;
    private final DataProcessor dataProcessor;
    private final FooterProcessor footerProcessor;

    public CompositeInputProcessor(HeaderProcessor headerProcessor, DataProcessor dataProcessor, FooterProcessor footerProcessor) {
        this.headerProcessor = headerProcessor;
        this.dataProcessor = dataProcessor;
        this.footerProcessor = footerProcessor;
        setClassifier(
                new SubclassClassifier<>(
                        Map.of(Header.class,
                                headerProcessor,
                                Data.class,
                                dataProcessor,
                                Footer.class,
                                footerProcessor
                        ),
                        dataProcessor)
        );
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        headerProcessor.setStepExecution(stepExecution);
        dataProcessor.setStepExecution(stepExecution);
        footerProcessor.setStepExecution(stepExecution);
    }

    public StepExecution getStepExecution() {
        return stepExecution;
    }
}
