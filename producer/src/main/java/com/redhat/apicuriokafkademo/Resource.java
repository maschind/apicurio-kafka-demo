package com.redhat.apicuriokafkademo;

import com.redhat.apicuriokafkademo.schema.avro.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
public class Resource {

    @Autowired
    Producer producer;

    @PostMapping(value = "/publish")
    public void publish(@RequestBody Event event) {
        log.info("REST Controller has received entity: {}", event);
        this.producer.send(event);
    }
}