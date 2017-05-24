package com.vehicleTest.DAO;

import com.vehicleTest.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by zyl on 2017/5/9.
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";

    String INSERT_FIELDS = "  username , password , salt ";

    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values (#{name}, #{password}, #{salt})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
