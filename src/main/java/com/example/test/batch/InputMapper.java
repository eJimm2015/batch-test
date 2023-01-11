package com.example.test.batch;

import com.example.test.model.Input;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;

public class InputMapper extends PatternMatchingCompositeLineMapper<Input> {

    @Override
    public Input mapLine(String line, int lineNumber) throws Exception {
        final Input input = super.mapLine(line, lineNumber);
        input.setLineNumber(lineNumber);
        return input;
    }
}
