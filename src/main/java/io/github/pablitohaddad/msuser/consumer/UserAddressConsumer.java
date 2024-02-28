package io.github.pablitohaddad.msuser.consumer;

import io.github.pablitohaddad.msuser.dto.UserCreateAddressDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseAddressDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "msaddress", url = "http://localhost:8082/v1/address")
public interface UserAddressConsumer {

    @PostMapping
    UserResponseAddressDTO complementAddress(@Valid @RequestBody UserCreateAddressDTO createDto);
}
