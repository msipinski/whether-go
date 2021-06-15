package pl.zzpj.djsr.whethergo.accounts.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccountDTO {
    String username;
    String password;
    String email;
}
