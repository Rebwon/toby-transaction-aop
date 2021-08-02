package com.rebwon.analyze;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@Getter @Setter
public final class User {

    private Long id;
    private String name;
    private int level;

    public User() {}

    public User(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public void upgradleLevel() {
        this.level++;
    }
}
