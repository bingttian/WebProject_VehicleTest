package com.vehicleTest.DAO;

import com.vehicleTest.model.UpFile;
import org.apache.ibatis.annotations.*;

/**
 * Created by zyl on 2017/5/10.
 */
@Mapper
public interface UpFileDAO {
    String TABLE_NAME = "up_file";

    String INSERT_FIELDS = "  username , filename , zhouzhong, zhidong, description, up_time ";

    String SELECT_FIELDS = INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values (#{username}, #{filename}, #{zhouzhong}" +
            ", #{zhidong}, #{description}, #{up_time})"})
    int addfile(UpFile upFile);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{name}"})
    UpFile selectByUser(String name);

    //filename是唯一的
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where filename=#{filename}"})
    UpFile selectByFile(String filename);

    @Delete({"delete from ", TABLE_NAME, " where filename = #{filename}"})
    void deleteByName(String filename);

    @Update({"update ", TABLE_NAME, " set zhouzhong = #{zhouzhong} , zhidong = #{zhidong} where filename=#{filename}"})
    void updateInfo(UpFile upFile);
}
