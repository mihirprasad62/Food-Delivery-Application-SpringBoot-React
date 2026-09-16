package com.kodewala.io;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
}
