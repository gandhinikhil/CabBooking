package com.example.cabbooking.vo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsInVO {
    private long id;
    private String userName;
    private String gender;
    private int age;
    private LocalDateTime localDateTime = LocalDateTime.now();

}
