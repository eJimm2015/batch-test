package com.example.test.batch;

import com.example.test.model.Data;

public class DataProcessor extends AbstractInputProcessor<Data> {

    @Override
    public Data process(Data data) throws Exception {
        System.out.println("Data Processor : LABEL_HEADER = " +
                getStepExecution().getExecutionContext().getString("LABEL_HEADER")
        );
        return data;
    }
}
