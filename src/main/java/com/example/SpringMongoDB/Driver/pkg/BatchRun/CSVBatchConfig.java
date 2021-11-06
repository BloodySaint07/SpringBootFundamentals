//package com.example.SpringMongoDB.Driver.pkg.BatchRun;
//
//
//import com.example.SpringMongoDB.Driver.pkg.model.SweetShop;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class CSVBatchConfig {
//
//
//    /**
//     * LOGGER
//     */
//    Logger LOGGER = LogManager.getLogger(CSVBatchConfig.class);
//
//    /** Class Dependencies */
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    public CSVBatchConfig(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
//        this.stepBuilderFactory = stepBuilderFactory;
//        this.jobBuilderFactory = jobBuilderFactory;
//    }
//
//    public CSVBatchConfig(){
//
//    }
//
//
//
//
//    @Bean
//    public Job job() {
//        return jobBuilderFactory.get("ReadCSV_JOB_2")
//                .incrementer(new RunIdIncrementer())
//                .start(step())
//                .build();
//    }
//
//    @Bean
//    public Step step() {
//        LOGGER.info("Reading Steps in "+this.getClass().getName().toString());
//        return stepBuilderFactory.get("Steps_ReadCSV")
//                .<SweetShop, SweetShop>chunk(1)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<SweetShop> reader() {
//        FlatFileItemReader<SweetShop> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("products.csv"));
//
//        DefaultLineMapper<SweetShop> lineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setNames("id", "sweetName", "sweetPrice");
//        BeanWrapperFieldSetMapper<SweetShop> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(SweetShop.class);
//
//        lineMapper.setLineTokenizer(lineTokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//
//        reader.setLineMapper(lineMapper);
//        return reader;
//
//    }
//
//    @Bean
//    public ItemProcessor<SweetShop,SweetShop> processor() {
//        return (p) -> {
//            p.setSweetPrice(p.getSweetPrice());
//            return p;
//        };
//    }
//
//    @Bean
//    public ItemWriter<SweetShop> writer() {
//        JdbcBatchItemWriter<SweetShop> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(dataSource());
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<SweetShop>());
//        writer.setSql("INSERT INTO SWEETSHOP (ID,SWEETNAME,SWEETPRICE) VALUES (:id,:sweetname,:sweetprice)");
//
//        return writer;
//    }
//
//    @Bean
//     public DataSource dataSource() {
//        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
//        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource1.setUrl("jdbc:mysql://127.0.0.1:3306/springdbschema?useSSL=false");
//        dataSource1.setUsername("root");
//        dataSource1.setPassword("root");
//        return dataSource1;
//    }
//
//}
