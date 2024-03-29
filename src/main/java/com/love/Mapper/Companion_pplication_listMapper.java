package com.love.Mapper;

import com.love.module.Companion_pplication_list;
import com.love.module.Companion_pplication_listExample;
import java.util.List;

import com.love.returnClass.ApplicationList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Companion_pplication_listMapper {
    //根据申请表和用户表找出，用户此申请有多少封用户申请
    @Select("select distinct companion_pplication_list.user_id from user INNER JOIN companion_pplication_list on user.id = companion_pplication_list.applicant_id where user.id =#{id}")
    List<ApplicationList> ApplicationList(int id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    long countByExample(Companion_pplication_listExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int deleteByExample(Companion_pplication_listExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int insert(Companion_pplication_list record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int insertSelective(Companion_pplication_list record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    List<Companion_pplication_list> selectByExample(Companion_pplication_listExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    Companion_pplication_list selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int updateByExampleSelective(@Param("record") Companion_pplication_list record, @Param("example") Companion_pplication_listExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int updateByExample(@Param("record") Companion_pplication_list record, @Param("example") Companion_pplication_listExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int updateByPrimaryKeySelective(Companion_pplication_list record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table companion_pplication_list
     *
     * @mbg.generated Wed Oct 19 18:43:23 CST 2022
     */
    int updateByPrimaryKey(Companion_pplication_list record);
}