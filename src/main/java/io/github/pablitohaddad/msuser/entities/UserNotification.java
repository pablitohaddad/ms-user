package io.github.pablitohaddad.msuser.entities;

import io.github.pablitohaddad.msuser.enums.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotification {
    private String email;
    private Events event;
    private String date;
}
