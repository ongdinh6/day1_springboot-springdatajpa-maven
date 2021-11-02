package com.tma.demo.entities;

import com.datastax.driver.core.DataType;
import com.tma.demo.dtos.responses.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("ACCOUNTS")
public class Account implements Serializable {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;
    private String username;
    private String password;
    private String phoneNumber;
    private boolean active;

    public AccountResponse toAccountResponse(){
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(id);
        accountResponse.setUsername(username);
        accountResponse.setPhoneNumber(phoneNumber);
        accountResponse.setActive(active);

        return accountResponse;
    }
}
