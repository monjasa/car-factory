package org.monjasa.carfactory.service.pipeline;

import org.monjasa.carfactory.model.transport.Pipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PipelineInitializer {

    @Bean("enginePipeline")
    public Pipeline enginePipeline(){
        return new Pipeline();
    }

    @Bean("bodyPipeline")
    public Pipeline bodyPipeline(){
        return new Pipeline();
    }

    @Bean("accessoryPipeline")
    public Pipeline accessoryPipeline(){
        return new Pipeline();
    }

    @Bean("carPipeline")
    public Pipeline carPipeline(){
        return new Pipeline();
    }

    @Bean("dealerPipeline")
    public Pipeline dealerPipeline(){
        return new Pipeline();
    }
}
