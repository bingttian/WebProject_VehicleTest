package com.vehicleTest.DAO;

import com.vehicleTest.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by zyl on 2017/5/9.
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";

    String INSERT_FIELDS = "  username , password , salt, telephone, address ";

    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values (#{name}, #{password}, #{salt}" +
            ", #{telephone}, #{address})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set username = #{username} where id=#{id}"})
    void updateUsername(User user);

    @Update({"update ", TABLE_NAME, " set telephone = #{telephone} where id=#{id}"})
    void updateTelephone(User user);

    @Update({"update ", TABLE_NAME, " set address = #{address} where id=#{id}"})
    void updateAddress(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
