package org.digimad.springbootamazons3.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Profile {
    private String name;
    private String address;
    private Long phone;
    private String email;
    private Long profileId;
}
