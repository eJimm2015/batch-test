package com.example.test.batch;

import com.example.test.model.Header;


public class HeaderProcessor extends AbstractInputProcessor<Header> {

    @Override
    public Header process(Header header) throws Exception {
        getStepExecution().getExecutionContext()
                .putString("LABEL_HEADER", header.getLabel());
        System.out.println("Header processor : " + header);
        return header;
    }
}
