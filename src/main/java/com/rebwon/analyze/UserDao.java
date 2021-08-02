package com.rebwon.analyze;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public final class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(User user) {
        jdbcTemplate.update("update users set level=? where id=?",
            user.getLevel(),
            user.getId());
    }

    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLevel(rs.getInt("level"));
                return user;
            }
        });
    }
}
