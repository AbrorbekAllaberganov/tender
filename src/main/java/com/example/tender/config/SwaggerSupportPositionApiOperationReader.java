package com.example.tender.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.OperationNameGenerator;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.operation.OperationReader;

import java.util.*;

import static java.util.Arrays.asList;

@Component
public class SwaggerSupportPositionApiOperationReader implements OperationReader {

    private static final Set<RequestMethod> allRequestMethods
            = new LinkedHashSet<>(asList(RequestMethod.values()));
    private final DocumentationPluginsManager pluginsManager;
    private final OperationNameGenerator nameGenerator;

    @Autowired
    public SwaggerSupportPositionApiOperationReader(DocumentationPluginsManager pluginsManager,
                                                    OperationNameGenerator nameGenerator) {
        this.pluginsManager = pluginsManager;
        this.nameGenerator = nameGenerator;
    }

    @Override
    public List<Operation> read(RequestMappingContext outerContext) {
        List<Operation> operations = new ArrayList();

        Set<RequestMethod> requestMethods = outerContext.getMethodsCondition();
        Set<RequestMethod> supportedMethods = supportedMethods(requestMethods);

        //Setup response message list
        Integer currentCount = 0;
        // Get position, then support position. NOTE: not support sorted by RequestMethod .
        int position = getApiOperationPosition(outerContext, 0);

        for (RequestMethod httpRequestMethod : supportedMethods) {
            OperationContext operationContext = new OperationContext(
                    new OperationBuilder(nameGenerator),
                    httpRequestMethod,
                    outerContext,
                    (position + currentCount));

            Operation operation = pluginsManager.operation(operationContext);
            if (!operation.isHidden()) {
                operations.add(operation);
                currentCount++;
            }
        }
        Collections.sort(operations, outerContext.operationOrdering());

        return operations;
    }

    private Set<RequestMethod> supportedMethods(final Set<RequestMethod> requestMethods) {
        return requestMethods == null || requestMethods.isEmpty()
                ? allRequestMethods
                : requestMethods;
    }

    private int getApiOperationPosition(final RequestMappingContext outerContext, final int defaultValue) {
        final Optional<ApiOperation> annotation = outerContext.findAnnotation(ApiOperation.class);
        return annotation.isPresent() ? annotation.get().position() : defaultValue;
    }
}
