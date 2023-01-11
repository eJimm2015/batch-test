package com.example.test.batch;

import com.example.test.model.Data;
import com.example.test.model.Footer;
import com.example.test.model.Header;
import com.example.test.model.Input;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.SubclassClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }



    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Input, Input>chunk(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }
/*
    @Bean
    public ItemProcessor<Header, Header> headerProcessor() {
        return h -> {
            final boolean processed = Files
                    .lines(Paths.get("src/main/resources/processed.txt"))
                    .anyMatch(e -> e.equals(h.format()));
            if(processed) throw new IllegalArgumentException("File already processed");
            System.out.println("Header processor : " + h);
            return h;
        };
    }

 */

    @Bean
    public HeaderProcessor headerProcessor() {
/*
        return h -> {
            System.out.println("Header processor : " + h);
            return h;
        };

 */

        return new HeaderProcessor();
    }

    @Bean
    public DataProcessor dataProcessor() {
        /*
        return d -> {
            // TODO perform any validation
            // TODO store total in context
            System.out.println("Data processor : " + d);
            return d;
        };

         */
        return new DataProcessor();
    }

    @Bean
    public FooterProcessor footerProcessor() {
        /*
        return f -> {
            // TODO perform any validation
            // TODO store total in context
            System.out.println("Footer processor : " + f);
            return f;
        };

         */
        return new FooterProcessor();
    }

    @Bean
    public CompositeInputProcessor itemProcessor() {
        return new CompositeInputProcessor(headerProcessor(),
                dataProcessor(),
                footerProcessor());
    }

    @Bean
    public ItemWriter<Input> headerWriter() {
        return h -> System.out.println("Header Item Writer : " + h);
    }

    @Bean
    public ItemWriter<Input> dataWriter() {
        return d -> System.out.println("Data Item Writer : " + d);
    }

    @Bean
    public ItemWriter<Input> footerWriter() {
        //return new FlatFileItemWriter<Footer>();
        return f -> System.out.println("Footer Item Writer : " + f);
    }

    @Bean
    public ItemWriter<Input> itemWriter() {
        return new InputWriter();
    }

    /*
    @Bean
    public ClassifierCompositeItemWriter<Input> itemWriter() {
        ClassifierCompositeItemWriter<Input> itemWriter = new ClassifierCompositeItemWriter<>();
        itemWriter.setClassifier(new SubclassClassifier<>(Map.of(Header.class,
                headerWriter(),
                Data.class,
                dataWriter(),
                Footer.class,
                footerWriter()
                ),
                headerWriter()));
        return itemWriter;
    }

     */

    @Bean
    public FlatFileItemReader<Input> itemReader() {

        FlatFileItemReader<Input> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/input.txt"));
        flatFileItemReader.setName("reader");
        flatFileItemReader.setLineMapper(orderFileLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public InputMapper orderFileLineMapper() {
        InputMapper lineMapper = new InputMapper();

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);
        tokenizers.put("0*", headerTokenizer());
        tokenizers.put("1*", dataTokenizer());
        tokenizers.put("9*", footerTokenizer());

        lineMapper.setTokenizers(tokenizers);

        Map<String, FieldSetMapper<Input>> mappers = new HashMap<>(2);
        mappers.put("0*", headerFieldSetMapper());
        mappers.put("1*", dataFieldSetMapper());
        mappers.put("9*", footerFieldSetMapper());

        lineMapper.setFieldSetMappers(mappers);

        return lineMapper;
    }

    private FieldSetMapper<Input> footerFieldSetMapper() {
        return getMapper(Footer.class);
    }

    private FieldSetMapper<Input> dataFieldSetMapper() {
        return getMapper(Data.class);
    }

    private FieldSetMapper<Input> headerFieldSetMapper() {
        return getMapper(Header.class);
    }

    private LineTokenizer footerTokenizer() {
        return getTokenizer(new String[]{"state"},
                new Range[]{new Range(2, 3)});
    }

    private LineTokenizer dataTokenizer() {
        return getTokenizer(new String[]{"id", "date", "state"},
                new Range[]{new Range(2, 7),
                        new Range(8, 15),
                        new Range(16, 22)}
        );
    }

    private LineTokenizer headerTokenizer() {
        return getTokenizer(new String[]{"label", "date"},
                new Range[]{new Range(2, 8), new Range(9, 16)});
    }
    private BeanWrapperFieldSetMapper<Input> getMapper(Class<? extends Input> clazz) {
        final BeanWrapperFieldSetMapper<Input> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(clazz);
        return mapper;
    }

    private LineTokenizer getTokenizer(String[] names, Range[] ranges) {
        final FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames(names);
        tokenizer.setColumns(ranges);
        return tokenizer;
    }

}
