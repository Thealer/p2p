package cn.wolfcode.p2p.base.rabbitMq;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable{

    private static final long serialVersionUID = 9143113848701092751L;

    private String name;

    private String age;
}
