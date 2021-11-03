package com.tma.demo.configs.cassandra;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/*
 * Code was referenced at https://github.com/springframeworkguru/spring-boot-cassandra-example
 */
@Configuration
@ComponentScan("com.tma.demo")
@PropertySource(value = {"classpath:application.properties"})
@EnableCassandraRepositories(basePackages = "com.tma.demo.repositories")
public class CassandraDBConfig extends AbstractCassandraConfiguration {
    public static final String KEYSPACE = "my_data";

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    /*
     * Two methods were commented below here can be made an error with 'Keyspace is already existed' error message.
     **/
   /* @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(KEYSPACE);
        return Arrays.asList(specification);
    }*/

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.tma.demo.entities"};
    }

}
