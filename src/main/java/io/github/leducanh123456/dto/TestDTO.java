package io.github.leducanh123456.dto;
import io.github.leducanh123456.dto.base.SuperDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestDTO extends SuperDTO {

    private UUID id;

    @NotEmpty(message = "{username.not.empty}")
    private String username;

    private String email;
}
